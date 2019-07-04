package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.LynxOptimizedI2cFactory;
import org.jetbrains.annotations.NotNull;
import org.openftc.revextensions2.ExpansionHubEx;
import org.openftc.revextensions2.ExpansionHubMotor;
import org.openftc.revextensions2.RevBulkData;
import org.openftc.revextensions2.RevExtensions2;

import static org.firstinspires.ftc.teamcode.drive.DriveConstants.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Optimized mecanum drive implementation for REV ExHs. The time savings may significantly improve
 * trajectory following performance with moderate additional complexity.
 */
public class SigmaDrive extends Drivebase {
    private ExpansionHubEx hub;
    private ExpansionHubMotor leftFront, leftRear, rightRear, rightFront;
    private List<ExpansionHubMotor> motors;
    private BNO055IMU imu;

    public SigmaDrive(HardwareMap hardwareMap) {
        super();

        RevExtensions2.init();

        // TODO: adjust the names of the following hardware devices to match your configuration
        // for simplicity, we assume that the desired IMU and drive motors are on the same hub
        // if your motors are split between hubs, **you will need to add another bulk read**
        hub = hardwareMap.get(ExpansionHubEx.class, "hub");

        imu = LynxOptimizedI2cFactory.createLynxEmbeddedImu(hub.getStandardModule(), 0);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);

        // TODO: if your hub is mounted vertically, remap the IMU axes so that the z-axis points
        // upward (normal to the floor) using a command like the following:
        // BNO055IMUUtil.remapAxes(imu, AxesOrder.XYZ, AxesSigns.NPN);

        leftFront = hardwareMap.get(ExpansionHubMotor.class, "leftFront");
        leftRear = hardwareMap.get(ExpansionHubMotor.class, "leftRear");
        rightRear = hardwareMap.get(ExpansionHubMotor.class, "rightRear");
        rightFront = hardwareMap.get(ExpansionHubMotor.class, "rightFront");

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motors = Arrays.asList(leftFront, leftRear, rightRear, rightFront);

        for (ExpansionHubMotor motor : motors) {
            // TODO: decide whether or not to use the built-in velocity PID
            // if you keep it, then don't tune kStatic or kA
            // otherwise, comment out the following line
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        // TODO: reverse any motors using DcMotor.setDirection()

        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);

        // TODO: set the tuned coefficients from DriveVelocityPIDTuner if using RUN_USING_ENCODER
        // setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, ...);

        setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDCoefficients(kP, kI, kD));

        // TODO: if desired, use setLocalizer() to change the localization method
        // for instance, setLocalizer(new ThreeTrackingWheelLocalizer(...));
    }

    @Override
    public PIDCoefficients getPIDCoefficients(DcMotor.RunMode runMode) {
        com.qualcomm.robotcore.hardware.PIDCoefficients coefficients = leftFront.getPIDCoefficients(runMode);
        return new PIDCoefficients(coefficients.p, coefficients.i, coefficients.d);
    }

    @Override
    public void setPIDCoefficients(DcMotor.RunMode runMode, PIDCoefficients coefficients) {
        for (ExpansionHubMotor motor : motors) {
            motor.setPIDCoefficients(runMode, new com.qualcomm.robotcore.hardware.PIDCoefficients(
                    coefficients.kP,
                    coefficients.kI,
                    coefficients.kD
            ));
        }
    }

    @NotNull
    @Override
    public List<Double> getWheelPositions() {
        RevBulkData bulkData = hub.getBulkInputData();

        if (bulkData == null) {
            return Arrays.asList(0.0, 0.0, 0.0, 0.0);
        }

        List<Double> wheelPositions = new ArrayList<>();
        for (ExpansionHubMotor motor : motors) {
            wheelPositions.add(encoderTicksToInches(bulkData.getMotorCurrentPosition(motor)));
            //wheelPositions.add(bulkData.getMotorCurrentPosition(motor) + 0.0);
        }
        return wheelPositions;
    }

    public boolean motorsAreBusy()
    {
        return (leftFront.isBusy() && leftRear.isBusy()) || (rightFront.isBusy() && rightRear.isBusy());
    }

    @Override
    public void setMotorPowers(double v, double v1, double v2, double v3) {
        leftFront.setPower(v);
        leftRear.setPower(v1);
        rightRear.setPower(v2);
        rightFront.setPower(v3);
    }

    public void setTargetPositions(int v, int v1, int v2, int v3)
    {
        for(ExpansionHubMotor motor : motors)
        {
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        leftFront.setTargetPosition(v);
        leftRear.setTargetPosition(v1);
        rightRear.setTargetPosition(v2);
        rightFront.setTargetPosition(v3);
    }

    @Override
    public double getRawExternalHeading() {
        return imu.getAngularOrientation().firstAngle;
    }

    public void driveTeleOp(double left_stick_x, double left_stick_y, double right_stick_x)
    {
        double lf = left_stick_y - (left_stick_x)  - right_stick_x;
        double rf = left_stick_y  + (left_stick_x) + right_stick_x;
        double lr = left_stick_y  + (left_stick_x)  - right_stick_x;
        double rr = left_stick_y - (left_stick_x) + right_stick_x;

        //Move range to between 0 and +1, if not already
        double[] wheelPowers = {rf, lf, lr, rr};
        Arrays.sort(wheelPowers);
        if (wheelPowers[3] > 1) {
            lf /= wheelPowers[3];
            rf /= wheelPowers[3];
            lr /= wheelPowers[3];
            rr /= wheelPowers[3];
        }

        this.setMotorPowers(lf,lr,rr,rf);
    }
}