package mod.jeje.voicerecognition.events;


import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static net.minecraft.entity.SpawnReason.TRIGGERED;

// This is a C2S package:
public class jejeEvents {

    public static void Creepers(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        EntityType.CREEPER.spawn((ServerWorld) player.getServerWorld(), player.getBlockPos(), TRIGGERED);
    }

    public static void Ghasts(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        EntityType.GHAST.spawn((ServerWorld) player.getServerWorld(), player.getBlockPos(), TRIGGERED);
    }

    public static void Witches(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        EntityType.WITCH.spawn((ServerWorld) player.getServerWorld(), player.getBlockPos(), TRIGGERED);
    }

    public static void Thunder(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        EntityType.LIGHTNING_BOLT.spawn((ServerWorld) player.getServerWorld(), player.getBlockPos(), TRIGGERED);
    }

    public static void TNT(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        EntityType.TNT.spawn((ServerWorld) player.getServerWorld(), player.getBlockPos(), TRIGGERED);
    }

    public static void TPEvent(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //TP the player a random number of blocks in a random direction.
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
        List<ServerPlayerEntity> playersShift = new ArrayList<ServerPlayerEntity>(players);
        Collections.rotate(playersShift, 1);
    }

    public static void Erase(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //Erase a random item from the player's inventory.
    }

    public static void Drop(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //Drop every item from the player's inventory.
    }

    public static void Blindness(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //Give the player blindness.
    }

    public static void Nausea(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //Give the player Nausea.
    }

    public static void Fake(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //Fake a hostile mob sound.
    }

    public static void Rick(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //Rickroll the player.
    }

    public static void Skibiddi(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //I forgor.
    }

    public static void TPTEST(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){

        ServerWorld world = player.getServerWorld();
        System.out.println("Server tickrate: " + player.getServerWorld().getTickManager().getTickRate());
        world.getTickManager().setTickRate(200);
        System.out.println("Server modified tickrate: " + player.getServerWorld().getTickManager().getTickRate());
        jejeEventsCallbacksHandler.jejeSchedule(5,"TPTEST", jejeEventsCallbacks::TPTEST_Reset,server, player, handler, buf, sender);




    }

}
