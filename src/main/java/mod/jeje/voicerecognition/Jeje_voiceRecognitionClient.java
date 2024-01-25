package mod.jeje.voicerecognition;

import mod.jeje.voicerecognition.keybinds.keybindHandler;
import mod.jeje.voicerecognition.networking.PacketHandler;
import mod.jeje.voicerecognition.tts.TTSManager;
import mod.jeje.voicerecognition.voskTest.voskTest;
import mod.jeje.voicerecognition.test.testGuiNotifier;

//import me.x150.renderer.event.RenderEvents;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Jeje_voiceRecognitionClient implements ClientModInitializer{

    public static final Identifier CUSTOM_MENU = new Identifier(Jeje_voiceRecognition.MOD_ID, "custom_menu");
    //start ttsManager
    public static TTSManager ttsVoice = TTSManager.getInstance();

    public static List<String> clientBannedList = new ArrayList<String>();
    //---------
    @Override
    public void onInitializeClient() {

        //start vosk
        try {
            voskTest.test();
        } catch (IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
        //---------

        //TEMP NOTIF TEST:
        HudRenderCallback.EVENT.register(new testGuiNotifier());
        //----------------




        //Register custom menu.

        //---------

        PacketHandler.registerS2CPackets();
        keybindHandler.register();


    }
}
