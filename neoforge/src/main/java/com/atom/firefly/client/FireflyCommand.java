package com.atom.firefly.client;

import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

public class FireflyCommand {

    public static void register(RegisterClientCommandsEvent event) {

        event.getDispatcher().register(Commands.literal("fireflylight")
                .executes(context -> {
                    // toggle light
                    FireflyEntity.enableDynamicLight = !FireflyEntity.enableDynamicLight;
                    boolean isEnabled = FireflyEntity.enableDynamicLight;


                    String status = isEnabled ? "§aACTIVÉE" : "§cDÉSACTIVÉE";
                    context.getSource().sendSystemMessage(
                            Component.literal("§e[FireFly] §fLumière dynamique : " + status)
                    );

                    return 1;
                })
        );
    }
}