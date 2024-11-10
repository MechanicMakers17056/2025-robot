package org.firstinspires.ftc.teamcode.teleop.button.buttons;

import org.firstinspires.ftc.teamcode.teleop.button.PressAndReleaseButton;

public class MultipleInputsButton extends PressAndReleaseButton {
    public void tick(OnClick defaultFunction, boolean... buttons) {
        // Check if all the buttons are pressed (have the same value)
        boolean allButtonsPressed = true;
        for (boolean pressed : buttons) {
            if(!pressed) {
                allButtonsPressed = false;
                break;
            }
        }
        super.tick(allButtonsPressed, defaultFunction);
    }
}
