package net.lebaguette.actinium.item;

import net.lebaguette.actinium.Actinium;
import net.lebaguette.actinium.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier STEEL = TierSortingRegistry.registerTier(
            new ForgeTier(2, 550, 7.5F, 2F, 16,
                    ModTags.Blocks.NEEDS_STEEL_TOOL, () -> Ingredient.of(ModItems.STEEL_INGOT.get())),
            new ResourceLocation(Actinium.MOD_ID,"steel"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
}
