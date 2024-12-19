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
    // minimum is 5 for error range
    private static final int ERROR_RANGE = 5;

    private final PressAndReleaseButton increaseTargetAngleButton = new PressAndReleaseButton();
    private final PressAndReleaseButton decreaseTargetAngleButton = new PressAndReleaseButton();
    private final PressAndReleaseButton moveToAngleButton = new PressAndReleaseButton();

    private int targetAngle = 0;
    private boolean shouldMoveToAngle = false;

    @Override
    public void runCode() {
        increaseTargetAngleButton.tick(gamepad1.square, () -> targetAngle++);
        decreaseTargetAngleButton.tick(gamepad1.circle, () -> targetAngle--);
        moveToAngleButton.tick(gamepad1.triangle, () -> shouldMoveToAngle = !shouldMoveToAngle);

        if (shouldMoveToAngle) {
            moveToAngle(targetAngle);
        } else {
            encoderMotor.setPower(gamepad1.left_stick_x);
        }

        int currentAngle = getScaledAngle(encoderMotor.getCurrentPosition());
        telemetry.addData("Rotations", currentAngle);
        telemetry.addData("Target Angle", targetAngle);
        telemetry.update();
    }

    private void moveToAngle(int desiredAngle) {
        int currentAngle = getScaledAngle(encoderMotor.getCurrentPosition());
        if (currentAngle == desiredAngle) {
            if (encoderMotor.getPower() < 0.05)
                shouldMoveToAngle = false;
            encoderMotor.setPower(0);
            return;
        }

        int negative = currentAngle < 0 ? -1 : 1;
        boolean shouldMoveRight = currentAngle < desiredAngle;
        boolean shouldMoveLeft = currentAngle > desiredAngle;

        if (negative == 1 ? shouldMoveRight : shouldMoveLeft) {
            encoderMotor.setPower(SPEED * negative);
        } else {
            encoderMotor.setPower((Math.abs(Math.abs(desiredAngle) - Math.abs(currentAngle)) > ERROR_RANGE ? -SPEED : -ERROR_SPEED) * negative);
        }
    }

    // doesn't work for new claw ):
    // i did math for nothing
    private static int getSwerveAngle(int currentAngle, int desiredAngle) {
        int directAngle = (desiredAngle - currentAngle + 180) % 360 - 180;
        int reverseAngle = (desiredAngle - currentAngle + 360) % 360 - 180;
        return currentAngle + Math.abs(directAngle) <= Math.abs(reverseAngle) ? directAngle : reverseAngle;
    }

    // converts the encoder rotations to [0, 360] format and then into a [0, 90, 180, -90] format
    private static int getScaledAngle(int angle) {
        int actualAngle = angle / REVOLUTION_MARGIN;
        return actualAngle * -1;
    }
}