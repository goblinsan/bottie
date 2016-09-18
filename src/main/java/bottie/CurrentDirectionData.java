package bottie;

public class CurrentDirectionData {
    private double x;
    private double y;
    private double heading;
    private double velocity;
    private double gunHeading;

    public CurrentDirectionData(double x, double y, double heading, double velocity, double gunHeading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
        this.velocity = velocity;
        this.gunHeading = gunHeading;
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

    public double getGunHeading() {
        return gunHeading;
    }

    public void setGunHeading(double gunHeading) {
        this.gunHeading = gunHeading;
    }
}
