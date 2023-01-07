
package com.obscuria.lumecore.world.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import org.jetbrains.annotations.NotNull;

public class AshFeverMobEffect extends MobEffect {
	public AshFeverMobEffect() {
		super(MobEffectCategory.HARMFUL, -6750055);
	}

	@Override
	public @NotNull String getDescriptionId() {
		return "effect.lumecore.ash_fever";
	}
}
