package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.registry_stuff.RegistryLinearOpMode;

@TeleOp(name = "Full", group = "TeleOp")
public class FullRobot extends RegistryLinearOpMode {

    public static final double FRONTERS_SPEED = 1;

    @Override
    public void runCode() {
        Moving.speed(gamepad1, false);
        if(gamepad1.left_trigger <= 0)
            lifts.setPower(gamepad1.right_trigger);
        if(gamepad1.right_trigger <= 0)
            lifts.setPower(-gamepad1.left_trigger);
        if(gamepad1.left_trigger <= 0 && gamepad1.right_trigger <= 0) {
            lifts.setPower(0);
        }

        if(gamepad1.right_bumper) {
            fronters.setPower(FRONTERS_SPEED);
        }
        if(gamepad1.left_bumper) {
            fronters.setPower(-FRONTERS_SPEED);
        }
        if(!(gamepad1.right_bumper && gamepad1.left_bumper)) {
            fronters.setPower(0);
        }
    }
}
