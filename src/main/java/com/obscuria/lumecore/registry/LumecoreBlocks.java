package com.obscuria.lumecore.registry;

import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.world.blocks.WallChandelierBlock;
import com.obscuria.lumecore.world.blocks.WallChandelierLeverBlock;
import com.obscuria.lumecore.world.blocks.WallChandelierMonoBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LumecoreBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, LumecoreMod.MODID);

    public static final RegistryObject<Block> BASEBOARD = REGISTRY.register("baseboard",
            () -> new Block(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> WALL_CHANDELIER = REGISTRY.register("wall_chandelier", WallChandelierBlock::new);
    public static final RegistryObject<Block> WALL_CHANDELIER_MONO = REGISTRY.register("wall_chandelier_mono", WallChandelierMonoBlock::new);
    public static final RegistryObject<Block> WALL_CHANDELIER_LEVER = REGISTRY.register("wall_chandelier_lever", WallChandelierLeverBlock::new);
}
