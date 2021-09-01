package me.maxish0t.mod.common.item;

import me.maxish0t.mod.common.handlers.ServerTickHandler;
import me.maxish0t.mod.common.init.ModItemGroups;
import me.maxish0t.mod.server.packets.mining.MiningAbilitiesPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MPickaxeItem extends PickaxeItem {
    private int blastMiningCoolDown;
    private boolean isBlastMiningOnCoolDown;

    public MPickaxeItem(Tier tier, int p_42962_, float p_42963_, Properties properties) {
        super(tier, p_42962_, p_42963_, properties);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int itemSlot, boolean isSelected) {
        if (entity != null && entity instanceof Player) {
            Player player = (Player) entity;
            isBlastMiningOnCoolDown = ServerTickHandler.playerBlastMiningCoolDown.containsKey(player.getUUID());

            if (isBlastMiningOnCoolDown) {
                blastMiningCoolDown = ServerTickHandler.playerBlastMiningCoolDown.get(player.getUUID());
            }
        }
    }

    @Override
    public Collection<CreativeModeTab> getCreativeTabs() {
        return Collections.singletonList(ModItemGroups.MRE_MOD_ITEMS);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(new TextComponent(""));

        String unlocked = ChatFormatting.GRAY + " - " + ChatFormatting.GREEN + "&lUNLOCKED";
        String locked = ChatFormatting.GRAY + " - " + ChatFormatting.DARK_RED + "&lLOCKED";

        unlocked = unlocked.replace("&l", ChatFormatting.BOLD.toString());
        locked = locked.replace("&l", ChatFormatting.BOLD.toString());

        if (MiningAbilitiesPacket.unlockedSuperBreaker) {
            list.add(new TextComponent(ChatFormatting.GRAY + "- " + ChatFormatting.RED + "Super Breaker" + unlocked));
        } else {
            list.add(new TextComponent(ChatFormatting.GRAY + "- " + ChatFormatting.RED + "Super Breaker" + locked));
        }

        if (MiningAbilitiesPacket.unlockedDoubleOres) {
            list.add(new TextComponent(ChatFormatting.GRAY + "- " + ChatFormatting.RED + "Double Ores" + unlocked));
        } else {
            list.add(new TextComponent(ChatFormatting.GRAY + "- " + ChatFormatting.RED + "Double Ores" + locked));
        }

        if (MiningAbilitiesPacket.unlockedBlastMining) {
            if (isBlastMiningOnCoolDown) {
                list.add(new TextComponent(ChatFormatting.GRAY + "- " + ChatFormatting.RED + "Blast Mining" + unlocked + ChatFormatting.YELLOW + ChatFormatting.GRAY + " - " + "Cooldown: " + ChatFormatting.WHITE + blastMiningCoolDown));
            } else {
                list.add(new TextComponent(ChatFormatting.GRAY + "- " + ChatFormatting.RED + "Blast Mining" + unlocked));
            }
        } else {
            list.add(new TextComponent(ChatFormatting.GRAY + "- " + ChatFormatting.RED + "Blast Mining" + locked));
        }
    }
}
