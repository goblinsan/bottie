package bottie;

import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class McBotFace extends robocode.AdvancedRobot {

    boolean forward = true;
    Map<String, Enemy> enemyMap = new HashMap<String, Enemy>();

    public void run() {
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setBodyColor(new Color(37, 132, 120));
        setGunColor(new Color(90, 178, 187));
        setRadarColor(new Color(144, 200, 184));
        setBulletColor(new Color(255, 121, 58));
        setScanColor(new Color(157, 255, 226));

        while (true) {
            setAhead(40000);
            setTurnRadarRight(360);
            setTurnRight(90);
            waitFor(new robocode.TurnCompleteCondition(this));
            setTurnRadarRight(360);
            setTurnLeft(180);
            waitFor(new robocode.TurnCompleteCondition(this));
            setTurnRadarRight(360);
            setTurnRight(180);
            waitFor(new robocode.TurnCompleteCondition(this));
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        CurrentDirectionData myData = new CurrentDirectionData(getX(), getY(), getHeading(), getVelocity(), getGunHeading());
        Enemy scannedEnemy = getEnemyFromMap(e);
        scannedEnemy.update(e);
        scannedEnemy.calculatePositions(myData);

        //Enemy closestTarget = getClosestTarget().get();  java 8 not working yet
        //Enemy closestTarget = getClosestTarget();  would need logic to keep scanning

        setTurnRadarRight(getHeading() - getRadarHeading() + e.getBearing());

        double firePower = Math.min(400 / e.getDistance(), 3);

        setTurnGunRight(RoboUtils.getGunAimDegrees(myData, scannedEnemy, firePower));

        if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10) {
            setFire(firePower);
        }
    }

    private Enemy getClosestTarget() {
        Enemy closest = null;
        for (Enemy e : enemyMap.values()) {
            if (null == closest){
                closest = e;
            } else {
                if (e.getDistance() < closest.getDistance()){
                    closest = e;
                }
            }
        }
        return closest;
    }

//    private Optional<Enemy> getClosestTarget() {
//        return enemyMap.values().stream().min(Comparator.comparing(e -> e.getDistance()));
//    }

    private Enemy getEnemyFromMap(ScannedRobotEvent e) {
        Enemy mappedEnemy = enemyMap.get(e.getName());
        if (null == mappedEnemy) {
            mappedEnemy = new Enemy(e.getName());
            enemyMap.put(e.getName(), mappedEnemy);
        }
        return mappedEnemy;
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        enemyMap.remove(event.getName());
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        reverseDirection();
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        reverseDirection();
    }

    public void reverseDirection() {
        if (forward) {
            setBack(4000);
            forward = false;
        } else {
            setAhead(4000);
            forward = true;
        }
    }

    double normalizeBearing(double angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }

}
