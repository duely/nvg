package com.noobanidus.nvg.proxy;

import com.noobanidus.nvg.client.Keybinds;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        Keybinds.initKeybinds();
    }
}
