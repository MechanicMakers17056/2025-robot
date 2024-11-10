package org.firstinspires.ftc.teamcode.teleop.button;

public class PressAndReleaseButton extends Button {
    protected boolean released = true;
    protected boolean disableClickFunction = false;
    OnClick defaultFunction;

    public boolean isReleased() {
        return released;
    }

    protected void onClick() {
        defaultFunction.onClick();
    }

    public void tick(boolean button, OnClick defaultFunction) {
        this.defaultFunction = defaultFunction;
        if (!button) {
            released = true;
        }

        if (button && released) {
            if(!disableClickFunction)
                onClick();

            released = false;
        }
    }
}
