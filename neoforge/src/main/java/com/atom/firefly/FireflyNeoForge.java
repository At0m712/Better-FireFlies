package com.atom.firefly;

import com.atom.firefly.client.FireflyCommand;
import com.atom.firefly.client.FireflyEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

@Mod(Constants.MOD_ID)
public class FireflyNeoForge {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Constants.MOD_ID);

    public static final Supplier<EntityType<FireflyEntity>> FIREFLY_ENTITY = ENTITY_TYPES.register("firefly",
            () -> EntityType.Builder.<FireflyEntity>of(FireflyEntity::new, MobCategory.AMBIENT).sized(0.2F, 0.2F).build("firefly")
    );

    public FireflyNeoForge(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
        Constants.LOG.info("FireFly 3D (NeoForge) initialisé !");
        NeoForge.EVENT_BUS.addListener(FireflyCommand::register);
    }
}