package org.firstinspires.ftc.teamcode.registry_stuff;

import static org.firstinspires.ftc.teamcode.registry_stuff.RegistryUtils.initMotor;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.enums.Colors;
import org.firstinspires.ftc.teamcode.enums.Direction;
import org.firstinspires.ftc.teamcode.enums.Stops;
import org.firstinspires.ftc.teamcode.registry_stuff.registry_hardware.RegistryDcMotor;
import org.firstinspires.ftc.teamcode.registry_stuff.registry_hardware.RegistryDualMotor;

import java.lang.annotation.Annotation;

// Abstract class that serves as a base for linear op modes with common robot components such as basic movement methods and it also used to give access to all
// motors, sensors and servos in all subclasses without needing to copy them for each one and avoid repetitiveness
public abstract class RegistryLinearOpMode extends LinearOpMode {
    // Declare motors, servos and sensors
    public static RegistryDcMotor frontLeftDrive;
    public static RegistryDcMotor frontRightDrive;
    public static RegistryDcMotor rearLeftDrive;
    public static RegistryDcMotor rearRightDrive;
    public static RegistryDualMotor lifts;
    public static RegistryDualMotor fronters;
//    public static RegistrySmartServo servo;
    public static CRServo servo;
    public static TouchSensor magnet;
    //    public static RegistryDcMotor lift1;
//    public static RegistryDcMotor lift2;
//    public static RegistryDcMotor clawRotationCling;
//    public static RegistryDcMotor rotationCling;
//    public static RegistryServo clawRight;
//    public static RegistryServo clawLeft;
    public final static int LIFT_MAX_POSITION = 1900;

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
                if (this instanceof AutonomousRegistryLinearOpMode) {
                    requestOpModeStop();
                }
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

        frontLeftDrive = initMotor("fl", DcMotorSimple.Direction.REVERSE);
        rearLeftDrive = initMotor("rl", DcMotorSimple.Direction.REVERSE);
        frontRightDrive = initMotor("fr", DcMotorSimple.Direction.FORWARD);
        rearRightDrive = initMotor("rr", DcMotorSimple.Direction.FORWARD);
        lifts = new RegistryDualMotor(hardwareMap, initMotor("l1", DcMotorSimple.Direction.REVERSE),
                initMotor("l2"));
        fronters = new RegistryDualMotor(hardwareMap, initMotor("f1", DcMotorSimple.Direction.REVERSE),
                initMotor("f2"));
//        servo = initSmartServo("ss");
//        servo = hardwareMap.get(CRServo.class, "ss");
//        magnet = hardwareMap.get(TouchSensor.class, "ms");
//        lifts.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        lift1 = getMotor("l1", DcMotorSimple.Direction.REVERSE);
//        lift2 = getMotor("l2", DcMotorSimple.Direction.FORWARD);
//        clawRight = getServo("cr", Servo.Direction.FORWARD);
//        clawLeft = getServo("cl", Servo.Direction.REVERSE);
//        rotationCling = getMotor("rcl", DcMotorSimple.Direction.FORWARD);
//        clawRotationCling = getMotor("ccl", DcMotorSimple.Direction.REVERSE);
        setMotorsZeroPowerBehaviour(Stops.WHEELS, DcMotor.ZeroPowerBehavior.BRAKE);
        resetEncoders(Stops.ALL);
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

    // Method to reset encoders of specific motors (could be all of them)
    public void resetEncoders(Stops stop) {
        for (RegistryDcMotor motor : stop.motorList) {
            resetEncoder(motor);
        }
    }

    // Method to reset the encoder of one specific motor
    public void resetEncoder(RegistryDcMotor motor) {
        motor.setMode(RegistryDcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(RegistryDcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // Method to stop only certain motors (could be all of them)
    public void stopOnly(Stops stop) {
        for (RegistryDcMotor motor : stop.motorList) {
            motor.setPower(0);
        }
    }

    public void setMotorsModes(DcMotor.RunMode mode, RegistryDcMotor... motors) {
        for (RegistryDcMotor motor : motors) {
            motor.setMode(mode);
        }
    }

    public void setMotorsModes(Stops stop, DcMotor.RunMode mode) {
        for (RegistryDcMotor motor : stop.motorList) {
            motor.setMode(mode);
        }
    }

    public void setMotorsZeroPowerBehaviour(Stops stop, DcMotor.ZeroPowerBehavior powerBehavior) {
        for (RegistryDcMotor motor : stop.motorList) {
            motor.setZeroPowerBehavior(powerBehavior);
        }
    }

    public static void runMotors(double frontLeft, double frontRight, double rearLeft, double rearRight) {
        frontLeftDrive.setPower(frontLeft);
        frontRightDrive.setPower(frontRight);
        rearLeftDrive.setPower(rearLeft);
        rearRightDrive.setPower(rearRight);
    }

    // Method to move the robot in a specific direction
    public void moveDirection(Direction direction, double speed) {
        switch (direction) {
            case FORWARD:
                for (RegistryDcMotor motor : Stops.WHEELS.motorList) {
                    motor.setPower(speed);
                }
                break;
            case BACKWARD:
                for (RegistryDcMotor motor : Stops.WHEELS.motorList) {
                    motor.setPower(-speed);
                }
                break;
            case LEFT:
                for (RegistryDcMotor motor : Stops.WHEELS.motorList) {
                    if (motor == frontLeftDrive || motor == rearRightDrive) {
                        motor.setPower(speed);
                    } else {
                        motor.setPower(-speed);
                    }
                }
                break;
            case RIGHT:
                for (RegistryDcMotor motor : Stops.WHEELS.motorList) {
                    if (motor == frontRightDrive || motor == rearLeftDrive) {
                        motor.setPower(speed);
                    } else {
                        motor.setPower(-speed);
                    }
                }
                break;
        }
    }

//    void moveLifts(double speed) {
//        for (DcMotor motor : Stops.LIFTS.motorList) {
//            motor.setPower(speed);
//        }
//    }
//
//    public void liftsUp(float speed) {
//        if (!(Math.abs(lift1.getCurrentPosition()) >= LIFT_MAX_POSITION) || !(Math.abs(lift2.getCurrentPosition()) >= LIFT_MAX_POSITION)) {
//            moveLifts(speed);
//        }
//    }
//
//    public void liftsDown(float speed) {
//        moveLifts(-speed);
//    }

//    public void clingsUp(float speed) {
//        moveClings(speed);
//    }
//
//    public void clingsDown(float speed) {
//        moveClings(-speed);
//    }

//    public void moveClings(float speed) {
//        rotationCling.setPower(speed);
//    }

//    public void closeClaws() {
//        setClaw(clawLeft, ClawState.CLOSE);
//        setClaw(clawRight, ClawState.CLOSE);
//    }
//    public void openClaws() {
//        setClaw(clawLeft, ClawState.OPEN);
//        setClaw(clawRight, ClawState.OPEN);
//    }
//    public void setClaw(Servo claw, ClawState clawState) {
//        claw.setPosition(clawState == ClawState.OPEN ? 0 : 0.5);
//    }
}
