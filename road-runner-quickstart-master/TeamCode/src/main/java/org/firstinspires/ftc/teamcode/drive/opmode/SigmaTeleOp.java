package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.SigmaDriveBase;
import org.firstinspires.ftc.teamcode.gamepad.SigmaGamepad;

@Config
@TeleOp(group="opmode")
public class SigmaTeleOp extends LinearOpMode {
    private SigmaDriveBase drive;
    private SigmaGamepad pad1;
    private SigmaGamepad pad2;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SigmaDriveBase(hardwareMap);
        pad1 = new SigmaGamepad(gamepad1);
        pad2 = new SigmaGamepad(gamepad2);

        while(opModeIsActive())
        {
            //START OF GAMEPAD1 CONTROL
            double r = Math.hypot(pad1.left_stick_x, pad1.left_stick_y);
            double robotAngle = Math.atan2(pad1.left_stick_y, pad1.left_stick_x) - Math.PI / 4;
            double rightX = pad1.right_stick_x;
            final double v = r * Math.cos(robotAngle) + rightX;
            final double v1 = r * Math.sin(robotAngle) - rightX;
            final double v2 = r * Math.sin(robotAngle) + rightX;
            final double v3 = r * Math.cos(robotAngle) - rightX;

            drive.setMotorPowers(v,v1,v2,v3);
            //END OF GAMEPAD1 CONTROL
        }
    }
}
