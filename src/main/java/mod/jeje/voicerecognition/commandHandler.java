package mod.jeje.voicerecognition;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class commandHandler{

    public static ServerCommandSource commandSource;

    public static void onServerStart(MinecraftServer server){
        commandSource = server.getCommandSource();
    }

    public static void executeCommand(String command) throws CommandSyntaxException {
        // Replace "your_mod_id" with the actual ID of your mod
        commandSource.withLevel(2).getDispatcher().execute(command, commandSource);
    }
}