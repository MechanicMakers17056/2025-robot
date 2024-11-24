package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.registry_stuff.RegistryLinearOpMode;
import org.firstinspires.ftc.teamcode.teleop.button.PressAndReleaseButton;

@TeleOp(name = "Servo Testing", group = "TeleOp")
public class ServoTesting extends RegistryLinearOpMode {
    private static final PressAndReleaseButton toggleable = new PressAndReleaseButton();
    private static final PressAndReleaseButton toggleable2 = new PressAndReleaseButton();

    @Override
    public void runCode() {
        if(gamepad1.left_trigger <= 0)
            servo.setPower(gamepad1.right_trigger);
        if(gamepad1.right_trigger <= 0)
            servo.setPower(-gamepad1.left_trigger);
        if(gamepad1.left_trigger <= 0 && gamepad1.right_trigger <= 0) {
            servo.setPower(0);
        }
    }
}
