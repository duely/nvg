package com.noobanidus.nvg.client;

import com.noobanidus.nvg.NightVisionGoggles;
import com.noobanidus.nvg.network.PacketHandler;
import com.noobanidus.nvg.network.PacketToggles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = NightVisionGoggles.MODID)
public class Keybinds {
    public static final String NVG_GROUP = "nvg.gui.keygroup";
    public static final String NVG_BINDS = "nvg.gui.keybinds";
    public static KeyBinding nightVisionKey = null;
    public static KeyBinding mobVisionKey = null;
    public static KeyBinding toggleVisionKey = null;


    public static void initKeybinds() {
        KeyBinding kb = new KeyBinding(NVG_BINDS + ".night_vision", 0, NVG_GROUP);
        ClientRegistry.registerKeyBinding(kb);
        nightVisionKey = kb;
        kb = new KeyBinding(NVG_BINDS + ".mob_vision", 0, NVG_GROUP);
        ClientRegistry.registerKeyBinding(kb);
        mobVisionKey = kb;
        kb = new KeyBinding(NVG_BINDS + ".both", 0, NVG_GROUP);
        ClientRegistry.registerKeyBinding(kb);
        toggleVisionKey = kb;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onKeyInputManifest(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        SoundEvent click = SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;

        boolean sound = false;

        if (nightVisionKey.isKeyDown()) {
            IMessage packet = new PacketToggles.ToggleNightVision();
            PacketHandler.sendToServer(packet);
            sound = true;
        }
        if (mobVisionKey.isKeyDown()) {
            IMessage packet = new PacketToggles.ToggleMobVision();
            PacketHandler.sendToServer(packet);
            sound = true;
        }
        if (toggleVisionKey.isKeyDown()) {
            IMessage packet = new PacketToggles.ToggleBoth();
            PacketHandler.sendToServer(packet);
            sound = true;
        }

        if (sound) {
            mc.player.playSound(click, 1f, 1f);
        }
    }
}
