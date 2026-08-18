package net.lebaguette.actinium.fluid;

import net.lebaguette.actinium.Actinium;
import net.lebaguette.actinium.block.ModBlocks;
import net.lebaguette.actinium.item.ModItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(
                    ForgeRegistries.FLUIDS,
                    Actinium.MOD_ID
            );

    public static final RegistryObject<FlowingFluid> OIL =
            FLUIDS.register("oil", () -> new ForgeFlowingFluid.Source(getOilProperties()));

    public static final RegistryObject<FlowingFluid> FLOWING_OIL =
            FLUIDS.register("flowing_oil", () -> new ForgeFlowingFluid.Flowing(getOilProperties()));

    private static ForgeFlowingFluid.Properties getOilProperties() {
        return new ForgeFlowingFluid.Properties(
                ModFluidTypes.OIL,
                OIL,
                FLOWING_OIL
        ).bucket(ModItems.OIL_BUCKET).block(ModBlocks.OIL_BLOCK).tickRate(30);
    }

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
