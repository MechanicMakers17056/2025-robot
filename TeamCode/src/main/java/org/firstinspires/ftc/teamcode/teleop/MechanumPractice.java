package org.firstinspires.ftc.teamcode.teleop;

import static androidx.core.math.MathUtils.clamp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "MechanumPractice", group = "TeleOp")
public class MechanumPractice extends LinearOpMode {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor rearLeft;
    DcMotor rearRight;
    public static final double MAX_SPEED = 1;
    public static final double MIN_SPEED = 0.8;
    public double speed = 0.8;
    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "fl");
        frontRight = hardwareMap.get(DcMotor.class, "fr");
        rearLeft = hardwareMap.get(DcMotor.class, "rl");
        rearRight = hardwareMap.get(DcMotor.class, "rr");

        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        rearLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        rearRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()) {
            double x = -gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            double r = -gamepad1.right_stick_x;
            boolean isStickPressed = gamepad1.left_stick_button;

            speed = isStickPressed ? MAX_SPEED : MIN_SPEED;

            frontLeft.setPower(clamp((y + x + r), -speed, speed));
            frontRight.setPower(clamp((y - x - r), -speed, speed));
            rearLeft.setPower(clamp((y - x + r), -speed, speed));
            rearRight.setPower(clamp((y + x - r), -speed, speed));
        }
    }
}
