package org.firstinspires.ftc.teamcode.teleop.button;

public abstract class Button {
    public abstract void tick(boolean button, OnClick onClick);
    public interface OnClick {
        void onClick();
    }
}
