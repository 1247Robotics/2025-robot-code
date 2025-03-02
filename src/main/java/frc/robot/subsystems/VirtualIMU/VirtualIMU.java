package frc.robot.subsystems.VirtualIMU;

import com.revrobotics.spark.SparkFlex;
import com.studica.frc.AHRS;


public class VirtualIMU {
    private static double x;
    private static double y;
    // private static double theta;
    private static double wheelBase = 21.869;
    private static TrackMotor leftMotor;
    private static TrackMotor rightMotor;
    private static AHRS navx = new AHRS(AHRS.NavXComType.kUSB1);

    public VirtualIMU() {}

    public VirtualIMU(SparkFlex left, SparkFlex right) {
        x = 0;
        y = 0;
        // theta = 0;
        leftMotor = new TrackMotor(6, 8.46);
        rightMotor = new TrackMotor(6, 8.46);
        leftMotor.setMotor(left);
        rightMotor.setMotor(right);
    }

    public double setGearRatio(double gearRatio) {
        leftMotor.setGearRatio(gearRatio);
        rightMotor.setGearRatio(gearRatio);
        return gearRatio;     
    }

    public double setWheelDiameter(double wheelDiameter) {
        leftMotor.setWheelDiameter(wheelDiameter);
        rightMotor.setWheelDiameter(wheelDiameter);
        return wheelDiameter;
    }

    public SparkFlex getLeftMotor() {
        return leftMotor.getMotor();
    }

    public SparkFlex getRightMotor() {
        return rightMotor.getMotor();
    }

    // private double differentialToRadians(double left, double right) {
    //     return (right - left) / wheelBase;
    // }

    // private void updateTheta(double distanceTraveledLeft, double distanceTraveledRight) {
    //     double relativeTheta = differentialToRadians(distanceTraveledLeft, distanceTraveledRight);
    //     theta += relativeTheta;
    // }

    private double avg(double a, double b) {
        return (a + b) / 2;
    }

    private void updateXY(double distanceTraveledLeft, double distanceTraveledRight) {
        double distance = avg(distanceTraveledLeft, distanceTraveledRight);
        double theta = getRadians();
        x += distance * Math.cos(theta);
        y += distance * Math.sin(theta);

        // x -= distance * 0.15 * Math.cos(theta + (0.5 * Math.PI));
        // y -= distance * 0.15 * Math.sin(theta + (0.5 * Math.PI));
    }

    public void updateMotors() {
        double distanceTraveledLeft = leftMotor.updatePositionAndGetMovement();
        double distanceTraveledRight = rightMotor.updatePositionAndGetMovement();
        // updateTheta(distanceTraveledLeft, distanceTraveledRight);
        updateXY(distanceTraveledLeft, distanceTraveledRight);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return navx.getAngle();
    }

    public double getRadians() {
        return (getAngle() / 360) * (2*Math.PI);
    }

    public double[] getPosition() {
        double[] position = { x, y, getAngle() };
        return position;
    }

    public double getWheelBase() {
        return wheelBase;
    }

    public double getWheelDiameter() {
        return leftMotor.getWheelDiameter();
    }

    public double getGearRatio() {
        return leftMotor.getGearRatio();
    }

    public TrackMotor getLeftTrackMotor() {
        return leftMotor;
    }

    public TrackMotor getRightTrackMotor() {
        return rightMotor;
    }

    public void resetDisplacement() {
        navx.reset();
        x = 0;
        y = 0;
    }

    public double getCollectiveAccel() {
        return navx.getWorldLinearAccelX() + navx.getWorldLinearAccelY() + navx.getWorldLinearAccelZ();
    }
    public double getAvgAccel() {
        return getCollectiveAccel() / 3;
    }
}
