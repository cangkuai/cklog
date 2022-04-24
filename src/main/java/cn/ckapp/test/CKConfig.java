package cn.ckapp.test;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CKConfig {
    public static final Pair<CKConfig, ForgeConfigSpec> pair;
    public static String sitVersion;
    public static String block;
    public static String whitelist;

    CKConfig(ForgeConfigSpec.Builder builder) {
        builder.define("sitVersion", "2");
        builder.define("block", "bedrock");
        builder.define("whitelist", "");
    }

    static {
        pair = new ForgeConfigSpec.Builder().configure(CKConfig::new);
    }
}
