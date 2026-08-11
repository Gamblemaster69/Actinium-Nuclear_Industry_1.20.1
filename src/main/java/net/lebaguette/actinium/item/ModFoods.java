package net.lebaguette.actinium.item;

import net.lebaguette.actinium.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties URANIUM_FUEL_ROD = new FoodProperties.Builder()
            .nutrition(1)
            .saturationMod(0.0f)
            .effect(() -> new MobEffectInstance(ModEffects.IRRADIATED_EFFECT.get(), 2000, 2), 1.0f)
            .alwaysEat()
            .build();
}
