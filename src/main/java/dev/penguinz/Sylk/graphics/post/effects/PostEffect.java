package dev.penguinz.Sylk.graphics.post.effects;

import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.util.Disposable;

public interface PostEffect extends Disposable {

    int processEffect(int workingTexture);

    void clearBuffers();

    void onEvent(Event event);

}
