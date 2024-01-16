package mod.jeje.voicerecognition.networking;

import mod.jeje.voicerecognition.Jeje_voiceRecognition;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class PacketHandler {
    public static final Identifier EVENT_ID = new Identifier(Jeje_voiceRecognition.MOD_ID, "event");

    public static void registerC2SPackets() {

        ServerPlayNetworking.registerGlobalReceiver(EVENT_ID, C2SPacket::receive);

    }

    public static void registerS2CPackets() {

    }
}
