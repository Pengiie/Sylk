package dev.penguinz.Sylk.audio;

import com.sun.media.sound.WaveFileReader;
import dev.penguinz.Sylk.util.BufferUtils;
import dev.penguinz.Sylk.util.IOUtils;
import org.lwjgl.openal.AL11;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class WavSound extends Sound {

    @Override
    public SoundData getSoundData(String filepath) {
        ByteBuffer data = IOUtils.loadFile(filepath);
        String riff = new String(new char[] {(char) data.get(0), (char) data.get(1), (char) data.get(2), (char) data.get(3)});
        String wave = new String(new char[] {(char) data.get(8), (char) data.get(9), (char) data.get(10), (char) data.get(11)});
        if(!riff.equals("RIFF") || !wave.equals("WAVE"))
            throw new RuntimeException("Could not read data of "+filepath);
        int sampleSize = data.getInt(24);
        int format = data.get(32);
        switch (format) {
            case 1: format = AL11.AL_FORMAT_MONO8; break;
            case 2: format = AL11.AL_FORMAT_MONO16; break;
            case 3: format = AL11.AL_FORMAT_STEREO8; break;
            case 4: format = AL11.AL_FORMAT_STEREO16; break;
        }

        int dataSize = data.getInt(40);
        int[] waveData = new int[dataSize/4];
        for (int i = 0; i < waveData.length; i++) {
            waveData[i] = data.getInt(i*4 + 44);
        }

        return new SoundData(format, sampleSize, waveData);
    }
}