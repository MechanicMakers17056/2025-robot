package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.registry_stuff.RegistryLinearOpMode;
import org.firstinspires.ftc.teamcode.teleop.button.PressAndReleaseButton;
import org.firstinspires.ftc.teamcode.teleop.button.buttons.HoldButton;
import org.firstinspires.ftc.teamcode.teleop.button.buttons.MultipleClicksButton;
import org.firstinspires.ftc.teamcode.teleop.button.buttons.MultipleInputsButton;

@TeleOp(name = "Button Testing", group = "TeleOp")
public class ButtonTesting extends RegistryLinearOpMode {
    private final PressAndReleaseButton pressAndReleaseButton = new PressAndReleaseButton();
    private final HoldButton holdButton = new HoldButton(1);
    private final MultipleClicksButton multipleClicksButton = new MultipleClicksButton();
    private final MultipleInputsButton multipleInputsButton = new MultipleInputsButton();

    @Override
    public void runCode() {
        pressAndReleaseButton.tick(gamepad1.right_bumper, () -> {
            telemetry.addLine("Press And Release Button Working!");
            gamepad1.rumble(500);
            telemetry.update();
        });

        holdButton.tick(gamepad1.circle, () -> {
            telemetry.addLine("Hold Button Working!");
            gamepad1.rumble(500);
            telemetry.update();
        });

        multipleClicksButton.tick(gamepad1.right_trigger > 0, () -> {
            telemetry.addLine("Multiple Clicks Button Working!");
            gamepad1.rumble(500);
            telemetry.update();
        });

        multipleInputsButton.tick(() -> {
            telemetry.addLine("Multiple Inputs Button Working!");
            gamepad1.rumble(500);
            telemetry.update();
        }, gamepad1.left_trigger > 0, gamepad1.right_trigger > 0);
    }
}
