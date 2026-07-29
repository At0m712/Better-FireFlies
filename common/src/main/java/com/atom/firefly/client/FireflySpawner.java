package com.atom.firefly.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class FireflySpawner {

    private static int clientEntityIdCounter = -10000;

    public static void trySpawn(Minecraft client, EntityType<FireflyEntity> entityType) {
        if (FireflyEntity.globalFireflyCount >= 35) return;

        ClientLevel level = client.level;
        if (level == null || client.player == null || client.isPaused() || level.random.nextInt(60) != 0) return;

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(
                client.player.getX() + (level.random.nextDouble() - 0.5) * 64,
                client.player.getY() + 15,
                client.player.getZ() + (level.random.nextDouble() - 0.5) * 64
        );

        boolean foundGround = false;
        for (int i = 0; i < 40; i++) {
            if (!level.getBlockState(pos).isAir()) {
                foundGround = true;
                break;
            }
            pos.move(0, -1, 0);
        }

        if (!foundGround) return;
        pos.move(0, 1, 0);

        long timeOfDay = level.getDayTime() % 24000;
        boolean isDarkCave = level.getBrightness(LightLayer.SKY, pos) == 0 && level.getBrightness(LightLayer.BLOCK, pos) < 4;

        if (!(timeOfDay >= 13000 && timeOfDay < 23000) && !isDarkCave) return;

        boolean isAllowed = isDarkCave;
        if (!isAllowed) {
            Holder<Biome> biome = level.getBiome(pos);
            isAllowed = biome.is(Biomes.SWAMP) || biome.is(Biomes.FOREST) || biome.is(Biomes.JUNGLE) || biome.is(Biomes.LUSH_CAVES);
        }

        if (!isAllowed) return;

        int clusterSize = 3 + level.random.nextInt(4);
        int baseX = pos.getX();
        int baseY = pos.getY();
        int baseZ = pos.getZ();

        for (int i = 0; i < clusterSize; i++) {
            if (FireflyEntity.globalFireflyCount >= 35) break;

            double finalX = baseX + (level.random.nextDouble() - 0.5) * 6.0;
            double finalY = baseY + 0.5 + level.random.nextDouble() * 2.0;
            double finalZ = baseZ + (level.random.nextDouble() - 0.5) * 6.0;

            pos.set(finalX, finalY, finalZ);

            if (level.getBlockState(pos).isAir()) {
                FireflyEntity firefly = new FireflyEntity(entityType, level);
                firefly.setPos(finalX, finalY, finalZ);
                firefly.setId(clientEntityIdCounter--);
                level.addEntity(firefly);
            }
        }
    }
}