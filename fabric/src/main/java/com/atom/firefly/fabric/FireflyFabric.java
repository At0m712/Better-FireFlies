package com.atom.firefly.fabric;

import com.atom.firefly.Constants;
import com.atom.firefly.client.FireflyEntity;
import com.atom.firefly.fabric.client.FireflyCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class FireflyFabric implements ModInitializer {

    // On enregistre l'Entité (Taille très petite : 0.2 x 0.2 blocs)
    public static final EntityType<FireflyEntity> FIREFLY_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "firefly"),
            EntityType.Builder.<FireflyEntity>of(FireflyEntity::new, MobCategory.AMBIENT).sized(0.2F, 0.2F).build("firefly")
    );

    @Override
    public void onInitialize() {
        Constants.LOG.info("FireFly 3D (Fabric) initialisé !");
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> FireflyCommand.register(dispatcher));
    }
}