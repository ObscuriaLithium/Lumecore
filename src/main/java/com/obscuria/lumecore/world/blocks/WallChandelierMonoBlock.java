package com.obscuria.lumecore.world.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallChandelierMonoBlock extends ChandelierBlock {
    public WallChandelierMonoBlock() {
        super(Properties.of(Material.METAL).sound(SoundType.METAL).strength(1f, 10f)
                .requiresCorrectToolForDrops().noOcclusion().randomTicks().isRedstoneConductor((bs, br, bp) -> false)
                .lightLevel(s -> s.getValue(LIT) ? 10 : 0));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            default -> box(5, 1, 0, 11, 14.5, 10);
            case NORTH -> box(5, 1, 6, 11, 14.5, 16);
            case EAST -> box(0, 1, 5, 10, 14.5, 11);
            case WEST -> box(6, 1, 5, 16, 14.5, 11);
        };
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT))
            switch (state.getValue(FACING)) {
                case NORTH -> {
                    if (random.nextInt(1, 4) == 1)
                        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.05, pos.getZ() + 0.53, 0, 0, 0);
                    level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.05, pos.getZ() + 0.53, 0, 0, 0);
                }
                case EAST -> {
                    if (random.nextInt(1, 4) == 1)
                        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.47, pos.getY() + 1.05, pos.getZ() + 0.5, 0, 0, 0);
                    level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.47, pos.getY() + 1.05, pos.getZ() + 0.5, 0, 0, 0);
                }
                case WEST -> {
                    if (random.nextInt(1, 4) == 1)
                        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.53, pos.getY() + 1.05, pos.getZ() + 0.5, 0, 0, 0);
                    level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.53, pos.getY() + 1.05, pos.getZ() + 0.5, 0, 0, 0);
                }
                default -> {
                    if (random.nextInt(1, 4) == 1)
                        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.05, pos.getZ() + 0.47, 0, 0, 0);
                    level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.05, pos.getZ() + 0.47, 0, 0, 0);
                }
            }
    }
}

