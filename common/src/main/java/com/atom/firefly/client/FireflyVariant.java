package com.atom.firefly.client;

import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public enum FireflyVariant {
    FOREST("forest", 0xBAF533),
    SWAMP("swamp", 0xFFB733),
    LUSH_CAVES("lush_caves", 0x33F5D5),
    CHERRY("cherry", 0xFF85C8);

    private final String name;
    private final int colorRGB;

    FireflyVariant(String name, int colorRGB) {
        this.name = name;
        this.colorRGB = colorRGB;
    }

    public String getName() {
        return this.name;
    }

    public int getColorRGB() {
        return this.colorRGB;
    }

    public static FireflyVariant fromBiome(Holder<Biome> biome) {
        if (biome.is(Biomes.CHERRY_GROVE)) {
            return CHERRY;
        }
        if (biome.is(Biomes.LUSH_CAVES) || biome.is(BiomeTags.IS_JUNGLE)) {
            return LUSH_CAVES;
        }
        if (biome.is(Biomes.SWAMP) || biome.is(Biomes.MANGROVE_SWAMP)) {
            return SWAMP;
        }
        return FOREST;
    }
}
