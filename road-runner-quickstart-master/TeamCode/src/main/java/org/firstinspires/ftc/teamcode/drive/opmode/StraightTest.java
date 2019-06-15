package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SigmaDrive;

/*
 * This is a simple routine to test translational drive capabilities. If this is *consistently*
 * overshooting or undershooting by a significant amount, check the constants in the drive class.
 */
@Autonomous(group = "drive")
public class StraightTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SigmaDrive drive = new SigmaDrive(hardwareMap);

        Trajectory trajectory = drive.trajectoryBuilder()
                .forward(60)
                .build();

        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectorySync(trajectory);
    }
}
