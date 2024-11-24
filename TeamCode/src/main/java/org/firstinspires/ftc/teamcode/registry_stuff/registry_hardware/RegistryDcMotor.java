package org.firstinspires.ftc.teamcode.registry_stuff.registry_hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

public class RegistryDcMotor implements DcMotor {
    DcMotor motor;

    String name;

    public RegistryDcMotor(HardwareMap map, String name, Direction direction) {
        try {
            this.name = name;
            this.motor = map.get(DcMotor.class, name);
        } catch (IllegalArgumentException e) {
            this.motor = null;
        }
        setDirection(direction);
    }

    public RegistryDcMotor(HardwareMap map, String name) {
        try {
            this.name = name;
            this.motor = map.get(DcMotor.class, name);
        } catch (IllegalArgumentException e) {
            this.motor = null;
        }
        setDirection(Direction.FORWARD);
    }

    public String getConfigName() {
        return name;
    }

    @Override
    public MotorConfigurationType getMotorType() {
        return motor == null ? new MotorConfigurationType() : motor.getMotorType();
    }

    @Override
    public void setMotorType(MotorConfigurationType motorConfigurationType) {
        if (motor != null)
            motor.setMotorType(motorConfigurationType);
    }

    @Override
    public DcMotorController getController() {
        return motor.getController();
    }

    @Override
    public int getPortNumber() {
        return motor == null ? 0 : motor.getPortNumber();
    }

    @Override
    public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {
        if (motor != null)
            motor.setZeroPowerBehavior(zeroPowerBehavior);
    }

    @Override
    public ZeroPowerBehavior getZeroPowerBehavior() {
        return motor == null ? ZeroPowerBehavior.BRAKE : motor.getZeroPowerBehavior();
    }

    @Override
    public void setPowerFloat() {
        if (motor != null)
            motor.setPowerFloat();
    }

    @Override
    public boolean getPowerFloat() {
        return motor != null && motor.getPowerFloat();
    }

    @Override
    public void setTargetPosition(int i) {
        if (motor != null)
            motor.setTargetPosition(i);
    }

    @Override
    public int getTargetPosition() {
        return motor == null ? 0 : motor.getTargetPosition();
    }

    @Override
    public boolean isBusy() {
        return motor != null && motor.isBusy();
    }

    @Override
    public int getCurrentPosition() {
        return motor == null ? 0 : motor.getCurrentPosition();
    }

    @Override
    public void setMode(RunMode runMode) {
        if (motor != null)
            motor.setMode(runMode);
    }

    @Override
    public RunMode getMode() {
        return motor == null ? RunMode.RUN_WITHOUT_ENCODER : motor.getMode();
    }

    @Override
    public void setDirection(Direction direction) {
        if (motor != null)
            motor.setDirection(direction);
    }

    @Override
    public Direction getDirection() {
        return motor == null ? Direction.FORWARD : motor.getDirection();
    }

    @Override
    public void setPower(double v) {
        if (motor != null)
            motor.setPower(v);
    }

    @Override
    public double getPower() {
        return motor == null ? 0 : motor.getPower();
    }

    @Override
    public Manufacturer getManufacturer() {
        return motor == null ? Manufacturer.Other : motor.getManufacturer();
    }

    @Override
    public String getDeviceName() {
        return motor == null ? "null" : motor.getDeviceName();
    }

    @Override
    public String getConnectionInfo() {
        return motor == null ? "null" : motor.getConnectionInfo();
    }

    @Override
    public int getVersion() {
        return motor == null ? 0 : motor.getVersion();
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {
        if (motor != null)
            motor.resetDeviceConfigurationForOpMode();
    }

    @Override
    public void close() {
        if (motor != null)
            motor.close();
    }
}
