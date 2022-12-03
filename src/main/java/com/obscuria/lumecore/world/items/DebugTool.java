package com.obscuria.lumecore.world.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class DebugTool extends Item {
    public DebugTool() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    }
}
