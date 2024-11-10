package org.firstinspires.ftc.teamcode.enums;

import static org.firstinspires.ftc.teamcode.registry_stuff.RegistryLinearOpMode.frontLeftDrive;
import static org.firstinspires.ftc.teamcode.registry_stuff.RegistryLinearOpMode.frontRightDrive;
import static org.firstinspires.ftc.teamcode.registry_stuff.RegistryLinearOpMode.rearLeftDrive;
import static org.firstinspires.ftc.teamcode.registry_stuff.RegistryLinearOpMode.rearRightDrive;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.registry_stuff.registry_hardware.RegistryDcMotor;

// Enum that is used by stopOnly method to decide which one of the three sensor lists to stop, either the lifts, wheels or both of them
public enum Stops {
    ALL(new RegistryDcMotor[]{frontLeftDrive, frontRightDrive, rearLeftDrive, rearRightDrive}),
    WHEELS(new RegistryDcMotor[]{frontLeftDrive, frontRightDrive, rearLeftDrive, rearRightDrive});
//    CLINGS(new DcMotor[]{clawRotationCling, rotationCling}),
//    LIFTS(new DcMotor[]{lift1, lift2});
    public final RegistryDcMotor[] motorList;

    Stops(RegistryDcMotor[] motorList) {
        this.motorList = motorList;
    }
}
