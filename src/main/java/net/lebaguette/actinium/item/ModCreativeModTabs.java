package net.lebaguette.actinium.item;

import net.lebaguette.actinium.Actinium;
import net.lebaguette.actinium.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Actinium.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ACTINIUM_TAB = CREATIVE_MODE_TABS.register("actinium_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.HAZMAT_HELMET.get()))
                    .title(Component.translatable("creativetab.actinium_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        //items
                        output.accept(ModItems.RAW_URANIUM.get());
                        output.accept(ModItems.URANIUM_INGOT.get());
                        output.accept(ModItems.URANIUM_FUEL_ROD.get());
                        output.accept(ModItems.STEEL_INGOT.get());
                        output.accept(ModItems.STEEL_SWORD.get());
                        output.accept(ModItems.STEEL_PICKAXE.get());
                        output.accept(ModItems.STEEL_AXE.get());
                        output.accept(ModItems.STEEL_SHOVEL.get());
                        output.accept(ModItems.STEEL_HOE.get());
                        output.accept(ModItems.STEEL_HELMET.get());
                        output.accept(ModItems.STEEL_CHESTPLATE.get());
                        output.accept(ModItems.STEEL_LEGGINGS.get());
                        output.accept(ModItems.STEEL_BOOTS.get());
                        output.accept(ModItems.HAZMAT_HELMET.get());
                        output.accept(ModItems.HAZMAT_CHESTPLATE.get());
                        output.accept(ModItems.HAZMAT_LEGGINGS.get());
                        output.accept(ModItems.HAZMAT_BOOTS.get());
                        output.accept(ModItems.GEIGER_COUNTER.get());
                        // Blocks
                        output.accept(ModBlocks.STEEL_BLOCK.get());
                        output.accept(ModBlocks.URANIUM_ORE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
