package mod.jeje.voicerecognition;

import mod.jeje.voicerecognition.networking.PacketHandler;
import mod.jeje.voicerecognition.voskTest.voskTest;
import net.fabricmc.api.ClientModInitializer;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Jeje_voiceRecognitionClient implements ClientModInitializer{
    @Override
    public void onInitializeClient() {

        try {
            voskTest.test();
        } catch (IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }

        PacketHandler.registerS2CPackets();

    }
}
