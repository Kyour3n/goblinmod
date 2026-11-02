package com.example.goblinmod;

import com.example.goblinmod.entity.GoblinEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, GoblinMod.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<GoblinEntity>> GOBLIN =
            ENTITY_TYPES.register("goblin", () -> EntityType.Builder
                    .of(GoblinEntity::new, MobCategory.MONSTER)
                    .sized(0.5F, 1.3F)
                    .eyeHeight(1.1F)
                    .clientTrackingRange(8)
                    .build("goblin"));
}
