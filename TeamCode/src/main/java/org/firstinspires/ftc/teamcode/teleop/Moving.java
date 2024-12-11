package org.firstinspires.ftc.teamcode.teleop;

import androidx.core.math.MathUtils;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.enums.Colors;
import org.firstinspires.ftc.teamcode.registry_stuff.RegistryLinearOpMode;
import org.firstinspires.ftc.teamcode.teleop.button.PressAndReleaseButton;
import org.firstinspires.ftc.teamcode.teleop.button.buttons.HoldButton;
import org.firstinspires.ftc.teamcode.teleop.button.buttons.MultipleClicksButton;
import org.firstinspires.ftc.teamcode.teleop.button.buttons.MultipleInputsButton;
import org.firstinspires.ftc.vision.VisionPortal;

import java.text.DecimalFormat;

@TeleOp(name = "Moving", group = "TeleOp")
public class Moving extends RegistryLinearOpMode {
    private static final double MAX_SPEED = 1;
    private static boolean reverse = false;
    private static final PressAndReleaseButton toggleableBumper = new PressAndReleaseButton();

    public void runCode() {
        speed(gamepad1, gamepad1.right_bumper);

        double frontLeftPower = frontLeftDrive.getPower();
        double frontRightPower = frontRightDrive.getPower();
        double rearLeftPower = rearLeftDrive.getPower();
        double rearRightPower = rearRightDrive.getPower();

        telemetry.addData("Front Left Speed", getFormattedSpeed(frontLeftPower));
        telemetry.addData("Front Right Speed", getFormattedSpeed(frontRightPower));
        telemetry.addData("Rear Left Speed", getFormattedSpeed(rearLeftPower));
        telemetry.addData("Rear Right speed", getFormattedSpeed(rearRightPower));
        telemetry.update();
    }

    private String getFormattedSpeed(double speed) {
        String format = speed == Math.floor(speed) ? "#" : "#.#";
        return new DecimalFormat(format).format(speed);
    }

    public static void speed(Gamepad gamepad, boolean reverseButton) {
        toggleableBumper.tick(reverseButton, () -> {
            reverse = !reverse;
            gamepad.rumble(100);
        });

        double y = (reverse ? -1 : 1) * gamepad.left_stick_y;
        double x = (reverse ? -1 : 1) * gamepad.left_stick_x;
        double r = gamepad.right_stick_x;

        double fl = getClampedSpeed(y - x - r);
        double fr = getClampedSpeed(y + x + r);
        double rl = getClampedSpeed(y + x - r);
        double rr = getClampedSpeed(y - x + r);

        runMotors(fl, fr, rl, rr);
    }

    private static double getClampedSpeed(double speed) {
        return MathUtils.clamp(speed, -MAX_SPEED, MAX_SPEED);
    }
}
