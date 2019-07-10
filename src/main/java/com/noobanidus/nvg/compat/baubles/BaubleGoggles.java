package com.noobanidus.nvg.compat.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.cap.BaubleItem;
import com.noobanidus.nvg.init.Items;
import com.noobanidus.nvg.item.ItemGoggles;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class BaubleGoggles extends BaubleItem {
  public static final IBauble bauble = new BaubleGoggles();

  public BaubleGoggles() {
    super(BaubleType.HEAD);
  }

  @Override
  public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
    Items.goggles.onUpdate(itemstack, player.world, player, -1, false);
  }

  @Override
  public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
    Items.goggles.clearActive((EntityPlayer) player, ItemGoggles.NIGHT_VISION);
    Items.goggles.clearActive((EntityPlayer) player, ItemGoggles.MOB_VISION);
  }
}
