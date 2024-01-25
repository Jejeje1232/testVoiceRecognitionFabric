package mod.jeje.voicerecognition.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mod.jeje.voicerecognition.test.testGuiNotifier;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;

public class changeColorTest {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment){
        dispatcher.register(CommandManager.literal("color")
                .then(argument("Alpha", IntegerArgumentType.integer())
                        .then(argument("Red", IntegerArgumentType.integer())
                                .then(argument("Green", IntegerArgumentType.integer())
                                        .then(argument("Blue", IntegerArgumentType.integer())
                                                .executes(changeColorTest::run))))));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        int Alpha = IntegerArgumentType.getInteger(context, "Alpha");
        int Red = IntegerArgumentType.getInteger(context, "Red");
        int Green = IntegerArgumentType.getInteger(context, "Green");
        int Blue = IntegerArgumentType.getInteger(context, "Blue");

        testGuiNotifier.setColor(Alpha, Red, Green, Blue);
        return 1;
    }
}
