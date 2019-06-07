package org.firstinspires.ftc.teamcode.gamepad;

public class SigmaButton {
    private boolean buttonInput;

    //These three states help calculate what to return.
    private boolean currState = false;
    private boolean prevState = false;
    private boolean taskState = true;

    SigmaButton(boolean button)
    {
        this.buttonInput = button;
    }

    //There is really no point of the function, but it helps discriminate presses vs toggles
    public boolean isPressed()
    {
        return this.buttonInput;
    }

    //Another useless function but returns the status to discriminate toggles vs presses. A separate class would be unnecessary.
    public boolean getStatus() { return this.buttonInput; }

    public boolean toggle() {

        if(buttonInput) {
            currState = true;
        }

        else {
            currState = false;
            if(prevState) {
                taskState = !taskState;
            }
        }

        prevState = currState;

        return taskState;
    }

}
