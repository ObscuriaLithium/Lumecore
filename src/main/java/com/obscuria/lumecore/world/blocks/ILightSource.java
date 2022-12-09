package com.obscuria.lumecore.world.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface ILightSource {
    boolean isLit(BlockState state);
    void setLit(BlockState state, BlockPos pos, Level world, boolean lit);
}
