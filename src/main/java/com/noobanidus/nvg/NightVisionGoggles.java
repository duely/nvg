package com.noobanidus.nvg;

import com.noobanidus.nvg.init.Config;
import com.noobanidus.nvg.proxy.ISidedProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
@Mod(modid = NightVisionGoggles.MODID, name = NightVisionGoggles.MODNAME, version = NightVisionGoggles.VERSION, dependencies = NightVisionGoggles.DEPENDS)
@SuppressWarnings("WeakerAccess")
public class NightVisionGoggles {
    public static final String MODID = "nvg";
    public static final String MODNAME = "Night Vision Goggles";
    public static final String VERSION = "GRADLE:VERSION";
    public static final String DEPENDS = "required-after:baubles;";

    @SuppressWarnings("unused")
    public final static Logger LOG = LogManager.getLogger(MODID);
    public final static Config CONFIG = new Config();

    @SidedProxy(modId = MODID, clientSide = "com.noobanidus.nvg.proxy.ClientProxy", serverSide = "com.noobanidus.nvg.proxy.CommonProxy")
    public static ISidedProxy proxy;

    public static NightVisionGogglesCT TAB;

    @Mod.Instance(NightVisionGoggles.MODID)
    public static NightVisionGoggles instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        proxy.loadComplete(event);
    }

}
