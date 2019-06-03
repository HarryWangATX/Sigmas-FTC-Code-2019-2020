package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SigmaDriveBase;

/*
 * Similar to the deprecated OldTrackWidthTuner, this routine attempts to automagically
 * determine the drive track width. The basic idea is to use a motion profile to rotate the robot
 * a certain circumferential distance and compare it to the angle swept out (as measured by the
 * IMU). For robustness, this procedure is repeated a few times, and the final track width is
 * averaged over those runs.
 */
@Config
@Autonomous(group="opmode")
public class TeleOp extends LinearOpMode {
    private SigmaDriveBase drive;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SigmaDriveBase(hardwareMap);

        while(opModeIsActive())
        {

        }
    }
}
