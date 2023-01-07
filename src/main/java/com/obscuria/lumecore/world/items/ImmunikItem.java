package com.obscuria.lumecore.world.items;

import com.obscuria.lumecore.registry.LumecoreMobEffects;
import com.obscuria.obscureapi.utils.TextHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ImmunikItem extends Item {

    public ImmunikItem(Item.Properties properties) {
        super(properties);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        if (!level.isClientSide) {
            entity.addEffect(new MobEffectInstance(LumecoreMobEffects.IMMUNITY.get(), 600, 0, true, false, true));
            if (entity.hasEffect(LumecoreMobEffects.ASH_FEVER.get())) {
                entity.removeEffect(LumecoreMobEffects.ASH_FEVER.get());
                entity.removeEffect(MobEffects.DARKNESS);
                entity.removeEffect(MobEffects.WITHER);
                entity.removeEffect(MobEffects.HUNGER);
            }
            if (entity.getRandom().nextInt(10) == 1)
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, true, false, true));
        }
        stack.shrink(1);
        return stack;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        list.addAll(TextHelper.build(new ArrayList<>(), "7", "7", TextHelper.translation("item.lumecore.immunik.description")));
        super.appendHoverText(stack, level, list, flag);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 32;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }
}
