package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.registry_stuff.RegistryLinearOpMode;

@TeleOp(name = "Magnet Testing", group = "TeleOp")
public class MagnetTesting extends RegistryLinearOpMode {

    @Override
    public void runCode() {
        telemetry.addData("Pressed", magnet.isPressed());
        telemetry.update();
    }
}
