package net.lebaguette.actinium.item.custom;

import net.lebaguette.actinium.item.ModItems;
import net.lebaguette.actinium.radiation.RadiationHandler;
import net.lebaguette.actinium.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GeigerCounterItem extends Item {
    public GeigerCounterItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (!pLevel.isClientSide() && pEntity instanceof Player player) {
            boolean isOffHand = player.getOffhandItem().is(ModItems.GEIGER_COUNTER.get());

            if (pIsSelected || isOffHand) {
                float currentRadiation = RadiationHandler.calculateOutsideRadiation(player);

                if (pLevel.getGameTime() % 20L == 0L) {
                    player.displayClientMessage(Component.translatable("message.actinium.geigerCounter", currentRadiation), true);
                }

                int interval = calculateGeigerInterval(currentRadiation);
                float pitch = calculateGeigerPitch(currentRadiation);

                if (pLevel.getGameTime() % interval == 0) {
                    pLevel.playSound(
                            null,
                            player.blockPosition(),
                            ModSounds.GEIGER_COUNTER_CLICK.get(),
                            SoundSource.PLAYERS,
                            1.0f,
                            pitch
                    );
                }
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.actinium.geigerCounter.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    private int calculateGeigerInterval (float radiation) {
        int interval = Integer.MAX_VALUE;
        if (radiation > 0) {
            if (radiation >= 400) {
                interval = 1;
            } else if (radiation >= 200) {
                interval = 3;
            } else if (radiation >= 100) {
                interval = 6;
            } else if (radiation >= 50) {
                interval = 10;
            } else if (radiation >= 10) {
                interval = 13;
            } else if (radiation >= 5) {
                interval = 15;
            } else {
            interval = 20;
            }
        }
        return interval;
    }

    private float calculateGeigerPitch (float radiation) {
        float pitch = 0;
        if (radiation > 0) {
            if (radiation >= 400) {
                pitch = 1.15f;
            } else if (radiation >= 200) {
                pitch = 1.1f;
            } else if (radiation >= 100) {
                pitch = 1.05f;
            } else if (radiation >= 50) {
                pitch = 1.0f;
            } else if (radiation >= 10) {
                pitch = 0.95f;
            } else if (radiation >= 5) {
                pitch = 0.9f;
            } else {
                pitch = 0.85f;
            }
        }
        return pitch;
    }
}
