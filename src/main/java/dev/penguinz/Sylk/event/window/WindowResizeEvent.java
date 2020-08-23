package dev.penguinz.Sylk.event.window;

import dev.penguinz.Sylk.event.Event;

public class WindowResizeEvent extends Event {

    public final int width, height;

    public WindowResizeEvent(int width, int height) {
        this.width = width;
        this.height = height;
    }

}
