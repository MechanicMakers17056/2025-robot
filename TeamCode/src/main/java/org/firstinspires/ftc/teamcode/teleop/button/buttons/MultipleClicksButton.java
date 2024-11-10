package org.firstinspires.ftc.teamcode.teleop.button.buttons;

import org.firstinspires.ftc.teamcode.teleop.button.Button;
import org.firstinspires.ftc.teamcode.teleop.button.TimedButton;

public class MultipleClicksButton extends TimedButton {
    private final int DEFAULT_RESET_TIME = 500;
    private final int resetTime;

    private final int clicksRequired;
    private int clicks = 0;

    public MultipleClicksButton() {
        this.resetTime = DEFAULT_RESET_TIME;
        this.clicksRequired = 2;
    }

    public MultipleClicksButton(int clicks) {
        this.clicksRequired = clicks;
        this.resetTime = DEFAULT_RESET_TIME;
    }

    public MultipleClicksButton(int clicks, int resetTime) {
        this.clicksRequired = clicks;
        this.resetTime = resetTime;
    }

    public void tick(boolean button, OnClick defaultFunction) {
        super.tick(button, defaultFunction);
        if (timer.milliseconds() > resetTime) {
            clicks = 0;
        }
    }

    public void onClick() {
        if (timer.milliseconds() <= resetTime) {
            clicks++;
            if (clicks == clicksRequired) {
                super.onClick();
                clicks = 0;
            }
        }
    }
}
