package mod.jeje.voicerecognition.utils;

import mod.jeje.voicerecognition.custom.JejeDamageSource;
import mod.jeje.voicerecognition.flags.jejeFlags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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

    public static boolean checkZombificationEnv(@NotNull ServerPlayerEntity player){
        if ((player.getWorld().getRegistryKey() != World.OVERWORLD) || !player.getWorld().isDay() || player.isTouchingWaterOrRain() || player.isOnFire()){return false;}
        return player.getWorld().isSkyVisible(player.getBlockPos());
    }

    public static void playerZombificationHandler(MinecraftServer server){
        if (!jejeFlags.ZOMBIFICATION){return;}

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()){
            if (checkZombificationEnv(player)){player.setFireTicks(100);}
        }
    }

    public static boolean checkEnderificationEnv(@NotNull ServerPlayerEntity player){
        //This does not take into account splash water nor drinking water.
        return player.isTouchingWaterOrRain();
    }

    public static void playerEnderificationHandler(MinecraftServer server){
        if (!jejeFlags.ENDERIFICATION){return;}


        DynamicRegistryManager DynamicRManager = Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getRegistryManager();
        JejeDamageSource jejeDamageSource = new JejeDamageSource(DynamicRManager);

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()){
            if (checkEnderificationEnv(player)){player.damage(jejeDamageSource.getWither(), 1f);}
        }
    }

    public static boolean teleport(ServerPlayerEntity player,double x, double y, double z, boolean particleEffects) {
        LivingEntity livingEntity;
        double d = player.getX();
        double e = player.getY();
        double f = player.getZ();
        double g = y;
        boolean bl = false;
        BlockPos blockPos = BlockPos.ofFloored(x, g, z);
        World world = player.getWorld();
        if (world.isChunkLoaded(blockPos)) {
            boolean bl2 = false;
            while (!bl2 && blockPos.getY() > world.getBottomY()) {
                BlockPos blockPos2 = blockPos.down();
                BlockState blockState = world.getBlockState(blockPos2);
                if (blockState.blocksMovement()) {
                    bl2 = true;
                    continue;
                }
                g -= 1.0;
                blockPos = blockPos2;
            }
            if (bl2) {
                player.requestTeleport(x, g, z);
                if (world.isSpaceEmpty(player) && !world.containsFluid(player.getBoundingBox())) {
                    bl = true;
                }
            }
        }
        if (!bl) {
            player.requestTeleport(d, e, f);
            return false;
        }
        if (particleEffects) {
            world.sendEntityStatus(player, EntityStatuses.ADD_PORTAL_PARTICLES);
        }
        return true;
    }


    public static boolean teleportTo(ServerPlayerEntity player, double x, double y, double z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);
        while (mutable.getY() > player.getWorld().getBottomY() && !player.getWorld().getBlockState(mutable).blocksMovement()) {
            mutable.move(Direction.DOWN);
        }
        BlockState blockState = player.getWorld().getBlockState(mutable);
        boolean bl = blockState.blocksMovement();
        boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
        if (!bl || bl2) {
            return false;
        }
        Vec3d vec3d = player.getPos();
        boolean bl3 = someHelpers.teleport(player, x, y, z, true);
        if (bl3) {
            player.getWorld().emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(player));
            if (!player.isSilent()) {
                player.getWorld().playSound(null, player.prevX, player.prevY, player.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, player.getSoundCategory(), 1.0f, 1.0f);
                player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            }
        }
        return bl3;
    }

    public static boolean teleportRandomly(ServerPlayerEntity player, Random random) {
        double d = player.getX() + (random.nextDouble() - 0.5) * 64.0;
        double e = player.getY() + (double)(random.nextInt(64) - 32);
        double f = player.getZ() + (random.nextDouble() - 0.5) * 64.0;
        return someHelpers.teleportTo(player, d, e, f);
    }
}
