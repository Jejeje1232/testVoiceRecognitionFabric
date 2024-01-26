package mod.jeje.voicerecognition.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class PlayerSleepMixin extends Entity implements Attackable {

    @Unique
    private LivingEntity thisEntity;

    public PlayerSleepMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyReturnValue(method = "isSleepingInBed", at = @At("RETURN"))
    protected boolean jejeOverridePlayerSleep(boolean original){
        if (((LivingEntity) (Object) this).getClass() == ServerPlayerEntity.class){return true;}
        return original;
    }
}
