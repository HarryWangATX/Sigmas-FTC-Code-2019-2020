package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SigmaDrive;

/*
 * This is a simple routine to test turning capabilities. If this is consistently overshooting or
 * undershooting by a significant amount, re-run OldTrackWidthTuner.
 */
@Autonomous(group = "drive")
public class TurnTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SigmaDrive drive = new SigmaDrive(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        drive.turnSync(Math.toRadians(90));
    }
}
