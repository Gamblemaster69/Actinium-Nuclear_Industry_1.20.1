package net.lebaguette.actinium.datagen;

import net.lebaguette.actinium.Actinium;
import net.lebaguette.actinium.item.ModItems;
import net.lebaguette.actinium.loot.AddItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGloalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGloalLootModifiersProvider(PackOutput output) {
        super(output, Actinium.MOD_ID);
    }

    @Override
    protected void start() {
        add("steel_ingot_from_iron_golem", new AddItemModifier(new LootItemCondition[] {
        new LootTableIdCondition.Builder(new ResourceLocation("entities/iron_golem")).build()}, ModItems.STEEL_INGOT.get()));
    }
}
