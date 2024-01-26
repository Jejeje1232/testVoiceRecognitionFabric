package mod.jeje.voicerecognition.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class FoodEffectsMixin {
    @Inject(method = "applyFoodEffects", at = @At("TAIL"))
    protected void jejeOnFinishUsing(ItemStack stack, World world, LivingEntity targetEntity, CallbackInfo ci){
        if (targetEntity.getClass() == ServerPlayerEntity.class){
            if (stack.getItem() == Items.GOLDEN_APPLE){
                Objects.requireNonNull(((ServerPlayerEntity) targetEntity).getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(20);
            }
        }
    }
}
