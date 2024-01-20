package mod.jeje.voicerecognition.events;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class jejeEventsCallbacks {
    public static void TPTEST_Reset(MinecraftServer server){

        ServerWorld world = server.getWorld(ServerWorld.OVERWORLD);
        System.out.println("Server tickrate: " + world.getTickManager().getTickRate());
        world.getTickManager().setTickRate(20);
        System.out.println("Server modified tickrate: " + world.getTickManager().getTickRate());

    }
}
