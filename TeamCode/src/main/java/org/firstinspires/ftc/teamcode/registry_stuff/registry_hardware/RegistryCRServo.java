package org.firstinspires.ftc.teamcode.registry_stuff.registry_hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RegistryCRServo implements CRServo {
    CRServo servo;
    String _name;

    public RegistryCRServo(HardwareMap map, String name, Direction direction) {
        try {
            _name = name;
            this.servo = map.get(CRServo.class, name);
        } catch (IllegalArgumentException e) {
            this.servo = null;
        }
        setDirection(direction);
    }

    public RegistryCRServo(HardwareMap map, String name) {
        try {
            this.servo = map.get(CRServo.class, name);
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
        return servo == null ? Direction.FORWARD : servo.getDirection();
    }

    @Override
    public void setPower(double power) {
        if (servo != null)
            servo.setPower(power);
    }

    public void setPower(double power, Telemetry telemetry) {
        telemetry.addData("Speed " + _name, power);
        if (servo != null)
            servo.setPower(power);
    }

    @Override
    public double getPower() {
        return servo == null ? 0 : servo.getPower();
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
