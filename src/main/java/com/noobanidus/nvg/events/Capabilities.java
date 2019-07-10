package com.noobanidus.nvg.events;

import com.noobanidus.nvg.NightVisionGoggles;
import com.noobanidus.nvg.compat.baubles.CapabilityHandler;
import com.noobanidus.nvg.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = NightVisionGoggles.MODID)
public class Capabilities {

  @SubscribeEvent
  public static void onItemCapability(AttachCapabilitiesEvent<ItemStack> event) {
    if (event.getObject().getItem() == Items.goggles) {
      event.addCapability(new ResourceLocation(NightVisionGoggles.MODID, "goggles"), CapabilityHandler.INSTANCE);
    }
  }
}
