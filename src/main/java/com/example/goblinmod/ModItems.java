package com.example.goblinmod;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(GoblinMod.MODID);

    public static final DeferredItem<DeferredSpawnEggItem> GOBLIN_SPAWN_EGG =
            ITEMS.register("goblin_spawn_egg",
                    () -> new DeferredSpawnEggItem(ModEntities.GOBLIN, 0x4C8C3A, 0x6B4A2B, new Item.Properties()));
}
