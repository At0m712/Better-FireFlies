package com.atom.firefly.fabric;

import com.atom.firefly.CommonClass;
import com.atom.firefly.Constants;
import com.atom.firefly.client.FireflyEntity;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class FireflyFabric implements ModInitializer {

    // 1. Création de la clé d'entité obligatoire pour la 1.21.3+
    public static final ResourceKey<EntityType<?>> FIREFLY_KEY = ResourceKey.create(
            Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "firefly")
    );

    // 2. On renomme bien la variable en "FIREFLY" pour que le Client la trouve !
    public static final EntityType<FireflyEntity> FIREFLY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "firefly"),
            EntityType.Builder.<FireflyEntity>of(FireflyEntity::new, MobCategory.AMBIENT)
                    .sized(0.2F, 0.2F)
                    .build(FIREFLY_KEY)
    );

    @Override
    public void onInitialize() {
        CommonClass.init();
        Constants.LOG.info("FireFly 3D (Fabric) initialized!");
    }
}