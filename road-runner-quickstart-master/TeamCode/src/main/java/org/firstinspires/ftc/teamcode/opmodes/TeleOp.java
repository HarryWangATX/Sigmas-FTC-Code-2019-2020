package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;

public abstract class TeleOp extends LinearOpMode {
    boolean useAutoPos = true;

    Robot robot;

    public void logData()
    {
        telemetry.addData("Robot position: ",robot.drive.getPoseEstimate());
        telemetry.addData("Motor positions", robot.drive.getWheelPositions());
        telemetry.update();
    }
}
