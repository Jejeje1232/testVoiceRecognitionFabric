package mod.jeje.voicerecognition.networking;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mod.jeje.voicerecognition.commandHandler;
import mod.jeje.voicerecognition.states.StateSaverAndLoader;
import mod.jeje.voicerecognition.test.testGuiNotifier;
import mod.jeje.voicerecognition.utils.stringStuff;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;

import static mod.jeje.voicerecognition.Jeje_voiceRecognitionClient.clientBannedList;

public class S2CPacket {
    //All of this executes on the client side.

    public static void receiveSyncList(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        String readBannedWords = buf.readString();
        clientBannedList = stringStuff.stringToList(readBannedWords);
    }

    public static void receiveDisplayDetectedWords(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        List<String> toSay = stringStuff.stringToList(buf.readString());

        for (String string : toSay){
            testGuiNotifier.queueText("Detected \"" + string + "\" or similar.");
        }
    }
}
