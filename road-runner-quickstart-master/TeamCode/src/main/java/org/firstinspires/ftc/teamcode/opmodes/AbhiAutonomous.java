package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SigmaDrive;

import java.util.List;

@Autonomous(group="abhi")
public class AbhiAutonomous extends LinearOpMode {
    private SigmaDrive robot;



    @Override
    public void runOpMode() throws InterruptedException {
        robot = new SigmaDrive(hardwareMap);

        waitForStart();

        robot.setTargetPositions(-3852, -3852, -4000, -4000);
        robot.setMotorPowers(.5, .5, .5, .5);

        while(robot.motorsAreBusy())
        {

        }

        List<Double> positions = robot.getWheelPositions();

        robot.setTargetPositions((int)(positions.get(0) - 1132),(int)(positions.get(1) - 1132),(int)(positions.get(2) + 1000),(int)(positions.get(3) + 1000));
        robot.setMotorPowers(.5, .5, .5, .5);

        while(robot.motorsAreBusy())
        {

        }
        positions = robot.getWheelPositions();
        robot.setTargetPositions((int)(positions.get(0) - 900),(int)(positions.get(1) - 900),(int)(positions.get(2) - 900),(int)(positions.get(3) - 900));
        robot.setMotorPowers(.5, .5, .5, .5);

        while(robot.motorsAreBusy())
        {

        }


        telemetry.addData("Finished...","");
        telemetry.update();
    }
}
