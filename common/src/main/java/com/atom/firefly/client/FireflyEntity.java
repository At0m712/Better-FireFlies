package com.atom.firefly.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FireflyEntity extends Entity {

    public static boolean enableDynamicLight = true;
    public static int globalFireflyCount = 0;

    private int age = 0;
    private final int lifetime;
    private BlockPos lastLightPos = null;
    private BlockPos homePos = null;

    private final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

    private double vx, vy, vz;
    private double targetX, targetY, targetZ;

    public FireflyEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.lifetime = 600 + level.random.nextInt(600);
        globalFireflyCount++;
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        globalFireflyCount--;
        this.removeLight();
    }

    private void pickNewTarget() {
        if (this.homePos == null) return;

        for (int i = 0; i < 5; i++) {
            double proposedX = this.homePos.getX() + (this.random.nextDouble() - 0.5) * 8.0;
            double proposedZ = this.homePos.getZ() + (this.random.nextDouble() - 0.5) * 8.0;

            this.mutablePos.set(proposedX, this.getY() + 2.0, proposedZ);
            boolean foundGround = false;

            for (int j = 0; j < 8; j++) {
                if (!this.level().getBlockState(this.mutablePos).isAir()) {
                    foundGround = true;
                    break;
                }
                this.mutablePos.move(0, -1, 0);
            }

            if (foundGround) {
                double groundY = this.mutablePos.getY() + 1.0;
                double proposedY = groundY + 0.5 + (this.random.nextDouble() * 3.5);

                this.mutablePos.set(proposedX, proposedY, proposedZ);
                BlockState targetState = this.level().getBlockState(this.mutablePos);

                if (targetState.isAir() && targetState.getFluidState().isEmpty()) {
                    this.targetX = proposedX;
                    this.targetY = proposedY;
                    this.targetZ = proposedZ;
                    return;
                }
            }
        }

        this.targetX = this.homePos.getX();
        this.targetY = this.getY() - 1.5;
        this.targetZ = this.homePos.getZ();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.homePos == null) {
            this.homePos = this.blockPosition();
            this.pickNewTarget();
        }

        this.age++;
        this.yRotO = this.getYRot();

        if (this.age > this.lifetime) {
            this.discard();
            return;
        }

        if (this.age % 40 == 0) {
            if (Minecraft.getInstance().player != null && this.distanceToSqr(Minecraft.getInstance().player) > 64 * 64) {
                this.discard();
                return;
            }
        }

        if (this.age % 4 == 0) {
            if (this.isInWaterOrBubble()) {
                this.vy += 0.05;
            } else {
                this.mutablePos.set(this.getX(), this.getY() - 1.0, this.getZ());

                if (!this.level().getBlockState(this.mutablePos).isAir()) {
                    this.vy += 0.03;
                } else {
                    boolean isTooHigh = true;
                    for (int i = 0; i < 3; i++) {
                        this.mutablePos.move(0, -1, 0);
                        if (!this.level().getBlockState(this.mutablePos).isAir()) {
                            isTooHigh = false;
                            break;
                        }
                    }

                    if (isTooHigh) {
                        this.vy -= 0.03;
                        if (this.targetY > this.getY()) {
                            this.targetY = this.getY() - 1.0;
                        }
                    }
                }
            }
        }

        double dx = this.targetX - this.getX();
        double dy = this.targetY - this.getY();
        double dz = this.targetZ - this.getZ();

        double distSqr = dx * dx + dy * dy + dz * dz;

        if (distSqr < 1.0 || this.age % 80 == 0) {
            this.pickNewTarget();
        } else if (distSqr > 0.001) {
            double distance = Math.sqrt(distSqr);
            double speed = 0.04;
            this.vx += (dx / distance * speed - this.vx) * 0.1;
            this.vy += (dy / distance * speed - this.vy) * 0.1;
            this.vz += (dz / distance * speed - this.vz) * 0.1;
        }

        double hover = Math.sin(this.age * 0.15) * 0.015;
        this.setPos(this.getX() + this.vx, this.getY() + this.vy + hover, this.getZ() + this.vz);

        float targetYaw = (float) (Mth.atan2(this.vz, this.vx) * (180F / Math.PI)) + 90.0F;
        float smoothYaw = Mth.approachDegrees(this.getYRot(), targetYaw, 10.0F);
        this.setYRot(smoothYaw);

        if (enableDynamicLight) {
            if (this.level() != null && this.level().isClientSide && this.age % 20 == 0) {
                int currentX = Mth.floor(this.getX());
                int currentY = Mth.floor(this.getY());
                int currentZ = Mth.floor(this.getZ());

                if (this.lastLightPos == null || this.lastLightPos.getX() != currentX || this.lastLightPos.getY() != currentY || this.lastLightPos.getZ() != currentZ) {
                    this.removeLight();

                    BlockPos newPos = new BlockPos(currentX, currentY, currentZ);
                    BlockState currentState = this.level().getBlockState(newPos);

                    if (currentState.isAir()) {
                        BlockState lightState = Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 9);
                        this.level().setBlock(newPos, lightState, 18);
                        this.lastLightPos = newPos;
                    }
                }
            }
        } else {
            this.removeLight();
        }
    }

    private void removeLight() {
        if (this.level() != null && this.lastLightPos != null) {
            BlockState state = this.level().getBlockState(this.lastLightPos);
            if (state.is(Blocks.LIGHT)) {
                this.level().setBlock(this.lastLightPos, Blocks.AIR.defaultBlockState(), 18);
            }
            this.lastLightPos = null;
        }
    }

    @Override protected void defineSynchedData(SynchedEntityData.Builder builder) {}
    @Override protected void readAdditionalSaveData(CompoundTag tag) {}
    @Override protected void addAdditionalSaveData(CompoundTag tag) {}
}