package com.noobanidus.nvg.init;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {
  private final Configuration CONFIG = new Configuration(new File("config", "nvg.cfg"), true);

  public int MOB_VISION_RANGE = CONFIG.get("General", "Mob Vision Range", 64, "The range at which mobs will have the glow effect applied to them.").getInt();
  public int MAX_DURABILITY = CONFIG.get("General", "Durability", 256, "Base durability of the helmet.").getInt();
  public int SNEAK_TICKS = CONFIG.get("HUD", "Ticks", 45, "Number of ticks (1/20th of a second) you need to sneak for before the HUD will display.").getInt();
  public boolean SNEAK_HUD = CONFIG.get("HUD", "Show", true, "Whether or not to show the HUD with icons for night vision, mob vision, and durability.").getBoolean();
  public boolean COMPLEX_DURABILITY = CONFIG.get("HUD", "Complex Durability", true, "Whether or not to show the full numerical durability.").getBoolean();
  public boolean UNDERWATER_FOG = CONFIG.get("General", "Underwater Vision", true, "Whether or not wearing goggles will grant clarity while underwater").getBoolean();
  public boolean NETHER_FOG = CONFIG.get("General", "Nether Vision", true, "Whether or not wearing goggles will grant clarity while in the Nether").getBoolean();

  public Config() {
  }

  public void save() {
    CONFIG.save();
  }
}
