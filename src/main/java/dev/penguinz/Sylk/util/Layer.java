package dev.penguinz.Sylk.util;

import dev.penguinz.Sylk.event.Event;

public interface Layer extends Disposable {

    void init();

    void update();

    void render();

    void onEvent(Event event);

    @Override
    void dispose();
}
