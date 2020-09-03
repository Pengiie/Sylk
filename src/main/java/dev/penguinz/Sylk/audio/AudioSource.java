package dev.penguinz.Sylk.audio;

import dev.penguinz.Sylk.util.Disposable;
import dev.penguinz.Sylk.util.maths.Vector2;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;

public class AudioSource implements Disposable {

    private final int id;

    public AudioSource() {
        this.id = AL11.alGenSources();

        AL11.alSourcei(this.id, AL11.AL_PITCH, 1);
        AL11.alSourcei(this.id, AL11.AL_GAIN, 1);
    }

    public void setPosition(Vector2 position) {
        AL11.alSource3f(this.id, AL11.AL_POSITION, position.x, position.y, 0);
    }

    public void play(Sound sound) {
        AL11.alListener3f(AL11.AL_POSITION, 0, 0, 0);
        AL11.alSourcei(this.id, AL11.AL_BUFFER, sound.id);
        AL11.alSourcePlay(this.id);
    }

    public void play(Sound sound, Vector2 listenerPosition) {
        AL11.alListener3f(AL11.AL_POSITION, listenerPosition.x, listenerPosition.y, 0);
        AL11.alSourcei(this.id, AL11.AL_BUFFER, sound.id);
        AL11.alSourcePlay(this.id);
    }

    public void setLooping(boolean looping) {
        AL11.alSourcei(this.id, AL10.AL_LOOPING, looping ? 1 : 0);
    }

    public void stop() {
        AL11.alSourceStop(this.id);
    }

    @Override
    public void dispose() {
        AL11.alDeleteSources(this.id);
    }
}
