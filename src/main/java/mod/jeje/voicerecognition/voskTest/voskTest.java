package mod.jeje.voicerecognition.voskTest;


import java.io.*;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.*;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import mod.jeje.voicerecognition.Jeje_voiceRecognition;
import mod.jeje.voicerecognition.commandHandler;
import mod.jeje.voicerecognition.networking.PacketHandler;
import mod.jeje.voicerecognition.setup.setupActions;
import mod.jeje.voicerecognition.wordCounter.WordCounter;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import org.vosk.Recognizer;
import org.vosk.Model;

public class voskTest {
    static CountDownLatch initializationLatch = new CountDownLatch(1);
    static String modelsStringPath = System.getProperty("user.dir") + "/model/";
    static Path modelsPath = Path.of(modelsStringPath);
    //static String modelFolderPath = extractFolder(modelPathJar, "voskModel");

    static String modelRealPath;

    public static List<String> bannedWords = new ArrayList<>();

    WordCounter wordCounter = new WordCounter();
    public static void test() throws IOException, UnsupportedAudioFileException {

        //START TEST
        if (Files.isDirectory(modelsPath)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(modelsPath)) {
                // Iterate through the contents of the folder
                for (Path path : directoryStream) {
                    // Check if the item is a subfolder
                    if (Files.isDirectory(path)) {
                        modelRealPath =  modelsStringPath + path.getFileName();
                        // Assuming you want only one subfolder, you can break after finding the first one
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No folder found.");
        }



        // END TEST

        try {
            //LibVosk.setLogLevel(LogLevel.DEBUG);

            // Load the Vosk model
            try (Model model = new Model(modelRealPath)) {

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
                //System.out.println("Jeje_LOG: Initialization of voiceRecognition took:" + initTime + "ms");
                Jeje_voiceRecognition.LOGGER.info("Jeje_LOG: Initialization of voiceRecognition took:" + initTime + "ms");


                // Shut down the ExecutorService
                //executorService.shutdown();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getWord(String jsonText){
        if (jsonText.length() > 20) {
            //TEST
            CharsetDetector detector = new CharsetDetector();

            //String theString = new String(jsonText.substring(14, jsonText.length() - 3).getBytes(), java.nio.charset.StandardCharsets.ISO_8859_1);
            byte[] theStringByte = jsonText.substring(14, jsonText.length() - 3).getBytes(StandardCharsets.ISO_8859_1);
            return new String(theStringByte, StandardCharsets.UTF_8);
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
                        //String[] wordArray = getWord(jsonResult).split(" ");
                        String word = getWord(jsonResult);

                        if (!word.isEmpty() && ClientPlayNetworking.canSend(PacketHandler.SEND_STRINGS_ID)){
                            PacketByteBuf stringToSend = PacketByteBufs.create();
                            stringToSend.writeString(word);
                            ClientPlayNetworking.send(PacketHandler.SEND_STRINGS_ID, stringToSend);
                        }


//                        if (word != "" && bannedWords.stream().anyMatch(word::contains)){
//                            //MinecraftClient.getInstance().player.sendMessage(Text.of("El Sexo:tm:"));
//                            //commandHandler.executeCommand("execute as @a run tp ~ ~10 ~");
//                            //ClientPlayNetworking.send(PacketHandler.EVENT_ID, PacketByteBufs.create());
//
//
//                        }
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

    private static void testResourceFile(String resourcePath) {
        // Use the class loader to get the URL of the resource
        URL resourceUrl = voskTest.class.getClassLoader().getResource(resourcePath);

        // Check if the resource URL is not null and the file exists
        if (resourceUrl != null) {
            System.out.println("Resource file found for path: " + resourcePath);
            System.out.println("File URL: " + resourceUrl.getFile());
        } else {
            System.out.println("Resource file not found for path: " + resourcePath);
        }
    }

}