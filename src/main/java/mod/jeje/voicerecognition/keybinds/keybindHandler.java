package mod.jeje.voicerecognition.keybinds;


import mod.jeje.voicerecognition.screen.infoScreen;
import mod.jeje.voicerecognition.screen.screenHandler;
import mod.jeje.voicerecognition.screen.testGui;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class keybindHandler {
    public static final String KEY_CATEGORY = "key.category.jejeVoice.test";
    public static final String KEY_OPEN_MENU = "key.jejeVoice.openMenu";
    public static KeyBinding menuKey;

    //TEST:

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            //MinecraftClient.getInstance().player.sendMessage(Text.of("The keybind works, at least."));
            if (menuKey.wasPressed()) {
                if (client.player != null) {
                    screenHandler test = new screenHandler(new infoScreen());
                    client.setScreen(test);
                }
            }

        });
    }

    public static void register(){
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPEN_MENU,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                KEY_CATEGORY
        ));
        registerKeyInputs();
    }


}
