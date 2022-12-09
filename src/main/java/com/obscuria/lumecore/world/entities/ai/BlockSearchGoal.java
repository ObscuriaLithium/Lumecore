package com.obscuria.lumecore.world.entities.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BlockSearchGoal extends Goal {

    protected BlockPos TARGET;
    protected final PathfinderMob MOB;
    protected final List<Class<?>> BLOCKS;

    protected BlockSearchGoal(PathfinderMob mob, List<Class<?>> blocks) {
        this.MOB = mob;
        this.BLOCKS = blocks;
    }

    @Override
    public boolean canUse() {
        return false;
    }

    protected @Nullable BlockPos search(BlockPos pos, int radius, int height) {
        if (TARGET != null && TARGET.getX() >= pos.getX() - radius && TARGET.getX() <= pos.getX() + radius &&
                TARGET.getZ() >= pos.getZ() - radius && TARGET.getZ() <= pos.getZ() + radius &&
                TARGET.getY() >= pos.getY() - 1 && TARGET.getY() <= pos.getY() + height) {
            final BlockState state = this.MOB.getLevel().getBlockState(TARGET);
            if (isFit(state, TARGET) && inSight(this.MOB.getEyePosition(), TARGET, radius * 2))
                return TARGET;
        }
        TARGET = null;
        int x = pos.getX() - radius;
        int y = pos.getY();
        int z = pos.getZ() - radius;
        for (int ix = 0; ix <= radius * 2; ix++)
            for (int iy = 0; iy <= height; iy++)
                for (int iz = 0; iz <= radius * 2; iz++) {
                    final BlockPos blockPos = new BlockPos(x + ix, y + iy, z + iz);
                    final BlockState state = this.MOB.getLevel().getBlockState(blockPos);
                    if (isFit(state, pos) && inSight(this.MOB.getEyePosition(), blockPos, radius * 2)) {
                        TARGET = blockPos;
                        return TARGET;
                    }
                }
        return null;
    }

    protected boolean isFit(BlockState state, BlockPos pos) {
        final Class<?> blockClass = state.getBlock().getClass();
        for (Class<?> entry : this.BLOCKS) if (blockClass == entry || entry.isAssignableFrom(blockClass)) return true;
        return false;
    }

    protected boolean inSight(Vec3 eyes, BlockPos target, int steps) {
        final Vec3 targetVec = new Vec3(target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5);
        final Vec3 path = eyes.vectorTo(targetVec);
        for (int i = 0; i <= steps; i++) {
            final float scale = i / 1F / steps;
            final Vec3 vec = eyes.add(path.scale(scale));
            final BlockPos pos = new BlockPos(vec);
            if (pos.equals(target)) continue;
            System.out.println(pos);
            if (!this.MOB.getLevel().isEmptyBlock(pos)) return false;
        }
        return true;
    }
}
