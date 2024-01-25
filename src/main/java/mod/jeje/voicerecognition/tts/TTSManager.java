package mod.jeje.voicerecognition.tts;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import lombok.Getter;

public class TTSManager {
    // Access the shared instance
    @Getter
    private static final TTSManager instance = new TTSManager(); // Singleton instance
    private final Voice voice;

    private TTSManager() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice("kevin16");

        if (voice != null) {
            try {
                voice.allocate();
            } catch (Exception e) {
                System.err.println("Error allocating voice: " + e.getMessage());
            }
        } else {
            System.err.println("Voice 'kevin16' not found");
        }
    }

    public void speakAsync(String text) {
        new Thread(null, () -> {
            voice.speak(text);
        }, "TTS-Thread").start();
    }

    public void deallocateVoice() {
        if (voice != null) {
            voice.deallocate();
        }
    }
}