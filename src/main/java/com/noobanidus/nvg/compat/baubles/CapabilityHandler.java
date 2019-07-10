package com.noobanidus.nvg.compat.baubles;

import baubles.api.cap.BaublesCapabilities;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityHandler implements ICapabilityProvider {
  public static final CapabilityHandler INSTANCE = new CapabilityHandler();

  @Override
  public boolean hasCapability(@Nonnull net.minecraftforge.common.capabilities.Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == BaublesCapabilities.CAPABILITY_ITEM_BAUBLE;
  }

  @Nullable
  @Override
  public <T> T getCapability(@Nonnull net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable EnumFacing facing) {
    return capability == BaublesCapabilities.CAPABILITY_ITEM_BAUBLE ? BaublesCapabilities.CAPABILITY_ITEM_BAUBLE.cast(BaubleGoggles.bauble) : null;
  }
}
