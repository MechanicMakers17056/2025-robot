package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.enums.DifferentialDirection;
import org.firstinspires.ftc.teamcode.registry_stuff.RegistryLinearOpMode;
import org.firstinspires.ftc.teamcode.teleop.button.PressAndReleaseButton;

@TeleOp(name = "Differential Test", group = "TeleOp")
public class DifferentialTest extends RegistryLinearOpMode {
    private static final double SPEED = 0.5;
    public static double speed = SPEED;

    private static final PressAndReleaseButton toggleableRightBumper = new PressAndReleaseButton();
    private static final PressAndReleaseButton toggleableLeftBumper = new PressAndReleaseButton();

    @Override
    public void runCode() {
        toggleableRightBumper.tick(gamepad1.circle, () -> {
            speed += 0.1;
        });
        
        toggleableLeftBumper.tick(gamepad1.square, () -> {
            speed -= 0.1;
        });
        
        if (gamepad1.dpad_right) {
            differentialMoveTo(DifferentialDirection.RIGHT, speed);
        }

        if (gamepad1.dpad_left) {
            differentialMoveTo(DifferentialDirection.LEFT, speed);
        }

        if (gamepad1.dpad_down) {
            differentialMoveTo(DifferentialDirection.DOWN, speed);
        }

        if (gamepad1.dpad_up) {
            differentialMoveTo(DifferentialDirection.UP, speed);
        }

        if(!gamepad1.dpad_right && !gamepad1.dpad_left && !gamepad1.dpad_down && !gamepad1.dpad_up) {
            rightServo.setPower(0);
            leftServo.setPower(0);
        }

        telemetry.addData("Speed", speed);
        telemetry.addData("Right", rightServo.getPower());
        telemetry.addData("Left", leftServo.getPower());
        telemetry.update();
    }

    public static void differential(Gamepad gamepad) {
        toggleableRightBumper.tick(gamepad.circle, () -> {
            speed += 0.1;
        });

        toggleableLeftBumper.tick(gamepad.square, () -> {
            speed -= 0.1;
        });

        if (gamepad.dpad_right) {
            differentialMoveTo(DifferentialDirection.RIGHT, speed);
        }

        if (gamepad.dpad_left) {
            differentialMoveTo(DifferentialDirection.LEFT, speed);
        }

        if (gamepad.dpad_down) {
            differentialMoveTo(DifferentialDirection.DOWN, speed);
        }

        if (gamepad.dpad_up) {
            differentialMoveTo(DifferentialDirection.UP, speed);
        }

        if(!gamepad.dpad_right && !gamepad.dpad_left && !gamepad.dpad_down && !gamepad.dpad_up) {
            rightServo.setPower(0);
            leftServo.setPower(0);
        }
    }

    private static void differentialMoveTo(DifferentialDirection direction, double speed) {
        switch (direction) {
            case RIGHT:
                rightServo.setPower(-speed);
                leftServo.setPower(speed);
                break;
            case LEFT:
                rightServo.setPower(speed);
                leftServo.setPower(-speed);
                break;
            case UP:
                rightServo.setPower(speed);
                leftServo.setPower(speed);
                break;
            case DOWN:
                rightServo.setPower(-speed);
                leftServo.setPower(-speed);
                break;
        }
    }
}
