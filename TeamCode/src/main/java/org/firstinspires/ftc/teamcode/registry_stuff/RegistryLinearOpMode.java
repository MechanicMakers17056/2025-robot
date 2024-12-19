package org.firstinspires.ftc.teamcode.registry_stuff;

import static org.firstinspires.ftc.teamcode.registry_stuff.RegistryUtils.initCRServo;
import static org.firstinspires.ftc.teamcode.registry_stuff.RegistryUtils.initMotor;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.enums.Colors;
import org.firstinspires.ftc.teamcode.registry_stuff.registry_hardware.RegistryCRServo;
import org.firstinspires.ftc.teamcode.registry_stuff.registry_hardware.RegistryDcMotor;

// Abstract class that serves as a base for linear op modes with common robot components such as basic movement methods and it also used to give access to all
// motors, sensors and servos in all subclasses without needing to copy them for each one and avoid repetitiveness
public abstract class RegistryLinearOpMode extends LinearOpMode {
    // Declare motors, servos and sensors
    public static RegistryDcMotor encoderMotor;
    public static RegistryCRServo encoderServo;
    public static AnalogInput encoder;
    public static TouchSensor magnet;

    // Method used to call the initialization of all motors, sensors, and servos because the hardwareMap is set only when the Op Mode has began to run
    // it is also used to run the code provided by the subclasses (runCode)
    public void runOpMode() {
        boolean isInitialable = this.getClass().isAnnotationPresent(InitialableOp.class);
        initConfig();
        if(!isInitialable) {
            initOpMode();
            waitForStart();
            while (opModeIsActive()) {
                runCode();
            }
        } else {
            runCode();
        }
    }

    public void initOpMode() {

    }

    // Method to initialize motors, sensors, and servos
    private void initConfig() {
        RegistryUtils.setMap(hardwareMap);

        encoderMotor = initMotor("tm");
        encoderServo = initCRServo("es");

//        resetEncoder(encoderMotor);
    }

     public static void setGamepadColor(Gamepad gamepad, Colors color, int duration) {
//        gamepad.setLedColor(color.getRGB()[0], color.getRGB()[1], color.getRGB()[2], duration);
        gamepad.setLedColor(color.getRed(), color.getGreen(), color.getBlue(), duration);
     }

    // Abstract method to be implemented by subclasses to serve as the runOpMode method where all of the code for that class is ran
    // because the runOpMode method is already being used by the abstract class
    public abstract void runCode();

    // Method to reset encoders of specific motors (could be all of them)
    public void resetEncoders(RegistryDcMotor... motors) {
        for (RegistryDcMotor motor : motors) {
            resetEncoder(motor);
        }
    }

    // Method to reset the encoder of one specific motor
    public void resetEncoder(RegistryDcMotor motor) {
        motor.setMode(RegistryDcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(RegistryDcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setMotorsModes(DcMotor.RunMode mode, RegistryDcMotor... motors) {
        for (RegistryDcMotor motor : motors) {
            motor.setMode(mode);
        }
    }
    public void waitFor(double seconds) {
        sleep((long) (seconds * 1000L));
    }
}
