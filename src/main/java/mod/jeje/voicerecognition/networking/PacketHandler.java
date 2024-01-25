package mod.jeje.voicerecognition.networking;

import mod.jeje.voicerecognition.Jeje_voiceRecognition;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class PacketHandler {
    public static final Identifier EVENT_ID = new Identifier(Jeje_voiceRecognition.MOD_ID, "event");
    public static final Identifier SEND_STRINGS_ID = new Identifier(Jeje_voiceRecognition.MOD_ID, "sendstrings");
    public static final Identifier SYNC_LIST = new Identifier(Jeje_voiceRecognition.MOD_ID, "synclist");
    public static final Identifier DISP_DET_WORDS = new Identifier(Jeje_voiceRecognition.MOD_ID, "displaywords");

    public static void registerC2SPackets() {

        ServerPlayNetworking.registerGlobalReceiver(EVENT_ID, C2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SEND_STRINGS_ID, C2SPacket::receiveString);

    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SYNC_LIST, S2CPacket::receiveSyncList);
        ClientPlayNetworking.registerGlobalReceiver(DISP_DET_WORDS, S2CPacket::receiveDisplayDetectedWords);
    }
}
