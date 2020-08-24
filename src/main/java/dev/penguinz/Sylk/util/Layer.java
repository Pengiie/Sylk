package dev.penguinz.Sylk.util;

import dev.penguinz.Sylk.event.Event;

public interface Layer extends Disposable {

    /**
     * Called when the layer is added to the application.
     */
    void init();

    /**
     * Updates once per frame.
     */
    void update();

    /**
     * Renders after updating.
     */
    void render();

    /**
     * An event listener.
     * @param event an event dispatched by the application.
     */
    void onEvent(Event event);
}
