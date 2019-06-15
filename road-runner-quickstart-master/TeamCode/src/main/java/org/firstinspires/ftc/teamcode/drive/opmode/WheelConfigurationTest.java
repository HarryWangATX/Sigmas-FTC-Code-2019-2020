package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.Drivebase;
import org.firstinspires.ftc.teamcode.drive.SigmaDrive;

@TeleOp(group = "drive")
public class WheelConfigurationTest extends LinearOpMode {
    private boolean leftFront = false;
    private boolean leftRear = false;
    private boolean rightFront = false;
    private boolean rightRear = false;
    private final double POWER = .2;

    @Override
    public void runOpMode() throws InterruptedException {

        Drivebase drive = new SigmaDrive(hardwareMap);

        telemetry.log().add("Press play to begin the wheel configuration tester");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        telemetry.log().clear();
        telemetry.log().add("Would you like to test leftFront?");
        telemetry.log().add("Press (A) for yes, (B) for no");
        telemetry.update();

        while (!isStopRequested()) {
            if (gamepad1.a) {
                while (!isStopRequested() && gamepad1.a) {
                    leftFront = true;
                    idle();
                }
                break;
            } else if (gamepad1.b) {
                while (!isStopRequested() && gamepad1.b) {
                    idle();
                }
                break;
            }
            idle();
        }

        telemetry.log().clear();
        telemetry.log().add("Would you like to test leftRear?");
        telemetry.log().add("Press (A) for yes, (B) for no");
        telemetry.update();

        while (!isStopRequested()) {
            if (gamepad1.a) {
                while (!isStopRequested() && gamepad1.a) {
                    leftRear = true;
                    idle();
                }
                break;
            } else if (gamepad1.b) {
                while (!isStopRequested() && gamepad1.b) {
                    idle();
                }
                break;
            }
            idle();
        }

        telemetry.log().clear();
        telemetry.log().add("Would you like to test rightFront?");
        telemetry.log().add("Press (A) for yes, (B) for no");
        telemetry.update();

        while (!isStopRequested()) {
            if (gamepad1.a) {
                while (!isStopRequested() && gamepad1.a) {
                    rightFront = true;
                    idle();
                }
                break;
            } else if (gamepad1.b) {
                while (!isStopRequested() && gamepad1.b) {
                    idle();
                }
                break;
            }
            idle();
        }

        telemetry.log().clear();
        telemetry.log().add("Would you like to test rightRear?");
        telemetry.log().add("Press (A) for yes, (B) for no");
        telemetry.update();

        while (!isStopRequested()) {
            if (gamepad1.a) {
                while (!isStopRequested() && gamepad1.a) {
                    rightRear = true;
                    idle();
                }
                break;
            } else if (gamepad1.b) {
                while (!isStopRequested() && gamepad1.b) {
                    idle();
                }
                break;
            }
            idle();
        }

        telemetry.log().clear();
        telemetry.log().add("Place your robot on the field");
        telemetry.log().add("Press (A) to begin");
        telemetry.update();

        while (!isStopRequested() && !gamepad1.a) {
            idle();
        }
        while (!isStopRequested() && gamepad1.a) {
            idle();
        }

        if(leftFront)
        {
            telemetry.log().clear();
            telemetry.log().add("Running leftFront...");
            telemetry.log().add("Press (B) to stop");
            telemetry.update();

            while(!isStopRequested() && !gamepad1.b)
            {
                drive.setMotorPowers(POWER,0,0,0);
            }

            drive.setMotorPowers(0,0,0,0);
            telemetry.log().clear();
            telemetry.log().add("Cooling down...");

            sleep(1500);
        }

        if(leftRear)
        {
            telemetry.log().clear();
            telemetry.log().add("Running leftRear...");
            telemetry.log().add("Press (B) to stop");
            telemetry.update();

            while(!isStopRequested() && !gamepad1.b)
            {
                drive.setMotorPowers(0,POWER,0,0);
            }

            drive.setMotorPowers(0,0,0,0);
            telemetry.log().clear();
            telemetry.log().add("Cooling down...");

            sleep(1500);
        }

        if(rightRear)
        {
            telemetry.log().clear();
            telemetry.log().add("Running rightRear...");
            telemetry.log().add("Press (B) to stop");
            telemetry.update();

            while(!isStopRequested() && !gamepad1.b)
            {
                drive.setMotorPowers(0,0,POWER,0);
            }

            drive.setMotorPowers(0,0,0,0);
            telemetry.log().clear();
            telemetry.log().add("Cooling down...");

            sleep(1500);
        }

        if(rightFront)
        {
            telemetry.log().clear();
            telemetry.log().add("Running rightFront...");
            telemetry.log().add("Press (B) to stop");
            telemetry.update();

            while(!isStopRequested() && !gamepad1.b)
            {
                drive.setMotorPowers(0,0,0,POWER);
            }

            drive.setMotorPowers(0,0,0,0);
        }

    }
}

