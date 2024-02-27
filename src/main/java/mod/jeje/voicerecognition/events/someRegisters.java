package mod.jeje.voicerecognition.events;

import mod.jeje.voicerecognition.utils.someHelpers;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class someRegisters {
    public static void registerEvents(){

        ServerTickEvents.END_SERVER_TICK.register(someHelpers::playerZombificationHandler);
        ServerTickEvents.END_SERVER_TICK.register(someHelpers::playerEnderificationHandler);
        ServerTickEvents.END_SERVER_TICK.register(someHelpers::playerFishBoyHandler);
    }
}
