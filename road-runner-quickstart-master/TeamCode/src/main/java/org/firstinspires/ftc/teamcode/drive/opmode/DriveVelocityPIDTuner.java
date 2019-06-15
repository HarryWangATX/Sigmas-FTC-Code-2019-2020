package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.config.ValueProvider;
import com.acmerobotics.dashboard.config.variable.BasicVariable;
import com.acmerobotics.dashboard.config.variable.CustomVariable;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.profile.MotionProfile;
import com.acmerobotics.roadrunner.profile.MotionProfileGenerator;
import com.acmerobotics.roadrunner.profile.MotionState;
import com.acmerobotics.roadrunner.util.NanoClock;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SigmaDrive;

import java.util.ArrayList;
import java.util.List;

/*
 * This routine is designed to tune the PID coefficients used by the REV Expansion Hubs for closed-
 * loop velocity control. Although it may seem unnecessary, tuning these coefficients is just as
 * important as the positional parameters. Like the other manual tuning routines, this op mode
 * relies heavily upon the dashboard. To access the dashboard, connect your computer to the RC's
 * WiFi network and navigate to https://192.168.49.1:8080/dash in your browser. Once you've
 * successfully connected, start the program, and your robot will begin moving forward and backward
 * according to a motion profile. Your job is to graph the velocity errors over time and adjust the
 * PID coefficients (it's highly suggested to leave F at its default value) like any normal PID
 * controller. Once you've found a satisfactory set of gains, add them to your drive class init.
 */
@Config
@Autonomous(group = "drive")
public class DriveVelocityPIDTuner extends LinearOpMode {
    public static double DISTANCE = 50;

    /*
     * If true, the kV value is computed from the free speed determined by the manufacturer (likely
     * an overestimate of the actual value. If false, the value from DriveConstants.kV is used.
     */
    public static boolean USE_THEORETICAL_KV = true;

    private FtcDashboard dashboard = FtcDashboard.getInstance();
    private CustomVariable catVar;

    private SigmaDrive drive;

    private static MotionProfile generateProfile(boolean movingForward) {
        MotionState start = new MotionState(movingForward ? 0 : DISTANCE, 0, 0, 0);
        MotionState goal = new MotionState(movingForward ? DISTANCE : 0, 0, 0, 0);
        return MotionProfileGenerator.generateSimpleMotionProfile(start, goal,
                DriveConstants.BASE_CONSTRAINTS.maxVel, DriveConstants.BASE_CONSTRAINTS.maxAccel);
    }

    private void addPidVariable() {
        String catName = getClass().getSimpleName();
        catVar = (CustomVariable) dashboard.getConfigRoot().getVariable(catName);
        if (catVar == null) {
            // this should never happen...
            catVar = new CustomVariable();
            dashboard.getConfigRoot().putVariable(catName, catVar);
        }
        CustomVariable pidVar = new CustomVariable();
        pidVar.putVariable("kP", new BasicVariable<>(new ValueProvider<Double>() {
            @Override
            public Double get() {
                return drive.getPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER).kP;
            }

            @Override
            public void set(Double value) {
                PIDCoefficients coeffs = drive.getPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
                drive.setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                        new PIDCoefficients(value, coeffs.kI, coeffs.kD));
            }
        }));
        pidVar.putVariable("kI", new BasicVariable<>(new ValueProvider<Double>() {
            @Override
            public Double get() {
                return drive.getPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER).kI;
            }

            @Override
            public void set(Double value) {
                PIDCoefficients coeffs = drive.getPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
                drive.setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                        new PIDCoefficients(coeffs.kP, value, coeffs.kD));
            }
        }));
        pidVar.putVariable("kD", new BasicVariable<>(new ValueProvider<Double>() {
            @Override
            public Double get() {
                return drive.getPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER).kD;
            }

            @Override
            public void set(Double value) {
                PIDCoefficients coeffs = drive.getPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
                drive.setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                        new PIDCoefficients(coeffs.kP, coeffs.kI, value));
            }
        }));
        catVar.putVariable("VELO_PID", pidVar);
        dashboard.updateConfig();
    }

    private void removePidVariable() {
        catVar.removeVariable("VELO_PID");
        dashboard.updateConfig();
    }

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        drive = new SigmaDrive(hardwareMap);

        addPidVariable();

        NanoClock clock = NanoClock.system();

        telemetry.log().add("Ready!");
        telemetry.update();
        telemetry.clearAll();

        waitForStart();

        if (isStopRequested()) return;

        boolean movingForwards = true;
        MotionProfile activeProfile = generateProfile(true);
        double profileStartTimestamp = clock.seconds();

        List<Double> lastWheelPositions = null;
        double lastTimestamp = 0;

        double maxVel = DriveConstants.rpmToVelocity(DriveConstants.getMaxRpm());
        double kV = USE_THEORETICAL_KV ? (1.0 / maxVel) : DriveConstants.kV;

        while (!isStopRequested()) {
            // calculate and set the motor power
            double profileTime = clock.seconds() - profileStartTimestamp;
            double dt = profileTime - lastTimestamp;
            lastTimestamp = profileTime;
            if (profileTime > activeProfile.duration()) {
                // generate a new profile
                movingForwards = !movingForwards;
                activeProfile = generateProfile(movingForwards);
                profileStartTimestamp = clock.seconds();
            }
            MotionState motionState = activeProfile.get(profileTime);
            double targetPower = kV * motionState.getV();
            drive.setDrivePower(new Pose2d(targetPower, 0, 0));

            List<Double> wheelPositions = drive.getWheelPositions();
            if (lastWheelPositions != null) {
                // compute velocities
                List<Double> syntheticVelocities = new ArrayList<>();
                for (int i = 0; i < wheelPositions.size(); i++) {
                    syntheticVelocities.add((wheelPositions.get(i) - lastWheelPositions.get(i)) / dt);
                }

                // update telemetry
                telemetry.addData("targetVelocity", motionState.getV());
                for (int i = 0; i < syntheticVelocities.size(); i++) {
                    telemetry.addData("velocity" + i, syntheticVelocities.get(i));
                    telemetry.addData("error" + i, motionState.getV() - syntheticVelocities.get(i));
                }
                telemetry.update();
            }
            lastWheelPositions = wheelPositions;
        }

        removePidVariable();
    }
}
