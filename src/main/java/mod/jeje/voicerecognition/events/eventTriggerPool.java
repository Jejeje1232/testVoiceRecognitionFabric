package mod.jeje.voicerecognition.events;

import static mod.jeje.voicerecognition.events.jejeEventsCallbacksHandler.PentaConsumer;

import mod.jeje.voicerecognition.test.testGuiNotifier;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class eventTriggerPool {
    /*
    This class refers to the events that need to be triggered one after the other, it sets a queue for them to
    not overlap with the execution of another (Mostly to not interrupt the tts)
    */

    private static List<Runnable> eventPool = new ArrayList<Runnable>();
    private static List<String> namePool = new ArrayList<String>();

    public static void add(Runnable function, String functionName){
        eventPool.add(function);
        namePool.add(functionName);
    }

    public static void runNext(){
        if (eventPool.isEmpty()){return;}
        eventPool.get(0).run();
        testGuiNotifier.queueText("Executing [" + namePool.get(0) + "].");
        eventPool.remove(0);
        namePool.remove(0);
    }
}
