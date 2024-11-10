package org.firstinspires.ftc.teamcode.registry_stuff;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.enums.Direction;
import org.firstinspires.ftc.teamcode.enums.Stops;
import org.firstinspires.ftc.teamcode.registry_stuff.registry_hardware.RegistryDcMotor;

public abstract class AutonomousRegistryLinearOpMode extends RegistryLinearOpMode {
//    public void liftsDown(float speed, float seconds) {
//        if (lift1.getCurrentPosition() > 0 && lift2.getCurrentPosition() > 0) {
//            moveLifts(-speed, seconds);
//        } else {
//            stopOnly(Stops.LIFTS);
//            resetEncoders(Stops.LIFTS);
//        }
//    }

//    public void liftsUp(double speed, double seconds) {
//        if (lift1.getCurrentPosition() < LIFT_MAX_POSITION ||
//                lift2.getCurrentPosition() < LIFT_MAX_POSITION) {
//            moveLifts(speed, seconds);
//        } else {
//            stopOnly(Stops.LIFTS);
//            resetEncoders(Stops.LIFTS);
//        }
//    }
//
//    public void resetLifts() {
//        if (lift1.getCurrentPosition() > 0 && lift2.getCurrentPosition() > 0) {
//            liftsDown(0.6f);
//        } else {
//            stopOnly(Stops.LIFTS);
//            resetEncoders(Stops.LIFTS);
//        }
//    }
//
//    protected void moveLifts(double speed, double seconds) {
//        moveLifts(speed);
//        waitAndStopOnly(Stops.LIFTS, seconds);
//    }

    // Method to move the robot in a specific direction
    public void moveDirection(Direction direction, double speed, double seconds) {
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
        waitAndStopOnly(Stops.WHEELS, seconds);
    }


    public void turn(Direction direction, double speed, double seconds) {
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
        waitAndStopOnly(Stops.WHEELS, seconds);
    }

    // Method to add a delay to the stopOnly method and wait a specific number of seconds and then stop the motors assigned to that method call
    public void waitAndStopOnly(Stops stop, double seconds) {
        waitFor(seconds);
        stopOnly(stop);
    }

    public void waitFor(double seconds) {
        sleep((long) (seconds * 1000L));
    }
}
