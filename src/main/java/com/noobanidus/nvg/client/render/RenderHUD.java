package com.noobanidus.nvg.client.render;

import com.noobanidus.nvg.NightVisionGoggles;
import com.noobanidus.nvg.util.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = NightVisionGoggles.MODID)
@SuppressWarnings("unused")
public class RenderHUD {
  public static HUDSlot slot = new HUDSlot();
  public static ItemStack NIGHT_VISION = new ItemStack(net.minecraft.init.Items.GLOWSTONE_DUST);
  public static ItemStack MOB_VISION = new ItemStack(net.minecraft.init.Items.SKULL);
  public static EntitySlime slime;

  private RenderHUD() {
  }

  // TODO: DEBUGGING
  private static int lastDrew = 0;

  @SideOnly(Side.CLIENT)
  public static EntitySlime getSlime() {
    if (slime == null) {
      Minecraft mc = Minecraft.getMinecraft();
      slime = new EntitySlime(mc.world);
    }

    slime.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 10, 0, true, false));
    return slime;
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void onDrawScreenPost(RenderGameOverlayEvent.Post event) {
    if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) return;

    if (!NightVisionGoggles.CONFIG.SNEAK_HUD) return;

    Minecraft mc = Minecraft.getMinecraft();

    if (mc.player.isSneaking()) {
      lastDrew += 1;
      if (lastDrew >= NightVisionGoggles.CONFIG.SNEAK_TICKS) {
        slot.render(event.getResolution(), event.getPartialTicks());
      }
    } else {
      lastDrew = 0;
    }

  }

  private static class HUDSlot {
    @SideOnly(Side.CLIENT)
    public void render(ScaledResolution res, float partialTicks) {
      float x = 30; //res.getScaledWidth() / 2.0f;
      float y = res.getScaledHeight() - 50f;

      if (NightVisionGoggles.CONFIG.COMPLEX_DURABILITY) {
        y -= 25f;
      }

      Minecraft mc = Minecraft.getMinecraft();

      ItemStack stack = InventoryUtil.getGoggles(mc.player);
      if (stack.isEmpty()) return;

      GlStateManager.pushMatrix();
      GlStateManager.translate(x, y, 0);
      RenderHelper.enableGUIStandardItemLighting();
      mc.getRenderItem().renderItemAndEffectIntoGUI(stack, 0, 0);
      mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, stack, 0, 0, null);

      FontRenderer fr = mc.fontRenderer;
      GlStateManager.disableLighting();
      GlStateManager.disableBlend();
      GlStateManager.disableDepth();

      int max = stack.getMaxDamage();
      int cur = max - stack.getItemDamage();

      if (NightVisionGoggles.CONFIG.COMPLEX_DURABILITY) {
        fr.drawStringWithShadow(String.format("%d/%d", cur, max), -13.5f, 20f, 16777215);
      }

      GlStateManager.enableLighting();
      GlStateManager.enableDepth();
      GlStateManager.enableBlend();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.popMatrix();
    }
  }
}
