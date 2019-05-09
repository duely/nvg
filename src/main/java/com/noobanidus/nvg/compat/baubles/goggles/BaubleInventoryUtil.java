package com.noobanidus.nvg.compat.baubles.goggles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import com.noobanidus.nvg.init.Items;
import com.noobanidus.nvg.item.ItemGoggles;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class BaubleInventoryUtil {
    public static ItemStack getGoggles(EntityPlayer player) {
        IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
        for (int i : BaubleType.HEAD.getValidSlots()) {
            ItemStack stack = handler.getStackInSlot(i);
            if (stack.getItem() == Items.goggles) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
