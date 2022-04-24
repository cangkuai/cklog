package cn.ckapp.test;

import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("test")
public class Test {
    public static boolean pass = false;
    public static Set<String> whitelist = new HashSet<>();
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Test() {
//        // Register the setup method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
//        // Register the enqueueIMC method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
//        // Register the processIMC method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, CKConfig.pair.getValue(), "sitting.toml");
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        init();
    }

    private static void init() {
        String curDir = System.getProperty("user.dir");
        File file = new File(curDir + "/mods/ckapp");
        if (!file.exists()) {
            file.mkdir();
        }
    }

//    private void setup(final FMLCommonSetupEvent event) {
//        // some preinit code
//
//    }
//
//    private void enqueueIMC(final InterModEnqueueEvent event) {
//        // some example code to dispatch IMC to another mod
//        InterModComms.sendTo("test", "helloworld", () -> {
//
//            return "Hello world";
//        });
//    }
//
//    private void processIMC(final InterModProcessEvent event) {
//        // some example code to receive and process InterModComms from other mods
//
//    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
//    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
//    public static class RegistryEvents {
//        @SubscribeEvent
//        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
//            // register a new block here
//        }
//    }

    @SubscribeEvent
    public void onBreak(BlockEvent.BreakEvent event) {
        Player players = event.getPlayer();
        String curDir = System.getProperty("user.dir");
        long times = System.currentTimeMillis() / 1000;
        Block block = event.getState().getBlock();
        String ls;
        String menss = event.getPos().toString();
        int len = menss.length();
        File file = new File(curDir + "/mods/ckapp/data");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!whitelist.isEmpty()) {
            for (String player : whitelist) {
                if (event.getPlayer().getName().getString().equals(player)) {
                    pass = true;
                }
            }
        }
        if (block.getRegistryName().getPath().equals(CKConfig.block.toLowerCase()) && pass) {
            event.getPlayer().sendMessage(new TranslatableComponent("cn.ckapp.run"), Util.NIL_UUID);
            long startTime = System.currentTimeMillis();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                while ((ls = br.readLine()) != null) {
                    String ls2 = ls.substring(0, len);
                    if (ls2.equals(menss)) {

                        String[] ls1 = ls.split(",");
                        String[] ls3 = ls.split("'");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String dateString = formatter.format(new Date(Long.parseLong(ls1[8] + "000")));
                        if (ls1[10].equals("0")) {
                            event.getPlayer().sendMessage(new TextComponent(ls3[1] + " " + dateString + " " + ls1[9] + " ").append(new TranslatableComponent("cn.ckapp.break")), Util.NIL_UUID);
                        } else {
                            event.getPlayer().sendMessage(new TextComponent(ls3[1] + " " + dateString + " " + ls1[9] + " ").append(new TranslatableComponent("cn.ckapp.place")), Util.NIL_UUID);
                        }
                        if (whitelist != null) {
                            pass = false;
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            long endTime = System.currentTimeMillis();
            event.getPlayer().sendMessage(new TextComponent("").append(new TranslatableComponent("cn.ckapp.finish")).append((endTime - startTime) + " ms"), Util.NIL_UUID);
        }
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(menss + "," + players + "," + times + "," + block + ",0\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public void onPlace(BlockEvent.EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        String curDir = System.getProperty("user.dir");
        long times = System.currentTimeMillis() / 1000;
        File file = new File(curDir + "/mods/ckapp/data");
        Block block = event.getPlacedBlock().getBlock();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(event.getPos().toString() + "," + entity + "," + times + "," + block + ",1\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
