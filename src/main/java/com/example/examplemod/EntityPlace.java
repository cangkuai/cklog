package com.example.examplemod;
import net.minecraftforge.event.world.BlockEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class EntityPlace {
    public EntityPlace(BlockEvent.EntityPlaceEvent event){
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
