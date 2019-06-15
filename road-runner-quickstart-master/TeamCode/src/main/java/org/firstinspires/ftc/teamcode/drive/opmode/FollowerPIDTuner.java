package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.Drivebase;
import org.firstinspires.ftc.teamcode.drive.SigmaDrive;

/*
 * Op mode for tuning follower PID coefficients. This is the final step in the tuning process.
 */
@Autonomous(group = "drive")
public class FollowerPIDTuner extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Drivebase drive = new SigmaDrive(hardwareMap);

        drive.setPoseEstimate(new Pose2d(-24, -24, 0));



        waitForStart();

        if (isStopRequested()) return;

        while (!isStopRequested()) {
            drive.followTrajectorySync(
                    drive.trajectoryBuilder()
                            .forward(48)
                            .build()
            );
            drive.turnSync(Math.toRadians(90));
        }
    }
}
