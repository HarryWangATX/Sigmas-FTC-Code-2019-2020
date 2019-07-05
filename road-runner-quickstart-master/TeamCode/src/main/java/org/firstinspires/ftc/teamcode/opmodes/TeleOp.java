package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

public abstract class TeleOp extends LinearOpMode {
    boolean useAutoPos = true;

    private final double LOOP_HZ = 144;
    private final long LOOP_MS = Math.round(1000/LOOP_HZ); //converts HZ to MS
    private long loops = 0;

    ElapsedTime runtime = new ElapsedTime();

    Robot robot;

    public void logData()
    {
        telemetry.addData("Robot position: ",robot.drive.getPoseEstimate());
        telemetry.addData("Motor positions", robot.drive.getWheelPositions());
        telemetry.addData("Loop Speed (HZ)", this.loopsPerSecond());
        telemetry.update();
    }

    public void endLoop()
    {
        this.logData();
        loops++;
        sleep(LOOP_MS);
    }

    public double loopsPerSecond()
    {
        if(loops == Long.MAX_VALUE)
        {
            loops = 0;
            runtime.reset();
        }

        if(runtime.seconds() == 0)
        {
            return 0;
        }
        else
        {
            return loops/runtime.seconds();
        }
    }
}
