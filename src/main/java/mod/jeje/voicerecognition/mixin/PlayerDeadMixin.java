package mod.jeje.voicerecognition.mixin;

import mod.jeje.voicerecognition.utils.someHelpers;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerDeadMixin {

    @Shadow @Final
    public MinecraftServer server;
    @Inject(method = "onDeath", at = @At("HEAD"))
    protected void jejeOnDeath(DamageSource damageSource, CallbackInfo ci){
        someHelpers.killEveryone(server);
    }

}
