package mod.jeje.voicerecognition;

import mod.jeje.voicerecognition.commands.CommandRegistries;
import mod.jeje.voicerecognition.commands.clearBannedWords;
import mod.jeje.voicerecognition.events.jejeEventsCallbacksHandler;
import mod.jeje.voicerecognition.events.someRegisters;
import mod.jeje.voicerecognition.networking.PacketHandler;
import mod.jeje.voicerecognition.setup.setupActions;
import mod.jeje.voicerecognition.utils.stringProcessing;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

		//LOGGER.info("Hello Fabric world!");

		ServerLifecycleEvents.SERVER_STARTING.register(setupActions::resetTrigger);

		//Initializes the command manager, in case the mod needs it.
		ServerLifecycleEvents.SERVER_STARTED.register(commandHandler::onServerStart);
		ServerLifecycleEvents.SERVER_STARTED.register(jejeEventsCallbacksHandler::toTest);
		//--------
		//Setup for the persistent data.
		ServerLifecycleEvents.SERVER_STARTED.register(setupActions::setupData);
		//--------
		//Initializes the main loop for the word ban.
		ServerLifecycleEvents.SERVER_STARTED.register(setupActions::setupMainBanLoop);
		//--------
		//Some events.
		ServerTickEvents.END_SERVER_TICK.register(jejeEventsCallbacksHandler::counterSteps);
		ServerTickEvents.END_SERVER_TICK.register(jejeEventsCallbacksHandler::checkFromCopy);

		//ServerTickEvents.END_SERVER_TICK.register(jejeEventsCallbacksHandler::checkSteps);
		//--------

		//Remove check list.
		ServerLifecycleEvents.SERVER_STOPPING.register(jejeEventsCallbacksHandler::forceTriggerAll);
		ServerLifecycleEvents.SERVER_STOPPING.register(jejeEventsCallbacksHandler::toTest);

		//Sync test.
		ServerPlayConnectionEvents.JOIN.register(setupActions::setupClientData);

		//Registries.
		PacketHandler.registerC2SPackets();
		CommandRegistries.registerCommands();
		someRegisters.registerEvents();
    }
}