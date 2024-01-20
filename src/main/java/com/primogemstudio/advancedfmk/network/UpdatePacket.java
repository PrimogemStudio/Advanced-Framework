package com.primogemstudio.advancedfmk.network;

import com.primogemstudio.advancedfmk.mmd.entity.TestEntity;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import static com.primogemstudio.advancedfmk.AdvancedFramework.MOD_ID;

public class UpdatePacket implements ClientPlayNetworking.PlayChannelHandler {
    private static final Int2ObjectMap<String> map = new Int2ObjectOpenHashMap<>();

    static {
        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof TestEntity te) {
                if (map.containsKey(te.getId())) {
                    te.setModel(map.get(te.getId()));
                    map.remove(te.getId());
                }
            }
        });
    }

    public static void flush() {
        var level = Minecraft.getInstance().level;
        if (level == null) return;
        for (var it : map.int2ObjectEntrySet()) {
            if (level.getEntity(it.getIntKey()) instanceof TestEntity te) {
                te.setModel(it.getValue());
            }
        }
        map.clear();
    }

    public static void send(TestEntity entity, String path) {
        var buf = PacketByteBufs.create();
        buf.writeInt(entity.getId());
        buf.writeUtf(path);
        entity.level().players().forEach(p -> ServerPlayNetworking.send((ServerPlayer) p, new ResourceLocation(MOD_ID, "update"), buf));
    }

    @Override
    public void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        var id = buf.readInt();
        var path = buf.readUtf();
        map.put(id, path);
    }
}
