package cn.ckapp.test;

import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("test")
public class Test {

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
        File file1=new File(System.getProperty("user.dir")+"/mods/ckapp");
        if(!file1.exists()) {
            file1.mkdir();
            LOGGER.info("create dir success");
        }
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
    String x =String.valueOf(event.getPos().getX());
    String y =String.valueOf(event.getPos().getY());
    String z =String.valueOf(event.getPos().getZ());
    String players=event.getPlayer().getName().getString();
    String curDir = System.getProperty("user.dir");
    String times=String.valueOf( System.currentTimeMillis()/1000);
    String names=event.getState().getBlock().getName().getString();
    String ls;
    String menss=x+","+y+","+z;
    Integer len=menss.length();
        File file = new File(curDir+"/mods/ckapp/data.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (names.equals("Bedrock")){
            event.getPlayer().sendMessage(new TextComponent("start search"),Util.NIL_UUID);
            long startTime = System.currentTimeMillis();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                while ((ls= br.readLine()) != null) {
                    String ls2=ls.substring(0,len);
                    if (ls2.equals(menss)){
                        String types;
                        String[] ls1=ls.split(",");
                        if (ls1[6].equals("0")){
                            types="Break";
                        }else {
                            types="Place";
                        }
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String dateString = formatter.format(new Date(Long.parseLong(ls1[4]+"000")));
                        event.getPlayer().sendMessage(new TextComponent(ls1[3]+" "+dateString+" "+ls1[5]+" "+types),Util.NIL_UUID);
                    }
                }
        }catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            long endTime = System.currentTimeMillis();
            event.getPlayer().sendMessage(new TextComponent("search Finish use "+(endTime - startTime) + " ms"),Util.NIL_UUID);
            }
        try {
            FileWriter writer = new FileWriter(file,true);
            writer.write(x+","+y+","+z+","+players+","+times+","+names+",0\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @SubscribeEvent
    public void breakss(BlockEvent.EntityPlaceEvent event) {
        String x =String.valueOf(event.getPos().getX());
        String y =String.valueOf(event.getPos().getY());
        String z =String.valueOf(event.getPos().getZ());
        String players=event.getEntity().getName().getString();
        String curDir = System.getProperty("user.dir");
        String times=String.valueOf( System.currentTimeMillis()/1000);
        File file = new File(curDir+"/mods/ckapp/data.txt");
        String names=event.getPlacedBlock().getBlock().getName().getString();
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FileWriter writer = new FileWriter(file,true);
            writer.write(x+","+y+","+z+","+players+","+times+","+names+",1\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    }
