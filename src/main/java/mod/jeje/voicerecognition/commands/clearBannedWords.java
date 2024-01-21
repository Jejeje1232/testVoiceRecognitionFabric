package mod.jeje.voicerecognition.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mod.jeje.voicerecognition.setup.setupActions;
import mod.jeje.voicerecognition.voskTest.voskTest;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;

public class clearBannedWords {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment){
        dispatcher.register(CommandManager.literal("words")
                .then(CommandManager.literal("clear")
                        .executes(clearBannedWords::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException{

        voskTest.bannedWords = new ArrayList<String>();
        setupActions.auxiliarUpdateData();

        //Feedback.
        context.getSource().sendFeedback(() -> Text.of("Cleared the bannedWords list."), true);
        return 1;
    }

}
