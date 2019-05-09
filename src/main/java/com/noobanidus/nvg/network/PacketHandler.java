package com.noobanidus.nvg.network;

import com.noobanidus.nvg.NightVisionGoggles;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@SuppressWarnings("unused")
public class PacketHandler {
    private static SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(NightVisionGoggles.MODID);

    public static void initPackets() {
        instance.registerMessage(PacketToggles.ToggleNightVision.Handler.class, PacketToggles.ToggleNightVision.class, 0, Side.SERVER);
        instance.registerMessage(PacketToggles.ToggleMobVision.Handler.class, PacketToggles.ToggleMobVision.class, 1, Side.SERVER);
    }

    public static void sendToAll(IMessage message) {
        instance.sendToAll(message);
    }

    public static void sendTo(IMessage message, EntityPlayerMP player) {
        instance.sendTo(message, player);
    }

    public static void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
        instance.sendToAllAround(message, point);
    }

    public static void sendToAllTracking(IMessage message, NetworkRegistry.TargetPoint point) {
        instance.sendToAllTracking(message, point);
    }

    public static void sendToAllTracking(IMessage message, Entity entity) {
        instance.sendToAllTracking(message, entity);
    }

    public static void sendToDimension(IMessage message, int dimensionId) {
        instance.sendToDimension(message, dimensionId);
    }

    public static void sendToServer(IMessage message) {
        instance.sendToServer(message);
    }

    public abstract static class Handler<T extends IMessage> implements IMessageHandler<T, IMessage> {
        abstract void processMessage(T message, MessageContext ctx);
    }

    public abstract static class ServerHandler<T extends IMessage> extends Handler<T> {

        @Override
        public IMessage onMessage(T message, MessageContext ctx) {
            FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> processMessage(message, ctx));
            return null;
        }
    }

    public abstract static class ClientHandler<T extends IMessage> extends Handler<T> {

        @Override
        public IMessage onMessage(T message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message, ctx));

            return null;
        }

        @Override
        public void processMessage(T message, MessageContext ctx) {
        }
    }
}
