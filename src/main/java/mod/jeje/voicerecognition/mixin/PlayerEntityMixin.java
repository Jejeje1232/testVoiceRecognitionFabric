package mod.jeje.voicerecognition.mixin;

import mod.jeje.voicerecognition.flags.jejeFlags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "wakeUp(ZZ)V", at = @At("HEAD"), cancellable = true)
    protected void jejeDontWakeUp(boolean skipSleepTimer, boolean updateSleepingPlayers, CallbackInfo ci){
        if (jejeFlags.SLEEP_DIRTY_PER_PLAYER.contains(this.getUuid())){ci.cancel();}
    }
}
