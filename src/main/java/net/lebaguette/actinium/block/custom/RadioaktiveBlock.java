package net.lebaguette.actinium.block.custom;

import net.lebaguette.actinium.radiation.RadiationInterface;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RadioaktiveBlock extends Block implements RadiationInterface {
    private int radiation = 0;

    public RadioaktiveBlock(Properties properties, int radiation) {
        super(properties);
        this.radiation = radiation;
    }

    @Override
    public int getRadiation() {
        return radiation;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.actinium.radioaktiveBlock.tooltip", radiation));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
