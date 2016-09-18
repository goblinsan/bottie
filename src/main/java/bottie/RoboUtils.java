package bottie;

import java.awt.geom.Point2D;

public class RoboUtils {
    static double absoluteBearing(double x1, double y1, double x2, double y2) {
        double xo = x2-x1;
        double yo = y2-y1;
        double hyp = Point2D.distance(x1, y1, x2, y2);
        double arcSin = Math.toDegrees(Math.asin(xo / hyp));
        double bearing = 0;

        if (xo > 0 && yo > 0) { // both pos: lower-Left
            bearing = arcSin;
        } else if (xo < 0 && yo > 0) { // x neg, y pos: lower-right
            bearing = 360 + arcSin; // arcsin is negative here, actually 360 - ang
        } else if (xo > 0 && yo < 0) { // x pos, y neg: upper-left
            bearing = 180 - arcSin;
        } else if (xo < 0 && yo < 0) { // both neg: upper-right
            bearing = 180 - arcSin; // arcsin is negative here, actually 180 + ang
        }

        return bearing;
    }

    static double getGunAimDegrees(CurrentDirectionData myData, Enemy enemy, double firePower) {

        double bulletSpeed = 20 - firePower * 3;
        long time = (long) (enemy.getDistance() / bulletSpeed);
        double calcNextX = myData.getX() + enemy.getFutureX(time);
        double calcNextY = myData.getY() + enemy.getFutureY(time);
        double absFutureBotBearing = absoluteBearing(myData.getX(), myData.getY(), calcNextX, calcNextY);
        double relFutureBotBearing = absFutureBotBearing - myData.getHeading();

        return myData.getHeading() - myData.getGunHeading() + relFutureBotBearing;
    }
}
