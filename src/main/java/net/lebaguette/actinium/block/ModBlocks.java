package net.lebaguette.actinium.block;

import net.lebaguette.actinium.Actinium;
import net.lebaguette.actinium.block.custom.PumpjackBlock;
import net.lebaguette.actinium.block.custom.RadioaktiveBlock;
import net.lebaguette.actinium.fluid.ModFluids;
import net.lebaguette.actinium.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Actinium.MOD_ID);
    //Normal Blocks
    public static final RegistryObject<Block> STEEL_BLOCK = registerBlock("steel_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));

    //Radioaktiv Blocks
    public static final RegistryObject<Block> URANIUM_ORE = registerBlock("uranium_ore",
            () -> new RadioaktiveBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops(),5));

    //Liquid Blocks
    public static final RegistryObject<LiquidBlock> OIL_BLOCK = BLOCKS.register("oil",
            () -> new LiquidBlock(ModFluids.OIL.get(), BlockBehaviour.Properties.copy(Blocks.WATER)));

    //BlockEntities
    public static final RegistryObject<Block> PUMPJACK =  registerBlock("pumpjack",
            () -> new PumpjackBlock(BlockBehaviour.Properties.copy(Blocks.ANVIL)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
