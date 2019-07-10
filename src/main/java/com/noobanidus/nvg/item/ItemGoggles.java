package com.noobanidus.nvg.item;

import baubles.api.render.IRenderBauble;
import com.noobanidus.nvg.NightVisionGoggles;
import com.noobanidus.nvg.client.Keybinds;
import com.noobanidus.nvg.client.model.ModelGoggles;
import com.noobanidus.nvg.init.Items;
import com.noobanidus.nvg.util.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@Optional.Interface(iface = "baubles.api.render.IRenderBauble", modid = "baubles")
@Mod.EventBusSubscriber(modid = NightVisionGoggles.MODID)
public class ItemGoggles extends ItemArmor implements IRenderBauble {
  public static final ResourceLocation texture = new ResourceLocation(NightVisionGoggles.MODID, "textures/models/armor/goggles_layer_1.png");

  public static final String NIGHT_VISION = "NIGHT_VISION";
  public static final String MOB_VISION = "MOB_VISION";

  public static ArmorMaterial GOGGLES = EnumHelper.addArmorMaterial("goggles", "goggles", NightVisionGoggles.CONFIG.MAX_DURABILITY, new int[]{2, 0, 0, 0}, 25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.5f);

  public ItemGoggles() {
    super(GOGGLES, 4, EntityEquipmentSlot.HEAD);
    GOGGLES.setRepairItem(new ItemStack(net.minecraft.init.Items.IRON_INGOT));
    this.setMaxStackSize(1);
    this.setMaxDamage(NightVisionGoggles.CONFIG.MAX_DURABILITY);
    this.setCreativeTab(NightVisionGoggles.TAB);
  }

  @Override
  @SuppressWarnings("deprecation")
  public EnumRarity getRarity(ItemStack stack) {
    return EnumRarity.UNCOMMON;
  }

  @SubscribeEvent
  public static void onUnequip(LivingEquipmentChangeEvent event) {
    if (!(event.getEntityLiving() instanceof EntityPlayer)) return;

    if (event.getSlot() != EntityEquipmentSlot.HEAD) return;

    ItemStack stack = event.getFrom();
    ItemStack toStack = event.getTo();

    if (stack.getItem() != Items.goggles) return;
    if (toStack.getItem() == Items.goggles) return;

    EntityPlayer player = (EntityPlayer) event.getEntityLiving();
    Items.goggles.clearActive(player, NIGHT_VISION);
    Items.goggles.clearActive(player, MOB_VISION);
  }

  @Override
  public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean f) {
    if (entity instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer) entity;

      if (player.world.isRemote) return;

      ItemStack goggles = InventoryUtil.getGoggles(player);
      if (goggles.isEmpty()) {
        return;
      }

      boolean mv = getActive(stack, MOB_VISION);
      boolean nv = getActive(stack, NIGHT_VISION);

      int damage_chance = 0;

      if (nv) {
        damage_chance++;
        player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 419, 0, true, false));
      }

      if (mv) {
        damage_chance++;
        player.addPotionEffect(new PotionEffect(NightVisionGoggles.MOB_VISION, 419, 0, true, false));
      }

      if (player.ticksExisted % 200 == 0) {
        for (int j = 0; j < damage_chance; j++) {
          if (itemRand.nextInt(3) == 0 && !player.capabilities.isCreativeMode) {
            stack.damageItem(1, player);
          }
        }
      }

    }
  }

  private NBTTagCompound getTagCompound(ItemStack stack) {
    NBTTagCompound tag = stack.getTagCompound();
    if (tag == null) {
      tag = new NBTTagCompound();
      stack.setTagCompound(tag);
    }
    return tag;
  }

  public void clearActive(EntityPlayer player, String type) {
    // This is only called when we're clearing the effect
    if (type.equals(NIGHT_VISION)) {
      player.removePotionEffect(MobEffects.NIGHT_VISION);
    } else {
      player.removePotionEffect(NightVisionGoggles.MOB_VISION);
    }
  }

  public boolean getActive(ItemStack stack, String type) {
    NBTTagCompound tag = getTagCompound(stack);

    if (tag.hasKey(type)) {
      return tag.getBoolean(type);
    }

    return false;
  }

  public void setActive(ItemStack stack, String type, boolean active) {
    NBTTagCompound tag = getTagCompound(stack);

    tag.setBoolean(type, active);
  }

  public void toggleActive(ItemStack stack, String type) {
    NBTTagCompound tag = getTagCompound(stack);

    if (tag.hasKey(type)) {
      boolean current = tag.getBoolean(type);
      tag.setBoolean(type, !current);
    } else {
      tag.setBoolean(type, true);
    }
  }

  @Optional.Method(modid = "baubles")
  @SideOnly(Side.CLIENT)
  @Override
  public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, IRenderBauble.RenderType type, float partialTicks) {
    if (type != IRenderBauble.RenderType.HEAD) return;

    Minecraft.getMinecraft().renderEngine.bindTexture(texture);
    GlStateManager.pushMatrix();
    GlStateManager.enableBlend();

    if (player.isSneaking()) {
      GlStateManager.translate(0.25 * MathHelper.sin(player.rotationPitch * (float) Math.PI / 180), 0.25 * MathHelper.cos(player.rotationPitch * (float) Math.PI / 180), 0.0f);
    }

    float s = 1f / 16f;
    GlStateManager.scale(s, s, s);
    GlStateManager.rotate(-90f, 0f, 1f, 0f);
    ModelGoggles.model_goggles_bauble.bipedHead.render(1.0f);

    GlStateManager.popMatrix();
  }

  @Nullable
  @Override
  @SideOnly(Side.CLIENT)
  public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, EntityEquipmentSlot slot, ModelBiped _default) {
    if (stack.isEmpty()) return null;

    if (slot != EntityEquipmentSlot.HEAD) return null;

    if (stack.getItem() != Items.goggles) return null;

    if (!(entityLiving instanceof EntityPlayer)) return null;

    return ModelGoggles.model_goggles;
  }

  @Nullable
  @Override
  public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
    return NightVisionGoggles.MODID + ":textures/models/armor/goggles_layer_1.png";
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    boolean nv = getActive(stack, NIGHT_VISION);
    boolean mv = getActive(stack, MOB_VISION);

    tooltip.add("");
    if (Keybinds.nightVisionKey != null) {
      tooltip.add(I18n.format("nvg.text.tooltip"));
      tooltip.add("");
      tooltip.add(I18n.format("nvg.text.tooltip2", Keybinds.nightVisionKey.getDisplayName(), Keybinds.mobVisionKey.getDisplayName(), Keybinds.toggleVisionKey.getDisplayName()));
    }

    if (nv) {
      tooltip.add("");
      tooltip.add(TextFormatting.BLUE + "" + TextFormatting.BOLD + I18n.format("nvg.text.night_vision"));
    }
    if (mv) {
      if (!nv) {
        tooltip.add("");
      }
      tooltip.add(TextFormatting.LIGHT_PURPLE + "" + TextFormatting.BOLD + I18n.format("nvg.text.mob_vision"));
    }

    super.addInformation(stack, worldIn, tooltip, flagIn);
  }
}
