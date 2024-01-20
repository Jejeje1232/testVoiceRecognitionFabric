package mod.jeje.voicerecognition.networking;

import mod.jeje.voicerecognition.events.jejeEvents;

import mod.jeje.voicerecognition.utils.stringProcessing;
import mod.jeje.voicerecognition.voskTest.voskTest;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class C2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){

        //jejeEvents.TPTEST(server, player, handler, buf, responseSender);

    }
    public static void receiveString(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        String receivedString = buf.readString();
        stringProcessing.addWordsFromString(receivedString);

        if (voskTest.bannedWords.stream().anyMatch(receivedString::contains) && !voskTest.bannedWords.equals("") && !voskTest.bannedWords.isEmpty()){
            jejeEvents.TPTEST(server, player, handler, buf, responseSender);
        }
    }
}
