package frc.robot.subsystems.VirtualIMU;

import com.revrobotics.spark.SparkFlex;

public class TrackMotor {
    private double wheelDiameter = 0.0;
    private double gearRatio = 0.0;
    private double lastEncoderPosition;
    private double velocity;
    private double lastVelocitySampleTime;
    private SparkFlex motor;
    public TrackMotor(double wheelDiameter, double gearRatio) {
        this.wheelDiameter = wheelDiameter;
        this.gearRatio = gearRatio;
    }

    public void setMotor(SparkFlex motor) {
        this.motor = motor;
    }

    public double getWheelDiameter() {
        return wheelDiameter;
    }

    public double getGearRatio() {
        return gearRatio;
    }

    public double getLastEncoderPosition() {
        return lastEncoderPosition;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getLastVelocitySampleTime() {
        return lastVelocitySampleTime;
    }
    
    public void setWheelDiameter(double wheelDiameter) {
        this.wheelDiameter = wheelDiameter;
    }

    public void setGearRatio(double gearRatio) {
        this.gearRatio = gearRatio;
    }

    private double encoderToDegrees(double encoder) {
        return encoder * 360;
    }

    private double degreesToDistance(double degrees) {
        return degrees * (wheelDiameter * Math.PI) / 360;
    }

    private double calculateVelocity(double distanceTraveled) {
        long now = System.currentTimeMillis();
        double timeDelta = (now - lastVelocitySampleTime) / 1000;
        if (timeDelta == 0) {
            return velocity;
        }
        lastVelocitySampleTime = now;
        velocity = distanceTraveled / timeDelta;
        return velocity;
    }

    private double calculateMovement(double encoderPosition) {
        encoderPosition /= gearRatio;
        double difference = encoderPosition - lastEncoderPosition;

        lastEncoderPosition = encoderPosition;

        double angleTheta = encoderToDegrees(difference);
        double distance = -degreesToDistance(angleTheta);
        // distance /= gearRatio;
        // distance *= 0.254;
        // distance *= 0.0254;
        distance /= 39.37007874;

        velocity = calculateVelocity(distance);

        return distance;
    }

    public double updatePositionAndGetMovement() {
        double distanceMoved = calculateMovement(motor.getEncoder().getPosition());
        return distanceMoved;
    }

    /** 
     * Calculate the end position of the encoder were the robot to move a certain distance
     * 
     * @param distance The distance the robot would move
     * @return The encoder position the robot would end up at
     */
    public double getEncoderMovementForDistance(double distance) {
        double position = distance / (wheelDiameter * Math.PI) * gearRatio;
        return position;
    }    

    public SparkFlex getMotor() {
        return motor;
    }
}
