package com.atom.firefly.fabric.client;

import com.atom.firefly.client.FireflyModel;
import com.atom.firefly.client.FireflyRenderer;
import com.atom.firefly.client.FireflySpawner;
import com.atom.firefly.fabric.FireflyFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class FireflyFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // 1. ENREGISTREMENT DE LA COUCHE DU MODÈLE (C'est ce qui manquait !)
        EntityModelLayerRegistry.registerModelLayer(FireflyModel.LAYER_LOCATION, FireflyModel::createBodyLayer);

        // 2. Relie l'Entité au Rendu
        EntityRendererRegistry.register(FireflyFabric.FIREFLY_ENTITY, FireflyRenderer::new);

        // 3. Gestionnaire d'apparition
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            FireflySpawner.trySpawn(client, FireflyFabric.FIREFLY_ENTITY);
        });
    }
}