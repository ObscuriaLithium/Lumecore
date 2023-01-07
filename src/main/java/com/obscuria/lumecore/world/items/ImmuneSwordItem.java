package com.obscuria.lumecore.world.items;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class ImmuneSwordItem extends SwordItem implements IImmuneToMansion {
    public ImmuneSwordItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }
}
