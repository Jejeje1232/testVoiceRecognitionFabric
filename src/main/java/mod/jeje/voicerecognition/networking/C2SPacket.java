package mod.jeje.voicerecognition.networking;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mod.jeje.voicerecognition.commandHandler;

import mod.jeje.voicerecognition.events.jejeEvents;
import mod.jeje.voicerecognition.utils.someHelpers;
import mod.jeje.voicerecognition.utils.stringProcessing;
import mod.jeje.voicerecognition.utils.stringStuff;
import mod.jeje.voicerecognition.voskTest.voskTest;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static mod.jeje.voicerecognition.networking.PacketHandler.DISP_DET_WORDS;
import static mod.jeje.voicerecognition.utils.someHelpers.random;

public class C2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){


        //jejeEvents.Zombification(server, player, handler, buf, responseSender);

    }
    public static void receiveString(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        String receivedString = buf.readString();
        stringProcessing.addWordsFromString(receivedString);

//        if (voskTest.bannedWords.stream().anyMatch(receivedString::contains) && !voskTest.bannedWords.equals("") && !voskTest.bannedWords.isEmpty()){
//            jejeEvents.TPTEST(server, player, handler, buf, responseSender);
//        }

        List<String> receivedList = new ArrayList<String>(stringStuff.stringToList(receivedString));
//        if (voskTest.bannedWords.stream().anyMatch(receivedList::contains) && !voskTest.bannedWords.equals("") && !voskTest.bannedWords.isEmpty()){
//            jejeEvents.TPTEST(server, player, handler, buf, responseSender);
//        }

        List<String> matchingWords = new ArrayList<String>(receivedList);
        matchingWords.retainAll(voskTest.bannedWords);

//        List<String> matchingWords = new ArrayList<>();
//
//        for (String word : voskTest.bannedWords) {
//            if (receivedList.contains(word)) {
//                matchingWords.add(word);
//            }
//        }

        //System.out.println(receivedList);

        if (!matchingWords.isEmpty() && !voskTest.bannedWords.isEmpty() && player != null) {

            String listAsStringToSend = stringStuff.listToString(matchingWords);
            PacketByteBuf dataToDisplay = PacketByteBufs.create();
            dataToDisplay.writeString(listAsStringToSend);
            ServerPlayNetworking.send(player, DISP_DET_WORDS, dataToDisplay);
        }

        for (int i = 0; i<matchingWords.size(); i++){
            //Acá se colocan los eventos a elegir.
            assert player != null;
            try{
            someHelpers.executeRandomMethod(random, server, player, handler, buf, responseSender);
            } catch (InvocationTargetException | IllegalAccessException ignore) {}
        }

    }
}
