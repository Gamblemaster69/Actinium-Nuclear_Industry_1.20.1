package net.lebaguette.actinium.block.entity;

import net.lebaguette.actinium.Actinium;
import net.lebaguette.actinium.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Actinium.MOD_ID);

    public static final RegistryObject<BlockEntityType<PumpjackBlockEntity>> PUMPJACK_BE =
            BLOCK_ENTITIES.register("pumpjack_be",
                    () -> BlockEntityType.Builder.of(
                            PumpjackBlockEntity::new,
                            ModBlocks.PUMPJACK.get()
                    ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
