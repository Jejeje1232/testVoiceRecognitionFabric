package mod.jeje.voicerecognition.utils;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class someHelpers {
    public static void swapPlayerPositions(MinecraftServer server) {
        int playerCount = server.getPlayerManager().getPlayerList().size();

        List<ServerWorld> WORLDs = new ArrayList<ServerWorld>();
        List<Vec3d> POSs = new ArrayList<Vec3d>();
        List<Float> YAWs = new ArrayList<Float>();
        List<Float> PITCHs = new ArrayList<Float>();
        
        for (int i = 0; i < playerCount; i++) {
            ServerPlayerEntity nextPlayer = server.getPlayerManager().getPlayerList().get((i + 1) % playerCount);

            Vec3d nextPos = nextPlayer.getPos();
            float nextYaw = nextPlayer.getYaw();
            float nextPitch = nextPlayer.getPitch();
            ServerWorld nextWorld = nextPlayer.getServerWorld();
            
            WORLDs.add(nextWorld);
            POSs.add(nextPos);
            YAWs.add(nextYaw);
            PITCHs.add(nextPitch);

            // Swap positions

            //nextPlayer.teleport(currentWorld, currentPos.x, currentPos.y, currentPos.z, currentYaw, currentPitch);
        }

        for (int i = 0; i < playerCount; i++) {
            ServerPlayerEntity currentPlayer = server.getPlayerManager().getPlayerList().get(i);
            currentPlayer.teleport(WORLDs.get(i), POSs.get(i).x, POSs.get(i).y, POSs.get(i).z, YAWs.get(i) , PITCHs.get(i));
        }
    }
}
