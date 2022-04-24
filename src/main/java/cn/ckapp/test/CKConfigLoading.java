package cn.ckapp.test;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CKConfigLoading {
    private static final Logger LOGGER = LogManager.getLogger();
    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        CKConfig.sitVersion = event.getConfig().getConfigData().get("sitVersion");
        CKConfig.block = event.getConfig().getConfigData().get("block");
        CKConfig.whitelist = event.getConfig().getConfigData().get("whitelist");

        if (!CKConfig.sitVersion.equals("2")) {
            Test.pass = true;
            LOGGER.info("update sit file success");
        } else {
            if (CKConfig.whitelist == null) {
                Test.pass = true;
            } else {
                Test.whitelist.add(CKConfig.whitelist);
            }
        }
    }
}
