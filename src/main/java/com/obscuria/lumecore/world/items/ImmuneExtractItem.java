package com.obscuria.lumecore.world.items;

import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.LumecoreUtils;
import com.obscuria.obscureapi.utils.TextHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ImmuneExtractItem extends Item {

    public ImmuneExtractItem() {
        super(new Properties().tab(LumecoreMod.TAB).rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        list.addAll(TextHelper.build(new ArrayList<>(), "7", "7", TextHelper.format(TextHelper.translation("item.lumecore.immune_extract.desc"))));
        super.appendHoverText(stack, level, list, flag);
    }

    @Override
    public boolean overrideStackedOnOther(@NotNull ItemStack stack, @NotNull Slot slot, @NotNull ClickAction action, @NotNull Player player) {
        if (action == ClickAction.SECONDARY && slot.allowModification(player) && slot.getItem().getCount() == 1 && !LumecoreUtils.isImmune(slot.getItem())) {
            LumecoreUtils.giveImmunity(List.of(slot.getItem()).iterator());
            stack.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }
}
