package mod.jeje.voicerecognition.events;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class jejeEventsCallbacksHandler {

    public interface QuadConsumer<T, U, V, W, M> {
        void accept(T t, U u, V v, W w, M m);
    }

    @FunctionalInterface
    public interface FunctionForList {
        boolean execute();
    }

    public static Map<String, Float> counters = new HashMap<String, Float>();
    public static Map<String, FunctionForList> checks = new HashMap<String, FunctionForList>();

    //This is not a callback but is the time manager, so I'll leave it here for now:

    public static void counterSteps(MinecraftServer server) {
        if (counters.isEmpty()){return;}

        float millisToSec = Objects.requireNonNull(server.getWorld(ServerWorld.OVERWORLD)).getTickManager().getMillisPerTick()/1000;

        for (Map.Entry<String, Float> entry : counters.entrySet()){
            String _COUNTER_ID = entry.getKey();
            float _value = entry.getValue();
            float _newValue = _value + (millisToSec);
            counters.put(_COUNTER_ID, _newValue);
        }
    }

    public static void checkSteps(MinecraftServer server){
        if (checks.isEmpty()){return;}

        for (Map.Entry<String, FunctionForList> entry : checks.entrySet()){
            if (entry.getValue().execute()) {
                checks.remove(entry.getKey());
            }
        }
    }

    //This scheduler is for normal functions.
    public static boolean jejeSchedule(float time, String COUNTER_ID, Runnable callback){
        //The time is in Seconds.

        if (counters.containsKey(COUNTER_ID)){
            if (counters.get(COUNTER_ID) >= time){
                counters.remove(COUNTER_ID);
                callback.run();
                return true;
            }
        } else {
            counters.put(COUNTER_ID, 0f);
            System.out.println("Counter SET:" + COUNTER_ID + " " + time);

            checks.put(COUNTER_ID, () -> jejeSchedule(time, COUNTER_ID, callback));
            System.out.println("Check SET:" + COUNTER_ID + " " + time);
            return false;
        }
        return false;
    }

    //This one is for the ones that require the server info.
    public static boolean jejeSchedule(float time, String COUNTER_ID, QuadConsumer<MinecraftServer, ServerPlayerEntity, ServerPlayNetworkHandler, PacketByteBuf, PacketSender> callback, MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //The time is in Seconds.

        if (counters.containsKey(COUNTER_ID)){
            if (counters.get(COUNTER_ID) >= time){
                counters.remove(COUNTER_ID);
                callback.accept(server, player, handler, buf, sender);
                return true;
            }
        } else {
            counters.put(COUNTER_ID, 0f);
            System.out.println("Counter SET:" + COUNTER_ID + " " + time);

            checks.put(COUNTER_ID, () -> jejeSchedule(time, COUNTER_ID, callback, server, player, handler, buf, sender));
            System.out.println("Check SET:" + COUNTER_ID + " " + time);
            return false;
        }
        return false;
    }

    //------------------------------------------------------------------------------
}
