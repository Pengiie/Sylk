package dev.penguinz.Sylk.event;

public class Event {

    private boolean handled = false;

    public boolean isHandled() {
        return handled;
    }

    public void handle() {
        handled = true;
    }

}
