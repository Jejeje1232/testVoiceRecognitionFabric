package mod.jeje.voicerecognition.mixin;

import mod.jeje.voicerecognition.flags.jejeFlags;
import mod.jeje.voicerecognition.utils.someHelpers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.SleepingChatScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.core.jmx.Server;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.UUID;

@Mixin(SleepingChatScreen.class)
public abstract class SleepScreenMixin extends ChatScreen {


    public SleepScreenMixin(String originalChatText) {
        super(originalChatText);
    }


    @Inject(method = "stopSleeping", at = @At("HEAD"))
    protected void jejeSleepDirty(CallbackInfo ci){
        UUID playerUUID = this.client.player.getUuid();

        if (jejeFlags.SLEEP_DIRTY_PER_PLAYER.contains(playerUUID)){
            someHelpers.setSleepDirtyFalse(playerUUID);
        }
    }
}
