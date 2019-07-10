package com.noobanidus.nvg.events;

import com.noobanidus.nvg.NightVisionGoggles;
import com.noobanidus.nvg.util.InventoryUtil;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = NightVisionGoggles.MODID)
public class Fog {
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public static void onRenderFog(EntityViewRenderEvent.FogDensity e) {
    boolean inNether = false;
    if ((e.getState().getMaterial() == Material.WATER && NightVisionGoggles.CONFIG.UNDERWATER_FOG) || (inNether = e.getEntity().world.provider.getDimension() == -1 && NightVisionGoggles.CONFIG.NETHER_FOG)) {
      if (!InventoryUtil.getGoggles((EntityPlayer) e.getEntity()).isEmpty()) {
        if (inNether) {
          float farPlane = Minecraft.getMinecraft().gameSettings.renderDistanceChunks * 16;
          GlStateManager.setFogStart(0.0f);
          GlStateManager.setFogEnd(farPlane * 16.0f);
        }
        e.setDensity(0f);
        e.setCanceled(true);
      }
    }
  }
}
