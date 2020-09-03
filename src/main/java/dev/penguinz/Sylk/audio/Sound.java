package dev.penguinz.Sylk.audio;

import dev.penguinz.Sylk.util.Disposable;
import org.lwjgl.openal.AL11;

public abstract class Sound implements Disposable {

    int id;

    public void loadAsync(String filepath) {
        this.id = AL11.alGenBuffers();

        SoundData soundData = getSoundData(filepath);

        AL11.alBufferData(this.id, soundData.format, soundData.data, soundData.frequency);

    }

    public abstract SoundData getSoundData(String filepath);

    @Override
    public void dispose() {
        AL11.alDeleteBuffers(this.id);
    }

    public static class SoundData {
        public final int format, frequency;
        public final int[] data;

        public SoundData(int format, int frequency, int[] data) {
            this.format = format;
            this.frequency = frequency;
            this.data = data;
        }
    }
}
