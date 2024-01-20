package mod.jeje.voicerecognition;

import mod.jeje.voicerecognition.keybinds.keybindHandler;
import mod.jeje.voicerecognition.networking.PacketHandler;
import mod.jeje.voicerecognition.voskTest.voskTest;
import net.fabricmc.api.ClientModInitializer;
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
import java.util.List;

public class Jeje_voiceRecognitionClient implements ClientModInitializer{

    public static final Identifier CUSTOM_MENU = new Identifier(Jeje_voiceRecognition.MOD_ID, "custom_menu");
    @Override
    public void onInitializeClient() {

        try {
            voskTest.test();
        } catch (IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }

        //Register custom menu.

        //---------

        PacketHandler.registerS2CPackets();
        keybindHandler.register();


    }
}
