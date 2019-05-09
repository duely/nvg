package com.noobanidus.nvg.util;

import com.noobanidus.nvg.NightVisionGoggles;
import com.noobanidus.nvg.compat.baubles.goggles.BaubleInventoryUtil;
import com.noobanidus.nvg.init.Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NightVisionGoggles.MODID)
public class InventoryUtil {
    public static ItemStack getGoggles(EntityPlayer player) {
        ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        if (!stack.isEmpty() && stack.getItem() == Items.goggles) return stack;

        if (Loader.isModLoaded("baubles")) {
            return BaubleInventoryUtil.getGoggles(player);
        }

        return ItemStack.EMPTY;
    }
}