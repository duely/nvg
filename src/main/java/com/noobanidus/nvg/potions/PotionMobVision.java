package com.noobanidus.nvg.potions;

import com.noobanidus.nvg.NightVisionGoggles;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@Mod.EventBusSubscriber(modid = NightVisionGoggles.MODID)
public class PotionMobVision extends Potion {
  public static final ResourceLocation POTION_TEXTURE = new ResourceLocation(NightVisionGoggles.MODID, "textures/gui/potions.png");

  public PotionMobVision() {
    super(false, 0xecbb10);
    setRegistryName(NightVisionGoggles.MODID, "mob_vision");
    setPotionName("nvg.potion.mob_vision");
    setBeneficial();
    setIconIndex(1, 0);
  }

  @Override
  public boolean isReady(int duration, int amplifier) {
    return true;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public int getStatusIconIndex() {
    Minecraft.getMinecraft().renderEngine.bindTexture(POTION_TEXTURE);
    return super.getStatusIconIndex();
  }

  @Override
  public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
    if (entityLivingBaseIn.world.isRemote) return;
    if (entityLivingBaseIn instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer) entityLivingBaseIn;

      AxisAlignedBB aabb = new AxisAlignedBB(player.posX, player.posY, player.posZ, player.posX, player.posY, player.posZ).grow(NightVisionGoggles.CONFIG.MOB_VISION_RANGE);
      List<EntityLivingBase> mobs = player.world.getEntitiesWithinAABB(EntityLivingBase.class, aabb, (Entity e) -> e != null && (e instanceof IMob || e.isCreatureType(EnumCreatureType.MONSTER, false)));
      if (player.isSneaking()) {
        mobs.addAll(player.world.getEntitiesWithinAABB(EntityPlayer.class, aabb));
      }
      for (EntityLivingBase mob : mobs) {
        mob.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 10, 0, true, false));
      }
    }
  }

  @SubscribeEvent
  public static void onEffectExpire(PotionEvent.PotionRemoveEvent event) {
    if (event.getPotion() != NightVisionGoggles.MOB_VISION) return;
    if (!(event.getEntityLiving() instanceof EntityPlayer)) return;
    if (event.getEntityLiving().world.isRemote) return;

    EntityPlayer player = (EntityPlayer) event.getEntityLiving();
    AxisAlignedBB aabb = new AxisAlignedBB(player.posX, player.posY, player.posZ, player.posX, player.posY, player.posZ).grow(NightVisionGoggles.CONFIG.MOB_VISION_RANGE);
    List<EntityLivingBase> mobs = player.world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
    for (EntityLivingBase mob : mobs) {
      mob.removePotionEffect(MobEffects.GLOWING);
    }
  }
}

