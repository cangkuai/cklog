package cn.ckapp.test;

import net.minecraft.Util;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.event.world.BlockEvent;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class breakchack {
    public breakchack(BlockEvent.BreakEvent event, String languages, String blocks, String pass, String[] whitelist, ArrayList<String> languagesit){
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

        if(whitelist!=null)
        {
            for(String i:whitelist){
                if (i.equals(players)){
                    pass="ok";
                }
            }
        }
        if (names.equals(blocks)&&pass.equals("ok")){
            event.getPlayer().sendMessage(new TextComponent(languagesit.get(0)), Util.NIL_UUID);
            long startTime = System.currentTimeMillis();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                while ((ls= br.readLine()) != null) {
                    String ls2=ls.substring(0,len);
                    if (ls2.equals(menss)){
                        String types="";
                        String[] ls1=ls.split(",");
                        if (ls1[6].equals("0")){
                            types=languagesit.get(1);
                        }else {
                            types=languagesit.get(2);
                        }
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String dateString = formatter.format(new Date(Long.parseLong(ls1[4]+"000")));
                        event.getPlayer().sendMessage(new TextComponent(ls1[3] + " " + dateString + " " + ls1[5] + " " + types), Util.NIL_UUID);
                        if(whitelist!=null){
                            pass="no";
                        }
                    }
                }
            }catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            long endTime = System.currentTimeMillis();
            event.getPlayer().sendMessage(new TextComponent(languagesit.get(3) + (endTime - startTime) + " ms"), Util.NIL_UUID);
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
}
