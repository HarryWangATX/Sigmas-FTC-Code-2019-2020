package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.SigmaDrive;

import java.util.Arrays;

@Config
@TeleOp(group="opmode")
public class VroomZoom extends LinearOpMode {
    private SigmaDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SigmaDrive(hardwareMap);
        telemetry.addData("Initialized...","");
        telemetry.update();

        waitForStart();

        if(isStopRequested()) return;

        while(opModeIsActive() && !isStopRequested())
        {
            //START OF GAMEPAD1 CONTROL
            double lf = gamepad1.left_stick_y - (gamepad1.left_stick_x)  - gamepad1.right_stick_x;
            double rf = gamepad1.left_stick_y  + (gamepad1.left_stick_x) + gamepad1.right_stick_x;
            double lr = gamepad1.left_stick_y  + (gamepad1.left_stick_x)  - gamepad1.right_stick_x;
            double rr = gamepad1.left_stick_y - (gamepad1.left_stick_x) + gamepad1.right_stick_x;

            //Move range to between 0 and +1, if not already
            double[] wheelPowers = {rf, lf, lr, rr};
            Arrays.sort(wheelPowers);
            if (wheelPowers[3] > 1) {
                lf /= wheelPowers[3];
                rf /= wheelPowers[3];
                lr /= wheelPowers[3];
                rr /= wheelPowers[3];
            }

            telemetry.addData("Motor positions", (drive.getWheelPositions()));
            telemetry.update();

            drive.setMotorPowers(lf,lr,rr,rf);
            //END OF GAMEPAD1 CONTROL
        }
    }
}
