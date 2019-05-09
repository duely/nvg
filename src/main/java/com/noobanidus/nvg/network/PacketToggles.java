package com.noobanidus.nvg.network;

import com.noobanidus.nvg.init.Items;
import com.noobanidus.nvg.item.ItemGoggles;
import com.noobanidus.nvg.util.InventoryUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketToggles {
    public static class ToggleNightVision implements IMessage {

        public ToggleNightVision() {
        }

        @Override
        public void fromBytes(ByteBuf buf) {
        }

        @Override
        public void toBytes(ByteBuf buf) {
        }

        public static class Handler extends PacketHandler.ServerHandler<ToggleNightVision> {
            @Override
            void processMessage(ToggleNightVision message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().player;

                ItemStack goggles = InventoryUtil.getGoggles(player);
                if (goggles.isEmpty()) return;

                boolean active = Items.goggles.getActive(goggles, ItemGoggles.MOB_VISION);
                Items.goggles.toggleActive(goggles, ItemGoggles.MOB_VISION);
                if (active) {
                    Items.goggles.clearActive(player, ItemGoggles.MOB_VISION);
                }
            }
        }
    }

    public static class ToggleMobVision implements IMessage {

        public ToggleMobVision() {
        }

        @Override
        public void fromBytes(ByteBuf buf) {
        }

        @Override
        public void toBytes(ByteBuf buf) {
        }

        public static class Handler extends PacketHandler.ServerHandler<ToggleMobVision> {
            @Override
            void processMessage(ToggleMobVision message, MessageContext ctx) {
                EntityPlayerMP player = ctx.getServerHandler().player;

                ItemStack goggles = InventoryUtil.getGoggles(player);
                if (goggles.isEmpty()) return;

                boolean active = Items.goggles.getActive(goggles, ItemGoggles.NIGHT_VISION);
                Items.goggles.toggleActive(goggles, ItemGoggles.NIGHT_VISION);
                if (active) {
                    Items.goggles.clearActive(player, ItemGoggles.NIGHT_VISION);
                }
            }
        }
    }


}
