package me.maxish0t.mod.server.packets.mining;

import me.maxish0t.mod.common.entity.projectile.ThrownTnt;
import me.maxish0t.mod.common.handlers.ServerTickHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class BlastMiningPacket {

    public BlastMiningPacket() { }

    public static void encode(final BlastMiningPacket msg, final FriendlyByteBuf packetBuffer) {
    }

    public static BlastMiningPacket decode(final FriendlyByteBuf packetBuffer) {
        return new BlastMiningPacket();
    }

    public static void handle(final BlastMiningPacket msg, final Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            final Player player = context.getSender();

            if (player != null && player instanceof ServerPlayer) {
                ServerPlayer serverPlayer = (ServerPlayer) player;

                Item getItemMain = serverPlayer.getMainHandItem().getItem();
                Item getItemOff = serverPlayer.getMainHandItem().getItem();

                if (ServerTickHandler.playerBlastMiningCoolDown.containsKey(serverPlayer.getUUID()))
                    return;

                if (serverPlayer.level instanceof ServerLevel) {
                    ServerLevel serverLevel = (ServerLevel) serverPlayer.level;

                    if (getItemMain instanceof PickaxeItem || getItemOff instanceof PickaxeItem) {
                        ThrownTnt thrownTnt = new ThrownTnt(serverLevel, serverPlayer);
                        thrownTnt.setItem(new ItemStack(Items.TNT));
                        thrownTnt.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), -20.0F, 0.7F, 1.0F);
                        serverLevel.addFreshEntity(thrownTnt);

                        //coolDownLeft = ServerTickHandler.playerBlastMiningCoolDown.get(serverPlayer.getUUID());
                        ServerTickHandler.playerBlastMiningCoolDown.put(serverPlayer.getUUID(), 100);
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
