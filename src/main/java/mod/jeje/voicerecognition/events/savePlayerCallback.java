package mod.jeje.voicerecognition.events;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.session.report.ReporterEnvironment;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class savePlayerCallback {
    /*
    I still don't have a use for this, but just in case.

    Description: Takes 2 inputs, a player and a function as a lambda, when the player joins if the execOnJoin is
    registered in the JOIN event, then it should execute all the callbacks for that player.
     */

    private static List<ServerPlayerEntity> players;
    private static List<Runnable> toExec;

    public static void add(ServerPlayerEntity player, Runnable function){
        players.add(player);
        toExec.add(function);
    }

    public static void execOnJoin(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server){
        if (players.contains(handler.player)){
            for (int i = 0; i < players.size(); i++){
                if (players.get(i) == handler.player){
                    players.remove(i);
                    toExec.get(i).run();
                    toExec.remove(i);
                }
            }
        }
    }

}
