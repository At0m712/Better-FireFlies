package com.atom.firefly.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FireflyJarBlock extends LanternBlock {

    protected static final VoxelShape STANDING_SHAPE = Shapes.or(
            box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D),
            box(5.0D, 10.0D, 5.0D, 11.0D, 12.0D, 11.0D)
    );

    protected static final VoxelShape HANGING_SHAPE = Shapes.or(
            box(4.0D, 2.0D, 4.0D, 12.0D, 12.0D, 12.0D),
            box(5.0D, 12.0D, 5.0D, 11.0D, 14.0D, 11.0D),
            box(6.5D, 14.0D, 6.5D, 9.5D, 16.0D, 9.5D)
    );

    public FireflyJarBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? HANGING_SHAPE : STANDING_SHAPE;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(3) == 0) {
            double yOffset = state.getValue(HANGING) ? 0.45D : 0.35D;
            double px = pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.3D;
            double py = pos.getY() + yOffset + (random.nextDouble() - 0.5D) * 0.25D;
            double pz = pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.3D;
            level.addParticle(ParticleTypes.GLOW, px, py, pz, 0.0D, 0.0D, 0.0D);
        }
    }
}
