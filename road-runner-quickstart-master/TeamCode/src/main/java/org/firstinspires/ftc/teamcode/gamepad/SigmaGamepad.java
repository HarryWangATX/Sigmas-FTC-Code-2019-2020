package org.firstinspires.ftc.teamcode.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.Arrays;
import java.util.List;

/*
    This gamepad is designed to accommodate higher level combinations of button presses.
 */

public class SigmaGamepad {
    private Gamepad gamepad;
    private List<SigmaButton> buttons;

    public final double left_stick_x;
    public final double left_stick_y;

    public final double right_stick_x;
    public final double right_stick_y;

    public SigmaButton leftBumper;
    public SigmaButton rightBumper;

    public SigmaButton a;
    public SigmaButton b;
    public SigmaButton x;
    public SigmaButton y;

    public SigmaGamepad(Gamepad gamepad)
    {
        this.gamepad = gamepad;

        leftBumper = new SigmaButton(this.gamepad.left_bumper);
        rightBumper = new SigmaButton(this.gamepad.right_bumper);

        a = new SigmaButton(this.gamepad.a);
        b = new SigmaButton(this.gamepad.b);
        x = new SigmaButton(this.gamepad.x);
        y = new SigmaButton(this.gamepad.y);

        buttons = Arrays.asList(this.leftBumper, this.rightBumper, this.a, this.b, this.x, this.y);

        left_stick_x = this.gamepad.left_stick_x;
        left_stick_y = this.gamepad.left_stick_y;

        right_stick_x = this.gamepad.right_stick_x;
        right_stick_y = this.gamepad.right_stick_y;
    }
}
