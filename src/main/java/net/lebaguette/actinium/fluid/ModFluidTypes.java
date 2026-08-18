package net.lebaguette.actinium.fluid;

import net.lebaguette.actinium.Actinium;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluidTypes {

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(
                    ForgeRegistries.Keys.FLUID_TYPES,
                    Actinium.MOD_ID
            );

    public static final RegistryObject<FluidType> OIL =
            FLUID_TYPES.register("oil", () -> new BaseFluidType(
                    new ResourceLocation(Actinium.MOD_ID, "block/oil_still"),
                    new ResourceLocation(Actinium.MOD_ID, "block/oil_flow"),
                    FluidType.Properties.create()
                            .density(850)
                            .viscosity(100000)
                            .temperature(293)
                            .descriptionId("fluid.actinium.oil")
            ));

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
