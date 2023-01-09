
package com.obscuria.lumecore.world.effects;

import com.obscuria.lumecore.LumecoreUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public abstract class MansionMobEffect extends MobEffect {
	public MansionMobEffect() {
		super(MobEffectCategory.HARMFUL, -6750055);
		addAttributeModifier(Attributes.MAX_HEALTH, "1D6F2BA2-1286-46AC-B896-C11C5AAE91CA", -0.5D, AttributeModifier.Operation.MULTIPLY_BASE);
	}

	@Override
	public boolean isDurationEffectTick(int tick, int amplifier) {
		return tick % 20 == 0;
	}

	@Override
	public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
		if (entity instanceof Player) LumecoreUtils.decay(entity, 0.01F, entity.getAllSlots().iterator(), SoundEvents.ITEM_BREAK);
	}

	@Override
	public void addAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap map, int amplifier) {
		if (entity instanceof Player) {
			super.addAttributeModifiers(entity, map, amplifier);
			if (entity.getHealth() > entity.getMaxHealth()) entity.setHealth(entity.getMaxHealth());
		}
	}
}
