package bottie;

import robocode.ScannedRobotEvent;

public class Enemy {

    private String name;
    private double bearing;
    private double distance;
    private double energy;
    private double heading;
    private double velocity;
    private double x;
    private double absX;
    private double y;
    private double absY;
    double normalAbsBearDeg;

    public Enemy() {
    }

    public Enemy(String name) {
        this.name = name;
    }

    public void update(ScannedRobotEvent event) {
        bearing = event.getBearing();
        distance = event.getDistance();
        energy = event.getEnergy();
        heading = event.getHeading();
        velocity = event.getVelocity();
    }

    public double getFutureX(long when) {
        return x + Math.sin(Math.toRadians(getHeading())) * getVelocity() * when;
    }

    public double getFutureY(long when) {
        return y + Math.cos(Math.toRadians(getHeading())) * getVelocity() * when;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void calculatePositions(CurrentDirectionData data) {
        normalAbsBearDeg = calcBearing(data.getHeading());
        x = calcX(distance);
        absX = calcAbsX(data.getX());
        y = calcY(distance);
        absY = calcAbsY(data.getY());
    }

    public double getXWhen(long time){
        return absX + x + Math.sin(Math.toRadians(heading)) * velocity * time;
    }

    public double getYWhen(long time){
        return absY + y + Math.cos(Math.toRadians(heading)) * velocity * time;
    }

    private double calcX(double distance){
        return Math.sin(Math.toRadians(normalAbsBearDeg)) * distance;
    }

    private double calcAbsX(double myX) {
        return myX + x;
    }

    private double calcY(double distance){
        return Math.cos(Math.toRadians(normalAbsBearDeg)) * distance;
    }

    private double calcAbsY(double myY) {
        return myY + y;
    }

    private double calcBearing(double myHeading) {
        double absBearingDeg = myHeading + getBearing();
        return absBearingDeg < 0 ? absBearingDeg += 360 : absBearingDeg;
    }
}
