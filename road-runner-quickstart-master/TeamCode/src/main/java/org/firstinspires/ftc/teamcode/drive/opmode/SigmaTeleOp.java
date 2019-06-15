package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.SigmaDrive;

@Config
@TeleOp(group="opmode")
public class SigmaTeleOp extends LinearOpMode {
    private SigmaDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SigmaDrive(hardwareMap);

        while(opModeIsActive())
        {
            //START OF GAMEPAD1 CONTROL
            double r = Math.hypot(gamepad1.left_stick_x, gamepad1.right_stick_x);
            double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.right_stick_x) - Math.PI / 4;
            double rightX = gamepad1.left_stick_x;
            final double v = r * Math.cos(robotAngle) + rightX;
            final double v1 = r * Math.sin(robotAngle) - rightX;
            final double v2 = r * Math.sin(robotAngle) + rightX;
            final double v3 = r * Math.cos(robotAngle) - rightX;

            drive.setMotorPowers(v,v1,v2,v3);
            //END OF GAMEPAD1 CONTROL
        }
    }
}
