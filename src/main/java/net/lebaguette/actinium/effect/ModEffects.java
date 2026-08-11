package net.lebaguette.actinium.effect;

import net.lebaguette.actinium.Actinium;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Actinium.MOD_ID);

    public static final RegistryObject<MobEffect> IRRADIATED_EFFECT = MOB_EFFECTS.register("irradiated",
            () -> new IrradiatedEffect(MobEffectCategory.HARMFUL, 0x36ebab));
    public static final RegistryObject<MobEffect> RadiationResistance = MOB_EFFECTS.register("radiation_resistance",
            () -> new RadiationResistanceEffect(MobEffectCategory.BENEFICIAL, 292721));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
