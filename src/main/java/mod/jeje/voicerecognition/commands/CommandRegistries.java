package mod.jeje.voicerecognition.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandRegistries {

    public static void registerCommands(){
        CommandRegistrationCallback.EVENT.register(clearBannedWords::register);
        CommandRegistrationCallback.EVENT.register(manualBanWord::register);
        //TEMP
        CommandRegistrationCallback.EVENT.register(changeColorTest::register);
        CommandRegistrationCallback.EVENT.register(changeTextTest::register);
    }
}
