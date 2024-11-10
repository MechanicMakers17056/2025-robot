package org.firstinspires.ftc.teamcode.registry_stuff;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.registry_stuff.registry_hardware.RegistryDcMotor;
import org.firstinspires.ftc.teamcode.registry_stuff.registry_hardware.RegistryServo;

import java.util.HashMap;
import java.util.Map;

public class RegistryUtils {
    private static HardwareMap map;
    private static final Map<String, HardwareDevice> registeredDevices = new HashMap<>();

    public static void setMap(HardwareMap hardwareMap) {
        map = hardwareMap;
    }

    public static HardwareDevice getDevice(String name) {
        return registeredDevices.get(name);
    }

    public static RegistryDcMotor initMotor(String name) {
        RegistryDcMotor motor = new RegistryDcMotor(map, name);
        registeredDevices.put(name, motor);
        return motor;
    }

    public static RegistryDcMotor initMotor(String name, DcMotorSimple.Direction direction) {
        RegistryDcMotor motor = new RegistryDcMotor(map, name, direction);
        registeredDevices.put(name, motor);
        return motor;
    }

    public static RegistryServo initServo(String name) {
        RegistryServo servo = new RegistryServo(map, name);
        registeredDevices.put(name, servo);
        return servo;
    }

    public static RegistryServo initServo(String name, Servo.Direction direction) {
        RegistryServo servo = new RegistryServo(map, name, direction);
        registeredDevices.put(name, servo);
        return servo;
    }
}
