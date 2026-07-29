package com.atom.firefly.fabric;

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

    // NOUVEAUTÉ 1.21.3 : Création d'une clé d'enregistrement officielle (ResourceKey)
    public static final ResourceKey<EntityType<?>> FIREFLY_KEY = ResourceKey.create(
            Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "firefly")
    );

    // On utilise la clé FIREFLY_KEY dans le .build() au lieu du texte "firefly"
    public static final EntityType<FireflyEntity> FIREFLY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            FIREFLY_KEY.location(),
            EntityType.Builder.<FireflyEntity>of(FireflyEntity::new, MobCategory.AMBIENT)
                    .sized(0.2F, 0.2F)
                    .build(FIREFLY_KEY)
    );

    @Override
    public void onInitialize() {
        // Code d'initialisation principal de Fabric
    }
}