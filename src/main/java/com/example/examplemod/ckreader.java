package com.example.examplemod;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class ckreader {
    private String languages;
    private Logger LOGGER = LogManager.getLogger();
    public HashMap<String, Object> getsit(){
        String blocks;
        String pass="no";
        String[] whitelist=null;
        HashMap<String, Object> Sit = new HashMap<String, Object>();
        File file1=new File(System.getProperty("user.dir")+"/mods/ckapp");
        String curDir = System.getProperty("user.dir");
        if(!file1.exists()) {
            file1.mkdir();
            LOGGER.info("create dir success");
        }
        File file2 = new File(curDir+"/mods/ckapp/sitting.txt");
        if (!file2.exists()){
            try {
                file2.createNewFile();
                FileWriter writer = new FileWriter(file2);
                writer.write("sitVersion=2\nlanguage=en-us\nblock=Bedrock\nwhitelist=null");
                writer.flush();
                writer.close();
                LOGGER.info("create sit main file success");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file2));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String ls;
        try {
            if(!br.readLine().equals("sitVersion=2")){
                FileWriter writer = new FileWriter(file2);
                writer.write("sitVersion=2\nlanguage=en-us\nblock=Bedrock\nwhitelist=null");
                writer.flush();
                writer.close();
                languages="en-us";
                blocks="Bedrock";
                pass="ok";
                LOGGER.info("update sit file success");
            }else{
                ls = br.readLine();
                languages = ls.substring(9);
                ls = br.readLine();
                blocks = ls.substring(6);
                ls = br.readLine();
                if(ls.equals("whitelist=null")){
                    pass="ok";
                }else {
                    ls=ls.substring(10);
                    whitelist=ls.split(",");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!(languages.equals("en-us")||languages.equals("zh-cn"))){
            LOGGER.error("language sit error");
            languages="en-us";
        }
        Sit.put("language",languages);
        Sit.put("pass",pass);
        Sit.put("blocks",blocks);
        Sit.put("whitelist",whitelist);
        return Sit;
    }
    public ArrayList<String> getlanguagesit(){
        ArrayList<String> data=new ArrayList<String>();
        File file1=new File(System.getProperty("user.dir")+"/mods/ckapp/language");
        if(!file1.exists()) {
            file1.mkdir();
            LOGGER.info("create language dir success");
            File file=new File(System.getProperty("user.dir")+"/mods/ckapp/language/en-us.txt");
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write("start search\nBreak\nPlace\nsearch Finish use ");
                writer.flush();
                writer.close();
                file=new File(System.getProperty("user.dir")+"/mods/ckapp/language/zh-cn.txt");
                file.createNewFile();
                writer = new FileWriter(file);
                writer.write("开始搜索\n破坏\n放置\n搜索完成 一共使用 ");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        File file2=new File(System.getProperty("user.dir")+"/mods/ckapp/language/"+languages+".txt");
        try {
            String ls;
            BufferedReader br = new BufferedReader(new FileReader(file2));
            while ((ls= br.readLine()) != null) {
                data.add(ls);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
