package mod.jeje.voicerecognition.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mod.jeje.voicerecognition.setup.setupActions;
import mod.jeje.voicerecognition.utils.stringProcessing;
import mod.jeje.voicerecognition.voskTest.voskTest;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.ArrayList;

import static net.minecraft.server.command.CommandManager.argument;

public class manualBanWord {


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment){
        dispatcher.register(CommandManager.literal("words")
                .then(CommandManager.literal("add")
                        .then(argument("word", StringArgumentType.string())
                                .executes(manualBanWord::run))));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        String wordToBan = StringArgumentType.getString(context, "word");
        if (!voskTest.bannedWords.contains(wordToBan) && wordToBan != null){
            stringProcessing.banWord(wordToBan);
            setupActions.auxiliarUpdateData();

            //Feedback on successful.
            context.getSource().sendFeedback(() -> Text.of("Manually added word \"" + wordToBan + "\" to the bannedWords list."), true);
            return 1;
        }


        //Feedback on failed.
        context.getSource().sendFeedback(() -> Text.of("Could not add word \"" + wordToBan + "\" to the bannedWords list. May already be registered."), true);

        return 1;
    }
}
