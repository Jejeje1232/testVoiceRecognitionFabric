package mod.jeje.voicerecognition;

import mod.jeje.voicerecognition.networking.PacketHandler;
import mod.jeje.voicerecognition.voskTest.voskTest;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Jeje_voiceRecognition implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "jeje_voicerecognition";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Identifier WORDS_ADDED = new Identifier(MOD_ID, "words_added");

	//Get server context

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		ServerLifecycleEvents.SERVER_STARTED.register(commandHandler::onServerStart);
		PacketHandler.registerC2SPackets();

    }
}