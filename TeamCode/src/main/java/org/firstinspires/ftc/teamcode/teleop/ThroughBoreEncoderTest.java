package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.registry_stuff.RegistryLinearOpMode;

@TeleOp(name = "Through Bore Encoder Test", group = "TeleOp")
public class ThroughBoreEncoderTest extends RegistryLinearOpMode {

    public static int MIN_ANGLE = 0;
    public static int MAX_ANGLE = 360;

    @Override
    public void runCode() {
        int encoderTroughMotor = encoderMotor.getCurrentPosition();
        double encoderTroughAnalog = encoder.getVoltage() / encoder.getMaxVoltage() * 360;
//        telemetry.addData("Rotations", );
        telemetry.update();
    }
}

