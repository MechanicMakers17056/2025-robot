package org.firstinspires.ftc.teamcode.teleop.button.buttons;

import org.firstinspires.ftc.teamcode.teleop.button.TimedButton;

public class HoldButton extends TimedButton {
    private final double requiredTime;
    private boolean triggered = false;

    public HoldButton(double requiredTime) {
        this.requiredTime = requiredTime;
        disableClickFunction = true;
    }

    @Override
    public void tick(boolean button, OnClick defaultFunction) {
        super.tick(button, defaultFunction);
        if(isReleased()) {
            triggered = false;
        } else {
            if (timer.seconds() >= requiredTime && !triggered) {
                super.onClick();
                timer.reset();
                triggered = true;
            }
        }
    }
}
