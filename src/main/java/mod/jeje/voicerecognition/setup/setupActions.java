package mod.jeje.voicerecognition.setup;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mod.jeje.voicerecognition.commandHandler;
import mod.jeje.voicerecognition.events.jejeEventsCallbacks;
import mod.jeje.voicerecognition.events.jejeEventsCallbacksHandler;
import mod.jeje.voicerecognition.networking.PacketHandler;
import mod.jeje.voicerecognition.states.StateSaverAndLoader;
import mod.jeje.voicerecognition.tts.TTSManager;
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

import static mod.jeje.voicerecognition.constants.GET_WORD_TIME_SECONDS;

public class setupActions {
    private static boolean mainLoopAlt = false;
    public static MinecraftServer auxiliarServerAccess;

    public static void setupData(MinecraftServer server){
        auxiliarServerAccess = server;
        StateSaverAndLoader state = StateSaverAndLoader.getServerState(server);
        voskTest.bannedWords = stringStuff.stringToList(state.bannedWordsString);
    }

    public static void updateData(MinecraftServer server){
        StateSaverAndLoader state = StateSaverAndLoader.getServerState(server);
        state.bannedWordsString = stringStuff.listToString(voskTest.bannedWords);
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()){
            updateClientData(state.bannedWordsString, player);
        }
    }

    public static void auxiliarUpdateData(){
        StateSaverAndLoader state = StateSaverAndLoader.getServerState(auxiliarServerAccess);
        state.bannedWordsString = stringStuff.listToString(voskTest.bannedWords);
        for (ServerPlayerEntity player : auxiliarServerAccess.getPlayerManager().getPlayerList()){
            updateClientData(state.bannedWordsString, player);
        }
    }

    public static void setupClientData(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server){
        StateSaverAndLoader state = StateSaverAndLoader.getServerState(server);
        PacketByteBuf sendData = PacketByteBufs.create();
        sendData.writeString(state.bannedWordsString);
        ServerPlayNetworking.send(handler.getPlayer(), PacketHandler.SYNC_LIST, sendData);
    }

    public static void updateClientData(String stringList, ServerPlayerEntity player){
        PacketByteBuf sendData = PacketByteBufs.create();
        sendData.writeString(stringList);
        ServerPlayNetworking.send(player, PacketHandler.SYNC_LIST, sendData);
    }

    public static void resetTrigger(MinecraftServer server){
        jejeEventsCallbacksHandler.forceTriggerB = false;
    }

    public static void setupMainBanLoop(MinecraftServer server){
        System.out.println("Se intenta iniciar el main loop");
        jejeEventsCallbacksHandler.jejeSchedule(GET_WORD_TIME_SECONDS,"SETUP", setupActions::mainLoop, server);
    }

    public static void mainLoop(MinecraftServer server){
        stringProcessing.banRandomWord();
        updateData(server);

        //TEST
        StateSaverAndLoader state = StateSaverAndLoader.getServerState(server);
        System.out.println("PALABRAS BANEADAS: " + state.bannedWordsString);
        //System.out.println("Funciona esta cosa?");
        //END TEST
        mainLoopAlt = !mainLoopAlt;
        String THIS_ID = mainLoopAlt ? "MAINLOOP_1" : "MAINLOOP_2";

        if (jejeEventsCallbacksHandler.forceTriggerB){return;}
        jejeEventsCallbacksHandler.jejeSchedule(GET_WORD_TIME_SECONDS,THIS_ID, setupActions::mainLoop, server);
    }
}
