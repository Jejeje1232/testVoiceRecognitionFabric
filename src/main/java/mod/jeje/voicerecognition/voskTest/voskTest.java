package mod.jeje.voicerecognition.voskTest;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.*;

import mod.jeje.voicerecognition.wordCounter.WordCounter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.vosk.LogLevel;
import org.vosk.Recognizer;
import org.vosk.LibVosk;
import org.vosk.Model;

public class voskTest {
    static CountDownLatch initializationLatch = new CountDownLatch(1);
    WordCounter wordCounter = new WordCounter();
    public static void test() throws IOException, UnsupportedAudioFileException {
        try {
            LibVosk.setLogLevel(LogLevel.DEBUG);

            // Load the Vosk model
            try (Model model = new Model("E:\\VSCode\\Projectos\\git\\testVoiceRecognitionFabric\\src\\main\\java\\mod\\jeje\\voicerecognition\\voskTest\\model\\vosk-model-small-es-0.42")) {

                // Create an ExecutorService with a single thread
                ExecutorService executorService = Executors.newSingleThreadExecutor();


                // Start the recognition process in a separate thread
                //executorService.submit(() -> recognizeSpeech(model));
                Future<?> recognitionFuture = executorService.submit(() -> recognizeSpeech(model, initializationLatch));

                //Wait to unpause main thread
                long startTime = System.currentTimeMillis();
                initializationLatch.await();
                long endTime = System.currentTimeMillis();
                long initTime = endTime - startTime;
                System.out.println("Jeje_LOG: Initialization of voiceRecognition took:" + initTime + "ms");

                // Shut down the ExecutorService
                //executorService.shutdown();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getWord(String jsonText){
        if (jsonText.length() > 20) {
            return jsonText.substring(14, jsonText.length() - 3);
        } else {
            return "";
        }
    }

    private static void recognizeSpeech(Model model, CountDownLatch initializationLatch) {
        try (TargetDataLine line = getMicrophone()) {
            line.open();
            line.start();

            // Create a Vosk recognizer
            Recognizer recognizer = new Recognizer(model, 16000);

            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesRead;

            //Send flag to unpause the main thread
            initializationLatch.countDown();

            //a little test here;
            while (true) {
                bytesRead = line.read(buffer, 0, bufferSize);

                if (bytesRead > 0) {
                    if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                        String jsonResult = recognizer.getResult();
//                        System.out.println(jsonResult);
                        System.out.println(getWord(jsonResult));
                        System.out.println("----------------");

                        //TEMP
                        String[] wordArray = getWord(jsonResult).split(" ");
                        String[] bannedWords = {"banana"};
                        for (String word : wordArray) {
                            if (word != "") {
                            for (String banned : bannedWords) {
                                System.out.println("{\"" + word + "\"} : {\"" + banned + "\"}");
                                if (Objects.equals(word, banned)) {
                                    System.out.println("IT WORKED?");


                                    // ACA TENGO QUE CONTINUAR



                                    }
                                }
                            } else {continue;}
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static TargetDataLine getMicrophone() throws LineUnavailableException {
        AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            throw new LineUnavailableException("Microphone not supported");
        }

        return (TargetDataLine) AudioSystem.getLine(info);
    }
}