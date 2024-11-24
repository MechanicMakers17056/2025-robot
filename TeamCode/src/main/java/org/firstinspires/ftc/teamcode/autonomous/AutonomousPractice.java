package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.enums.Direction;
import org.firstinspires.ftc.teamcode.registry_stuff.AutonomousRegistryLinearOpMode;

@Autonomous(name = "AutonomousPractice", group = "Autonomous")
public class AutonomousPractice extends AutonomousRegistryLinearOpMode {
    public void runCode() {
        moveDirection(Direction.FORWARD, 0.5, 3);
    }
}