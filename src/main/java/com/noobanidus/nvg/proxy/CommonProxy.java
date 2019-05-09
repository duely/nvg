package com.noobanidus.nvg.proxy;

import com.noobanidus.nvg.NightVisionGogglesCT;
import com.noobanidus.nvg.NightVisionGoggles;
import com.noobanidus.nvg.init.Items;
import com.noobanidus.nvg.network.PacketHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.*;

public class CommonProxy implements ISidedProxy {
    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.initPackets();
        NightVisionGoggles.TAB = new NightVisionGogglesCT(CreativeTabs.getNextID(), NightVisionGoggles.MODID);
        Items.preInit();
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public void loadComplete(FMLLoadCompleteEvent event) {
        NightVisionGoggles.LOG.info("NightVisionGoggles: Load Complete.");
        NightVisionGoggles.CONFIG.save();
    }

    public void serverStarting(FMLServerStartingEvent event) {
    }

    public void serverStarted(FMLServerStartedEvent event) {
    }
}
