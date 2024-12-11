package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.registry_stuff.RegistryLinearOpMode;

@TeleOp(name = "Full", group = "TeleOp")
public class FullRobot extends RegistryLinearOpMode {

    public static final double FRONTERS_SPEED = 1;

    @Override
    public void runCode() {
        Moving.speed(gamepad1, gamepad1.cross);
        DifferentialTest.differential(gamepad1);
        if(gamepad1.left_trigger <= 0)
            fronters.setPower(gamepad1.right_trigger);
        if(gamepad1.right_trigger <= 0)
            fronters.setPower(-gamepad1.left_trigger);
        if(gamepad1.left_trigger <= 0 && gamepad1.right_trigger <= 0) {
            fronters.setPower(0);
        }

//        if(gamepad1.right_bumper) {
//            fronters.setPower(FRONTERS_SPEED);
//        }
//        if(gamepad1.left_bumper) {
//            fronters.setPower(-FRONTERS_SPEED);
//        }
//        if(!gamepad1.right_bumper && !gamepad1.left_bumper) {
//            fronters.setPower(0);
//        }

        telemetry.addData("Lifts Speed", lifts.getPower());
        telemetry.addData("Fronters Speed", fronters.getPower());
        telemetry.addData("Differential Speed", DifferentialTest.speed);
        telemetry.addData("Differential Right Speed", rightServo.getPower());
        telemetry.addData("Differential Left Speed", leftServo.getPower());
        telemetry.update();
    }
}
