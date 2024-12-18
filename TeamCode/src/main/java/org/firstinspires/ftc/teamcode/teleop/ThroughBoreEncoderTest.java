package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.registry_stuff.RegistryLinearOpMode;
import org.firstinspires.ftc.teamcode.teleop.button.PressAndReleaseButton;

@TeleOp(name = "Through Bore Encoder Test", group = "TeleOp")
public class ThroughBoreEncoderTest extends RegistryLinearOpMode {
    // Encoder Through Voltage (doesn't work right): encoder.getVoltage() / encoder.getMaxVoltage() * 360;

    public static final double ERROR_SPEED = 0.05;
    public static final int REVOLUTION_MARGIN = 8192 / 360;
    private static final double SPEED = 0.5;
    private static final int MAX_RIGHT_DEGREES = -180;
    private static final int MAX_LEFT_DEGREES = 180;
    private static final int ERROR_RANGE = 150;

    private final PressAndReleaseButton triangle = new PressAndReleaseButton();
    private final PressAndReleaseButton square = new PressAndReleaseButton();
    private final PressAndReleaseButton circle = new PressAndReleaseButton();

    private int targetAngle = 0;

    @Override
    public void runCode() {
        square.tick(gamepad1.square, () -> targetAngle++);
        circle.tick(gamepad1.circle, () -> targetAngle--);
        triangle.tick(gamepad1.triangle, () -> {
            moveToAngle(targetAngle);
        });

        int encoderThroughMotor = getScaledAngle(encoderMotor.getCurrentPosition());
        telemetry.addData("Rotations", encoderThroughMotor);
        telemetry.addData("Target Angle", targetAngle);
        telemetry.update();
    }

    private void moveToAngle(int desiredAngle) {
        int currentAngle = getScaledAngle(encoderMotor.getCurrentPosition());
        if (currentAngle == desiredAngle) {
            encoderMotor.setPower(0);
            return;
        }

        int negative = desiredAngle < 0 ? -1 : 1;
        boolean shouldMoveRight = currentAngle < desiredAngle;
        boolean shouldMoveLeft = currentAngle > desiredAngle;

        if (negative == 1 ? shouldMoveRight : shouldMoveLeft) {
            encoderMotor.setPower(SPEED * negative);
        } else {
            encoderMotor.setPower((Math.abs(currentAngle) - Math.abs(desiredAngle) > ERROR_RANGE ? -SPEED : -ERROR_SPEED) * negative);
        }
    }

    // doesn't work for new claw ):
    // i did math for noting
    private static int getSwerveAngle(int currentAngle, int desiredAngle) {
        int directAngle = (desiredAngle - currentAngle + 180) % 360 - 180;
        int reverseAngle = (desiredAngle - currentAngle + 360) % 360 - 180;
        return currentAngle + Math.abs(directAngle) <= Math.abs(reverseAngle) ? directAngle : reverseAngle;
    }

    private static int getScaledAngle(int angle) {
        int actualDegrees = angle / REVOLUTION_MARGIN;
        return actualDegrees == MAX_LEFT_DEGREES ? MAX_LEFT_DEGREES : actualDegrees % MAX_LEFT_DEGREES * (actualDegrees > MAX_LEFT_DEGREES ? -1 : 1);
    }
}