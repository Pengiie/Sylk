package dev.penguinz.Sylk.event.input;

import dev.penguinz.Sylk.event.Event;

public class MouseClickEvent extends Event {

    public final int button;
    public final float mouseX;
    public final float mouseY;

    public MouseClickEvent(int button, float mouseX, float mouseY) {
        this.button = button;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }
}
