package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.drive.SigmaDrive;

import java.util.Arrays;

@Config
@TeleOp(group="opmode")
public class VroomZoom extends org.firstinspires.ftc.teamcode.opmodes.TeleOp {

    @Override
    public void runOpMode() throws InterruptedException {
        /*
          _____   _____  _______ _____  _____  __   _ _______
         |     | |_____]    |      |   |     | | \  | |______
         |_____| |          |    __|__ |_____| |  \_| ______|

         */

        useAutoPos = true;

        /*
         _____ __   _ _____ _______
           |   | \  |   |      |
         __|__ |  \_| __|__    |

         */

        if(!useAutoPos) { robot = new Robot(hardwareMap); }

        telemetry.addData("Initialized...","");
        telemetry.update();

        waitForStart();

        if(isStopRequested()) return;

        while(!isStopRequested())
        {
            //START OF GAMEPAD1 CONTROL
            robot.drive.driveTeleOp(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x);
            //END OF GAMEPAD1 CONTROL

            //LOGGING
            endLoop();
        }
    }
}
