package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.enums.Direction;
import org.firstinspires.ftc.teamcode.enums.Stops;
import org.firstinspires.ftc.teamcode.registry_stuff.AutonomousRegistryLinearOpMode;
import org.firstinspires.ftc.teamcode.registry_stuff.registry_hardware.RegistryDcMotor;


@Autonomous(name = "Encoder Drive", group = "Autonomous")
public class EncoderDrive extends AutonomousRegistryLinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();

    // Calculate the COUNTS_PER_INCH for your specific drive train.
    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
    // This is gearing DOWN for less speed and more torque.
    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
    static final double COUNTS_PER_MOTOR_REV = 537.6;
    static final double DRIVE_GEAR_REDUCTION = 1;  // No External Gearing.
    static final double WHEEL_DIAMETER_INCH = 3.76; // For figuring circumference
    static final double COUNTS_PER_CM = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCH * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;

    @Override
    public void initOpMode() {
        resetEncoders(Stops.WHEELS);
        setMotorsModes(Stops.WHEELS, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void runCode() {
        encoderDrive(Direction.FORWARD, DRIVE_SPEED, 90, 5.0);
//        encoderDrive(Direction.LEFT, DRIVE_SPEED, 10, 5.0);
        waitFor(1);
        encoderDrive(Direction.BACKWARD, DRIVE_SPEED, 20, 5.0);

//        encoderDrive(Direction.BACKWARD, DRIVE_SPEED, 10, 4.0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    public int getDesiredPosition(RegistryDcMotor motor, double distance) {
        return motor.getCurrentPosition() + (int) (distance * COUNTS_PER_CM);
    }

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the OpMode running.
     */
    public void encoderDrive(Direction direction, double speed,
                             double distance,
                             double timeoutS) {
        switch (direction) {
            case FORWARD:
                for (RegistryDcMotor motor : Stops.WHEELS.motorList) {
                    motor.setTargetPosition(getDesiredPosition(motor, distance));
                }
                break;
            case BACKWARD:
                for (RegistryDcMotor motor : Stops.WHEELS.motorList) {
                    motor.setTargetPosition(-getDesiredPosition(motor, distance));
                }
                break;
            case LEFT:
                for (RegistryDcMotor motor : Stops.WHEELS.motorList) {
                    if (motor == frontLeftDrive || motor == rearRightDrive) {
                        motor.setTargetPosition(getDesiredPosition(motor, distance));
                    } else {
                        motor.setTargetPosition(-getDesiredPosition(motor, distance));
                    }
                }
            case RIGHT:
                for (RegistryDcMotor motor : Stops.WHEELS.motorList) {
                    if (motor == frontRightDrive || motor == rearLeftDrive) {
                        motor.setTargetPosition(getDesiredPosition(motor, distance));
                    } else {
                        motor.setTargetPosition(-getDesiredPosition(motor, distance));
                    }
                }
                break;
        }

        setMotorsModes(Stops.WHEELS, DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        runtime.reset();
        runMotors(Math.abs(speed), Math.abs(speed), Math.abs(speed), Math.abs(speed));

        // keep looping while we are still active, and there is time left, and both motors are running.
        // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
        // its target position, the motion will stop.  This is "safer" in the event that the robot will
        // always end the motion as soon as possible.
        // However, if you require that BOTH motors have finished their moves before the robot continues
        // onto the next step, use (isBusy() || isBusy()) in the loop test.
        while ((runtime.seconds() < timeoutS) &&
                (frontLeftDrive.isBusy() || frontRightDrive.isBusy()
                        || rearLeftDrive.isBusy() || rearRightDrive.isBusy())) {
            // Display it for the driver.
            telemetry.addData("Front Left",
                    frontLeftDrive.getCurrentPosition());
            telemetry.addData("Rear Left",
                    rearLeftDrive.getCurrentPosition());
            telemetry.addData("Front Right",
                    frontRightDrive.getCurrentPosition());
            telemetry.addData("Rear Right",
                    rearRightDrive.getCurrentPosition());
            telemetry.update();
        }
//        if (
//                (runtime.seconds() < timeoutS) &&
//                        (frontLeftDrive.isBusy() && frontRightDrive.isBusy())) {
//
//            // Display it for the driver.
//            telemetry.addData("Running to", " %7d :%7d", newLeftTarget, newRightTarget);
//            telemetry.addData("Currently at", " at %7d :%7d",
//                    frontLeftDrive.getCurrentPosition(), frontRightDrive.getCurrentPosition());
//            telemetry.update();
//        }

        stopOnly(Stops.WHEELS);


        // Turn off RUN_TO_POSITION
        setMotorsModes(Stops.WHEELS, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void encoderRotate(Direction direction, double speed,
                              double distance,
                              double timeoutS) {
        switch (direction) {
            case LEFT:
                for (DcMotor motor : Stops.WHEELS.motorList) {
                    if (motor == frontLeftDrive || motor == rearLeftDrive) {
                        motor.setPower(speed);
                    } else {
                        motor.setPower(-speed);
                    }
                }
                break;
            case RIGHT:
                for (DcMotor motor : Stops.WHEELS.motorList) {
                    if (motor == frontRightDrive || motor == rearRightDrive) {
                        motor.setPower(speed);
                    } else {
                        motor.setPower(-speed);
                    }
                }
                break;
        }
        while ((runtime.seconds() < timeoutS) &&
                (frontLeftDrive.isBusy() && frontRightDrive.isBusy()
                        && rearLeftDrive.isBusy() && rearRightDrive.isBusy())) {
            // Display it for the driver.
            telemetry.addData("Front Left",
                    frontLeftDrive.getCurrentPosition());
            telemetry.addData("Rear Left",
                    rearLeftDrive.getCurrentPosition());
            telemetry.addData("Front Right",
                    frontRightDrive.getCurrentPosition());
            telemetry.addData("Rear Right",
                    rearRightDrive.getCurrentPosition());
            telemetry.update();
        }

        stopOnly(Stops.WHEELS);

        // Turn off RUN_TO_POSITION
        setMotorsModes(Stops.WHEELS, DcMotor.RunMode.RUN_TO_POSITION);
    }
}
