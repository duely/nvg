package com.noobanidus.nvg.init;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {
    private final Configuration CONFIG = new Configuration(new File("config", "nvg.cfg"), true);

    public int MAX_DURABILITY = CONFIG.get("General", "Durability", 256, "Base durability of the helmet.").getInt();

    public Config() {
    }

    public void save() {
        CONFIG.save();
    }
}
