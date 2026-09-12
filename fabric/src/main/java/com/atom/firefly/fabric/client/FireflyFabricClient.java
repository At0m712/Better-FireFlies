package com.atom.firefly.fabric.client;

import com.atom.firefly.client.FireflyModel;
import com.atom.firefly.client.FireflyRenderer;
import com.atom.firefly.client.FireflySpawner;
import com.atom.firefly.fabric.FireflyFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class FireflyFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // 1. Enregistrement des modèles et du rendu
        EntityModelLayerRegistry.registerModelLayer(FireflyModel.LAYER_LOCATION, FireflyModel::createBodyLayer);
        EntityRendererRegistry.register(FireflyFabric.FIREFLY, FireflyRenderer::new);

        // 2. Enregistrement du spawner de lucioles
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.level != null && !client.isPaused()) {
                FireflySpawner.trySpawn(client, FireflyFabric.FIREFLY);
            }
        });

        // 3. Enregistrement des commandes (Lumière dynamique ET Particules)
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            FireflyCommand.register(dispatcher);
        });

        // 4. Couche de rendu translucide/cutout pour le bocal en verre
        net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap.INSTANCE.putBlock(
                FireflyFabric.FIREFLY_JAR_BLOCK,
                net.minecraft.client.renderer.RenderType.cutout()
        );
    }
}