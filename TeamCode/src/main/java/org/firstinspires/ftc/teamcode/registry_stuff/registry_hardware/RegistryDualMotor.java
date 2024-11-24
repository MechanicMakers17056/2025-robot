package org.firstinspires.ftc.teamcode.registry_stuff.registry_hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class RegistryDualMotor implements DcMotor {
    List<RegistryDcMotor> motors = new ArrayList<>();
    private int selectedMotorIndex = 0;

    public RegistryDualMotor(HardwareMap map, RegistryDcMotor... motors) {
        for (RegistryDcMotor motor : motors) {
            try {
                this.motors.add(new RegistryDcMotor(map, motor.getConfigName(), motor.getDirection()));
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    public void setSelectedMotor(int index) {
        selectedMotorIndex = index;
    }

    public RegistryDcMotor getSelectedMotor() {
        return motors.get(selectedMotorIndex);
    }

    private void doForEachMotor(Consumer<? super RegistryDcMotor> action) {
        motors.stream().filter(Objects::nonNull).forEach(action);
    }

    @Override
    public MotorConfigurationType getMotorType() {
        return getSelectedMotor() == null ? new MotorConfigurationType() : getSelectedMotor().getMotorType();
    }

    @Override
    public void setMotorType(MotorConfigurationType motorConfigurationType) {
        doForEachMotor(motor -> motor.setMotorType(motorConfigurationType));
    }

    @Override
    public DcMotorController getController() {
        return getSelectedMotor().getController();
    }

    @Override
    public int getPortNumber() {
        return getSelectedMotor() == null ? 0 : getSelectedMotor().getPortNumber();
    }

    @Override
    public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {
        doForEachMotor(motor -> motor.setZeroPowerBehavior(zeroPowerBehavior));
    }

    @Override
    public ZeroPowerBehavior getZeroPowerBehavior() {
        return getSelectedMotor() == null ? ZeroPowerBehavior.BRAKE : getSelectedMotor().getZeroPowerBehavior();
    }

    @Override
    public void setPowerFloat() {
        doForEachMotor(motor -> motor.setPowerFloat());
    }

    @Override
    public boolean getPowerFloat() {
        return getSelectedMotor() != null && getSelectedMotor().getPowerFloat();
    }

    @Override
    public void setTargetPosition(int i) {
        doForEachMotor(motor -> motor.setTargetPosition(i));
    }

    @Override
    public int getTargetPosition() {
        return getSelectedMotor() == null ? 0 : getSelectedMotor().getTargetPosition();
    }

    @Override
    public boolean isBusy() {
        return getSelectedMotor() != null && getSelectedMotor().isBusy();
    }

    @Override
    public int getCurrentPosition() {
        return getSelectedMotor() == null ? 0 : getSelectedMotor().getCurrentPosition();
    }

    @Override
    public void setMode(RunMode runMode) {
        doForEachMotor(motor -> motor.setMode(runMode));
    }

    @Override
    public RunMode getMode() {
        return getSelectedMotor() == null ? RunMode.RUN_WITHOUT_ENCODER : getSelectedMotor().getMode();
    }

    @Override
    public void setDirection(Direction direction) {
        doForEachMotor(motor -> motor.setDirection(direction));
    }

    @Override
    public Direction getDirection() {
        return getSelectedMotor() == null ? Direction.FORWARD : getSelectedMotor().getDirection();
    }

    @Override
    public void setPower(double v) {
        doForEachMotor(motor -> motor.setPower(v));
    }

    @Override
    public double getPower() {
        return getSelectedMotor() == null ? 0 : getSelectedMotor().getPower();
    }

    @Override
    public Manufacturer getManufacturer() {
        return getSelectedMotor() == null ? Manufacturer.Other : getSelectedMotor().getManufacturer();
    }

    @Override
    public String getDeviceName() {
        return getSelectedMotor() == null ? "null" : getSelectedMotor().getDeviceName();
    }

    @Override
    public String getConnectionInfo() {
        return getSelectedMotor() == null ? "null" : getSelectedMotor().getConnectionInfo();
    }

    @Override
    public int getVersion() {
        return getSelectedMotor() == null ? 0 : getSelectedMotor().getVersion();
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {
        doForEachMotor(motor -> motor.resetDeviceConfigurationForOpMode());
    }

    @Override
    public void close() {
        doForEachMotor(motor -> motor.close());
    }
}
