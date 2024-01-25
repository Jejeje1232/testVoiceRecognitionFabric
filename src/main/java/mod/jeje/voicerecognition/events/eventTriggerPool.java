package mod.jeje.voicerecognition.events;

import static mod.jeje.voicerecognition.events.jejeEventsCallbacksHandler.PentaConsumer;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class eventTriggerPool {
    /*
    This class refers to the events that need to be triggered one after the other, it sets a queue for them to
    not overlap with the execution of another (Mostly to not interrupt the tts)
    */

    public static List<PentaConsumer<MinecraftServer, ServerPlayerEntity, ServerPlayNetworkHandler, PacketByteBuf, PacketSender>> eventPool;

    public static void runNext(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        if (!eventPool.isEmpty()){
            eventPool.get(0).accept(server, player, handler, buf, sender);
            eventPool.remove(0);
        }
    }
}
