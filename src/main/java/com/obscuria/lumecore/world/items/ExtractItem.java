package com.obscuria.lumecore.world.items;

import com.mojang.datafixers.util.Pair;
import com.obscuria.lumecore.LumecoreUtils;
import com.obscuria.obscureapi.utils.TextHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ExtractItem extends Item {
    private final boolean LARGE;
    private final List<com.mojang.datafixers.util.Pair<Supplier<MobEffectInstance>, Float>> effects = Lists.newArrayList();

    @SafeVarargs
    public ExtractItem(boolean large, Properties properties, java.util.function.Supplier<MobEffectInstance>... effects) {
        super(properties.rarity(large ? Rarity.EPIC : Rarity.UNCOMMON));
        this.LARGE = large;
        for (java.util.function.Supplier<MobEffectInstance> effect : effects) this.effects.add(Pair.of(effect, 1F));
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        if (!level.isClientSide)
            if (LumecoreUtils.isInMansion(entity)) {
                for (com.mojang.datafixers.util.Pair<Supplier<MobEffectInstance>, Float> effect : this.effects) {
                    final MobEffectInstance parent = effect.getFirst().get();
                    entity.addEffect(new MobEffectInstance(parent.getEffect(), parent.getDuration(), parent.getAmplifier(), true, false, true));
                }
            } else {
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 2));
            }
        stack.shrink(1);
        return stack;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        list.addAll(TextHelper.build(new ArrayList<>(), "7", "7", TextHelper.translation("item.lumecore.extract.description")));
        list.add(TextHelper.component(""));
        for (com.mojang.datafixers.util.Pair<Supplier<MobEffectInstance>, Float> effect : this.effects) {
            final MobEffectInstance parent = effect.getFirst().get();
            final String amplifier = parent.getAmplifier() > 0 ? " " + TextHelper.translation("potion.potency." + parent.getAmplifier()) : "";
            final String duration = parent.getDuration() > 20 ? " (" + MobEffectUtil.formatDuration(parent, 1F) + ")" : "";
            list.add(TextHelper.component((parent.getEffect().getCategory() == MobEffectCategory.HARMFUL ? "§c" : "§9") +
                    TextHelper.translation(parent.getDescriptionId()) + amplifier + duration));
        }
        super.appendHoverText(stack, level, list, flag);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return this.LARGE ? 40 : 24;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return this.LARGE;
    }
}
