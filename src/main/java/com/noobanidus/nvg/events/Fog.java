package com.noobanidus.nvg.events;

import com.noobanidus.nvg.NightVisionGoggles;
import com.noobanidus.nvg.init.Config;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class Fog {
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onRenderFog(EntityViewRenderEvent.FogDensity e) {
    if (e.getState().getMaterial() == Material.WATER && NightVisionGoggles.CONFIG.UNDERWATER_FOG) {
      e.setDensity(0f);
      e.setCanceled(true);
    }
  }
}
