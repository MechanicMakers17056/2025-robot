package org.firstinspires.ftc.teamcode.teleop.button;

import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class TimedButton extends PressAndReleaseButton {
    protected ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
    private boolean alreadyResetTimer = false;

    public void tick(boolean button, OnClick defaultFunction) {
        super.tick(button, defaultFunction);
        if (!isReleased()) {
            if (!alreadyResetTimer) {
                timer.reset();
                alreadyResetTimer = true;
            }
        }

        if (isReleased())
            alreadyResetTimer = false;
    }
}
