package mod.jeje.voicerecognition.events;


import mod.jeje.voicerecognition.flags.jejeFlags;
import mod.jeje.voicerecognition.utils.someHelpers;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static mod.jeje.voicerecognition.utils.someHelpers.random;
import static net.minecraft.entity.SpawnReason.TRIGGERED;

// This is a C2S package:
public class jejeEvents {
    //private static boolean testEraseLater = false;
    static Class<?> thisClass = jejeEvents.class;
    public static Method[] methodList = thisClass.getDeclaredMethods();
    private static Random random = new Random();

    public static void Creepers(MinecraftServer server, @NotNull ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        EntityType.CREEPER.spawn((ServerWorld) player.getServerWorld(), player.getBlockPos(), TRIGGERED);
    }

    public static void Ghasts(MinecraftServer server, @NotNull ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        EntityType.GHAST.spawn((ServerWorld) player.getServerWorld(), player.getBlockPos(), TRIGGERED);
    }

    public static void Witches(MinecraftServer server, @NotNull ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        EntityType.WITCH.spawn((ServerWorld) player.getServerWorld(), player.getBlockPos().add(4, 0, 0), TRIGGERED);
        EntityType.WITCH.spawn((ServerWorld) player.getServerWorld(), player.getBlockPos().add(-4, 0, 0), TRIGGERED);
        EntityType.WITCH.spawn((ServerWorld) player.getServerWorld(), player.getBlockPos().add(0, 0, -4), TRIGGERED);
        EntityType.WITCH.spawn((ServerWorld) player.getServerWorld(), player.getBlockPos().add(0, 0, 4), TRIGGERED);
    }

    public static void Thunder(MinecraftServer server, @NotNull ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        EntityType.LIGHTNING_BOLT.spawn((ServerWorld) player.getServerWorld(), player.getBlockPos(), TRIGGERED);
    }

    public static void TNT(MinecraftServer server, @NotNull ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        EntityType.TNT.spawn((ServerWorld) player.getServerWorld(), player.getBlockPos(), TRIGGERED);
    }

    public static void Entangled(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        someHelpers.swapPlayerPositions(server);
    }

    public static void TPEvent(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //TPs the player to a random nearby location.
        for (int i = 0; i < 64; ++i) {
            if (player == null){break;}
            if (!someHelpers.teleportRandomly(player, random)){continue;};
        }
    }

    public static void Erase(MinecraftServer server, @NotNull ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //Erase a random item from the player's inventory.
        ItemStack stack = player.getInventory().main.get(random.nextInt(0, player.getInventory().main.size()));
        int slot = player.getInventory().getSlotWithStack(stack);
        player.getInventory().removeStack(slot);
    }

    public static void Venezuela(MinecraftServer server, @NotNull ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //Erases all the food from the player's inventory.
        PlayerInventory playerInv = player.getInventory();
        playerInv.main.forEach((itemStack) -> {
            if (itemStack.isFood()){
                int slot = playerInv.getSlotWithStack(itemStack);
                playerInv.removeStack(slot);
            }
        });
    }

    public static void Drop(MinecraftServer server, @NotNull ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //Drop every item from the player's inventory.
        player.getInventory().dropAll();
    }

    public static void Blindness(MinecraftServer server, @NotNull ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //Give the player blindness.
        StatusEffectInstance playerEffect = new StatusEffectInstance(StatusEffects.BLINDNESS, random.nextInt(6000, 18000), 1);
        player.addStatusEffect(playerEffect);
    }

    public static void Nausea(MinecraftServer server, @NotNull ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //Give the player Nausea.
        StatusEffectInstance playerEffect = new StatusEffectInstance(StatusEffects.NAUSEA, 600, 1);
        player.addStatusEffect(playerEffect);
    }

//    public static void Fake(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
//        //Fake a hostile mob sound.
//    }

//    public static void Rick(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
//        //Rickroll the player.
//    }

//    public static void Skibiddi(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
//        //I forgor.
//    }

    public static void TimeUp(@NotNull MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        ServerWorld world = server.getWorld(ServerWorld.OVERWORLD);
        assert world != null;
        world.getTickManager().setTickRate(200);
        jejeEventsCallbacksHandler.jejeSchedule(5,"TimeUp", jejeEventsCallbacks::TPTEST_Reset,server);
    }

    public static void TimeDown(@NotNull MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        ServerWorld world = server.getWorld(ServerWorld.OVERWORLD);
        assert world != null;
        world.getTickManager().setTickRate(5);
        jejeEventsCallbacksHandler.jejeSchedule(5,"TimeDown", jejeEventsCallbacks::TPTEST_Reset,server);
    }

    public static void Invisizombie(MinecraftServer server, @NotNull ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        ZombieEntity theZombie = EntityType.ZOMBIE.spawn((ServerWorld) player.getServerWorld(), player.getBlockPos(), TRIGGERED);
        StatusEffectInstance zombieEffect = new StatusEffectInstance(StatusEffects.INVISIBILITY, 10000000, 1);
        assert theZombie != null;
        theZombie.addStatusEffect(zombieEffect);
    }

    public static void HPDOWN(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        if (player == null){return;}
        EntityAttributeInstance playerHP = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        assert playerHP != null;
        double currentHP = playerHP.getValue();

        if (currentHP == 1){player.kill();}
        playerHP.setBaseValue(currentHP-1);
    }

    public static void HPUP(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        if (player == null){return;}
        EntityAttributeInstance playerHP = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        assert playerHP != null;
        double currentHP = playerHP.getValue();
        playerHP.setBaseValue(currentHP+1);
    }

    public static void Zombification(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        jejeFlags.ZOMBIFICATION = true;
        jejeEventsCallbacksHandler.jejeSchedule(600,"ZombificationStop", jejeEventsCallbacks::Zombification_Reset);
    }

    public static void Enderification(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        jejeFlags.ENDERIFICATION = true;
        jejeEventsCallbacksHandler.jejeSchedule(600,"EnderificationStop", jejeEventsCallbacks::Enderification_Reset);
    }

    public static void Sleepyhead(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        jejeFlags.SLEEP_DIRTY_PER_PLAYER.add(player.getUuid());
        BlockPos pos = player.getBlockPos();
        player.setPose(EntityPose.SLEEPING);
        player.setPosition((double)pos.getX() + 0.5, (double)pos.getY() + 0.6875, (double)pos.getZ() + 0.5);
        player.setSleepingPosition(pos);
        player.setVelocity(Vec3d.ZERO);
        player.velocityDirty = true;
    }

    public static void WaHoo(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        float playerYaw = player.getHeadYaw();
        player.addVelocity(someHelpers.yawToUnitVec3d(playerYaw).multiply(1).add(0, 1, 0));
        player.velocityModified = true;
    }

    public static void DoubleTrouble(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //Execute 2 random events instead of 1.
        //Not really tested yet, but should work.
        for (int i = 0; i<2; i++){
            assert player != null;
            try{
                someHelpers.executeRandomMethod(random, server, player, handler, buf, sender);
            } catch (InvocationTargetException | IllegalAccessException ignore) {}}
    }

    public static void FishBoy(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        jejeFlags.FISH_BOY = true;

        jejeEventsCallbacksHandler.jejeSchedule(300,"FishBoyStop", jejeEventsCallbacks::FishBoy_Reset);
    }

    public static void ExplosiveDiarrhea(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        //Give speed and spawn multiple tnts;
        StatusEffectInstance playerEffect = new StatusEffectInstance(StatusEffects.SPEED, 30, 1);
        player.addStatusEffect(playerEffect);

        for (int i = -2; i < 3; i++){
            for (int k = -2; k < 3; k++){
                Vec3i toAdd = new Vec3i(i*3, 0, k*3);
                EntityType.TNT.spawn(player.getServerWorld(), player.getBlockPos().add(toAdd), TRIGGERED);
            }
        }
    }
}
