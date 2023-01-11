package com.obscuria.lumecore.registry;

import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.world.items.DebugTool;
import com.obscuria.lumecore.world.items.ExtractItem;
import com.obscuria.lumecore.world.items.ImmuneItem;
import com.obscuria.lumecore.world.items.ImmunikItem;
import com.obscuria.lumecore.world.items.weapon.BladeOfDeathmark;
import com.obscuria.lumecore.world.items.weapon.BladeOfVitality;
import com.obscuria.lumecore.world.items.weapon.TwistedDagger;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LumecoreItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, LumecoreMod.MODID);

    public static final RegistryObject<DebugTool> DEBUG_TOOL = REGISTRY.register("debug_tool", DebugTool::new);

    public static final RegistryObject<Item> BOWL_OF_RICE = REGISTRY.register("bowl_of_rice",
            () -> new ImmuneItem(new Item.Properties().stacksTo(3)
            .tab(LumecoreMod.TAB).food(new FoodProperties.Builder().nutrition(14).saturationMod(1.2F).build())));

    public static final RegistryObject<ImmunikItem> IMMUNIK = REGISTRY.register("immunik",
            () -> new ImmunikItem(new Item.Properties().stacksTo(8).tab(LumecoreMod.TAB)));
    public static final RegistryObject<ExtractItem> BLOOD_EXTRACT = REGISTRY.register("blood_extract",
            () -> new ExtractItem(false, new Item.Properties().stacksTo(3).tab(LumecoreMod.TAB),
            () -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 6000, 1)));
    public static final RegistryObject<ExtractItem> LARGE_BLOOD_EXTRACT = REGISTRY.register("large_blood_extract",
            () -> new ExtractItem(true, new Item.Properties().stacksTo(3).tab(LumecoreMod.TAB),
            () -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 9000, 3)));

    public static final RegistryObject<TwistedDagger> TWISTED_DAGGER = REGISTRY.register("twisted_dagger", TwistedDagger::new);
    public static final RegistryObject<BladeOfDeathmark> BLADE_OF_DEATHMARK = REGISTRY.register("blade_of_deathmark", BladeOfDeathmark::new);
    public static final RegistryObject<BladeOfVitality> BLADE_OF_VITALITY = REGISTRY.register("blade_of_vitality", BladeOfVitality::new);
    public static final RegistryObject<Item> SKELETON_KEY = REGISTRY.register("skeleton_key",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(16).tab(LumecoreMod.TAB)));

    public static final RegistryObject<Item> BASEBOARD = REGISTRY.register("baseboard",
            () -> new BlockItem(LumecoreBlocks.BASEBOARD.get(), new Item.Properties().tab(LumecoreMod.TAB)));
    public static final RegistryObject<Item> WALL_CHANDELIER = REGISTRY.register("wall_chandelier",
            () -> new BlockItem(LumecoreBlocks.WALL_CHANDELIER.get(), new Item.Properties().tab(LumecoreMod.TAB)));
    public static final RegistryObject<Item> WALL_CHANDELIER_MONO = REGISTRY.register("wall_chandelier_mono",
            () -> new BlockItem(LumecoreBlocks.WALL_CHANDELIER_MONO.get(), new Item.Properties().tab(LumecoreMod.TAB)));
    public static final RegistryObject<Item> WALL_CHANDELIER_LEVER = REGISTRY.register("wall_chandelier_lever",
            () -> new BlockItem(LumecoreBlocks.WALL_CHANDELIER_LEVER.get(), new Item.Properties().tab(LumecoreMod.TAB)));
}
