package org.firstinspires.ftc.teamcode.registry_stuff.registry_hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

public class RegistryServo implements Servo {
    Servo servo;

    public RegistryServo(HardwareMap map, String name, Direction direction) {
        try {
            this.servo = map.get(Servo.class, name);
        } catch (IllegalArgumentException e) {
            this.servo = null;
        }
        setDirection(direction);
    }

    public RegistryServo(HardwareMap map, String name) {
        try {
            this.servo = map.get(Servo.class, name);
        } catch (IllegalArgumentException e) {
            this.servo = null;
        }
        setDirection(Direction.FORWARD);
    }


    @Override
    public ServoController getController() {
        return servo.getController();
    }

    @Override
    public int getPortNumber() {
        return servo == null ? 0 : servo.getPortNumber();
    }

    @Override
    public void setDirection(Direction direction) {
        if(servo != null)
            servo.setDirection(direction);
    }

    @Override
    public Direction getDirection() {
        return servo.getDirection();
    }

    @Override
    public void setPosition(double v) {
        if(servo != null)
            servo.setPosition(v);
    }

    @Override
    public double getPosition() {
        return servo == null ? 0 : servo.getPosition();
    }

    @Override
    public void scaleRange(double v, double v1) {
        if(servo != null)
            servo.scaleRange(v, v1);
    }

    @Override
    public Manufacturer getManufacturer() {
        return servo == null ? Manufacturer.Other : servo.getManufacturer();
    }

    @Override
    public String getDeviceName() {
        return servo == null ? "null" : servo.getDeviceName();
    }

    @Override
    public String getConnectionInfo() {
        return servo == null ? "null" : servo.getConnectionInfo();
    }

    @Override
    public int getVersion() {
        return servo == null ? 0 : servo.getVersion();
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {
        if(servo != null)
            servo.resetDeviceConfigurationForOpMode();
    }

    @Override
    public void close() {
        if(servo != null)
            servo.close();
    }
}
