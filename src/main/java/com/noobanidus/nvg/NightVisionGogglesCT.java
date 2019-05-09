package com.noobanidus.nvg;

import com.noobanidus.nvg.init.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class NightVisionGogglesCT extends CreativeTabs {
    public NightVisionGogglesCT(int id, String id2) {
        super(id, id2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack createIcon() {
        return new ItemStack(Items.goggles);
    }
}
