package com.atom.firefly.fabric.client;

import com.atom.firefly.client.FireflyEntity;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;

public class FireflyCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("fireflylight")
                .executes(context -> {
                    // Toggle light
                    FireflyEntity.enableDynamicLight = !FireflyEntity.enableDynamicLight;
                    boolean isEnabled = FireflyEntity.enableDynamicLight;

                    String status = isEnabled ? "§aACTIVÉE" : "§cDÉSACTIVÉE";
                    context.getSource().sendFeedback(
                            Component.literal("§e[FireFly] §fLumière dynamique : " + status)
                    );

                    return 1;
                })
        );
    }
}