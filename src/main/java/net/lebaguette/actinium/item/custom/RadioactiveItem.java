package net.lebaguette.actinium.item.custom;

import net.lebaguette.actinium.radiation.RadiationInterface;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RadioactiveItem extends Item implements RadiationInterface {
    private int radiation = 0;

    public RadioactiveItem(Properties pProperties, int radiation) {
        super(pProperties);
        this.radiation = radiation;
    }

    @Override
    public int getRadiation() {
        return radiation;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.actinium.radioaktiveItem.tooltip", radiation));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
