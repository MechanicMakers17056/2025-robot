package org.firstinspires.ftc.teamcode.registry_stuff.registry_hardware;

import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoController;
public class RegistrySmartServo {
    CRServoImpl servo;

    public RegistrySmartServo(HardwareMap map, String name, DcMotorSimple.Direction direction) {
        try {
            this.servo = map.get(CRServoImpl.class, name);
        } catch (IllegalArgumentException e) {
            this.servo = null;
        }
        setDirection(direction);
    }

    public RegistrySmartServo(HardwareMap map, String name) {
        try {
            this.servo = map.get(CRServoImpl.class, name);
        } catch (IllegalArgumentException e) {
            this.servo = null;
        }
        setDirection(DcMotorSimple.Direction.FORWARD);
    }


    public ServoController getController() {
        return servo.getController();
    }

    public int getPortNumber() {
        return servo == null ? 0 : servo.getPortNumber();
    }

    public void setDirection(DcMotorSimple.Direction direction) {
        if(servo != null)
            servo.setDirection(direction);
    }

    public DcMotorSimple.Direction getDirection() {
        return servo.getDirection();
    }

    public void setPower(double v) {
        if(servo != null)
            servo.setPower(v);
    }

    public double getPower() {
        return servo == null ? 0 : servo.getPower();
    }

    public HardwareDevice.Manufacturer getManufacturer() {
        return servo == null ? HardwareDevice.Manufacturer.Other : servo.getManufacturer();
    }

    public String getDeviceName() {
        return servo == null ? "null" : servo.getDeviceName();
    }

    public String getConnectionInfo() {
        return servo == null ? "null" : servo.getConnectionInfo();
    }

    public int getVersion() {
        return servo == null ? 0 : servo.getVersion();
    }

    public void resetDeviceConfigurationForOpMode() {
        if(servo != null)
            servo.resetDeviceConfigurationForOpMode();
    }

    public void close() {
        if(servo != null)
            servo.close();
    }
}
