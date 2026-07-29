package com.atom.firefly.fabric.client;

import com.atom.firefly.client.FireflyEntity;
import com.atom.firefly.client.FireflyModel;
import com.atom.firefly.client.FireflyRenderer;
import com.atom.firefly.client.FireflySpawner;
import com.atom.firefly.fabric.FireflyFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.network.chat.Component;

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

        // 3. Enregistrement de la commande /fireflylight
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("fireflylight")
                    .executes(context -> {
                        // Bascule l'état de la lumière
                        FireflyEntity.enableDynamicLight = !FireflyEntity.enableDynamicLight;
                        boolean isEnabled = FireflyEntity.enableDynamicLight;

                        // Envoie un message dans le chat
                        String status = isEnabled ? "§aACTIVÉE" : "§cDÉSACTIVÉE";
                        context.getSource().sendFeedback(
                                Component.literal("§e[FireFly] §fLumière dynamique : " + status)
                        );

                        return 1;
                    })
            );
        });
    }
}