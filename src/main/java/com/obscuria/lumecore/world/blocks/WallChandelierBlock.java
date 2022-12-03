package com.obscuria.lumecore.world.blocks;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class WallChandelierBlock extends ChandelierBlock {
    public WallChandelierBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1f, 10f)
                .requiresCorrectToolForDrops().noOcclusion().randomTicks().isRedstoneConductor((bs, br, bp) -> false)
                .lightLevel(s -> s.getValue(LIT) ? 12 : 0));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            default -> box(1.5, 1, 0, 14.5, 14.5, 7.5);
            case NORTH -> box(1.5, 1, 8.5, 14.5, 14.5, 16);
            case EAST -> box(0, 1, 1.5, 7.5, 14.5, 14.5);
            case WEST -> box(8.5, 1, 1.5, 16, 14.5, 14.5);
        };
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT))
            switch (state.getValue(FACING)) {
                case NORTH -> {
                    if (random.nextInt(1, 4) == 1) {
                        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.22, pos.getY() + 1.05, pos.getZ() + 0.65, 0, 0, 0);
                        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.78, pos.getY() + 1.05, pos.getZ() + 0.65, 0, 0, 0);
                    }
                    level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.22, pos.getY() + 1.05, pos.getZ() + 0.65, 0, 0, 0);
                    level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.78, pos.getY() + 1.05, pos.getZ() + 0.65, 0, 0, 0);
                }
                case EAST -> {
                    if (random.nextInt(1, 4) == 1) {
                        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.35, pos.getY() + 1.05, pos.getZ() + 0.22, 0, 0, 0);
                        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.35, pos.getY() + 1.05, pos.getZ() + 0.78, 0, 0, 0);
                    }
                    level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.35, pos.getY() + 1.05, pos.getZ() + 0.22, 0, 0, 0);
                    level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.35, pos.getY() + 1.05, pos.getZ() + 0.78, 0, 0, 0);
                }
                case WEST -> {
                    if (random.nextInt(1, 4) == 1) {
                        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.65, pos.getY() + 1.05, pos.getZ() + 0.22, 0, 0, 0);
                        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.65, pos.getY() + 1.05, pos.getZ() + 0.78, 0, 0, 0);
                    }
                    level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.65, pos.getY() + 1.05, pos.getZ() + 0.22, 0, 0, 0);
                    level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.65, pos.getY() + 1.05, pos.getZ() + 0.78, 0, 0, 0);
                }
                default -> {
                    if (random.nextInt(1, 4) == 1) {
                        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.22, pos.getY() + 1.05, pos.getZ() + 0.35, 0, 0, 0);
                        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.78, pos.getY() + 1.05, pos.getZ() + 0.35, 0, 0, 0);
                    }
                    level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.22, pos.getY() + 1.05, pos.getZ() + 0.35, 0, 0, 0);
                    level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.78, pos.getY() + 1.05, pos.getZ() + 0.35, 0, 0, 0);
                }
            }
    }
}

