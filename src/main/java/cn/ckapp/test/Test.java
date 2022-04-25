package cn.ckapp.test;

import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.event.world.BlockEvent;
import org.stringtemplate.v4.ST;


import java.util.ArrayList;
import java.util.HashMap;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("test")
public class Test {
public String languages,blocks;
public String pass="no";
public String[] whitelist=null;
public ArrayList<String> languagesit=new ArrayList<String>();
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Test() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code

    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("test", "helloworld", () -> {

            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // do something when the server starts
        ckreader ckreaders=new ckreader();
        HashMap<String, Object> sitting=ckreaders.getsit();
        languages=(String) sitting.get("language");
        pass=(String) sitting.get("pass");
        whitelist=(String[]) sitting.get("whitelist");
        blocks=(String) sitting.get("blocks");
        languagesit=ckreaders.getlanguagesit();
        }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
        }
    }

    @SubscribeEvent
    public void breakss(BlockEvent.BreakEvent event) {
        new breakchack(event,languages,blocks,pass,whitelist,languagesit);
    }
    @SubscribeEvent
    public void breakss(BlockEvent.EntityPlaceEvent event) {
        new EntityPlace(event);
    }
    }
