package net.lebaguette.actinium.radiation;

import net.lebaguette.actinium.effect.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class RadiationHandler {

    public static int calculateInventoryRadiation(Player player) {
        int radiation = 0;

        //inventar prüfen
        for (ItemStack stack : player.getInventory().items) {
            //Radioaktive Items
            if (stack.getItem() instanceof RadiationInterface radiationInterface) {
                radiation += radiationInterface.getRadiation() * stack.getCount();
            }
            //Radioaktive BlockItems
            else if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof RadiationInterface radiationInterface) {
                radiation += radiationInterface.getRadiation() * stack.getCount();
            }
        }
        return radiation;
    }

    //umgebung nach radioaktiven blöcken absuchen
    private static int calculateNearbyBlockRadiation(Player player) {
        int radiation = 0;
        int radius = 5;

        BlockPos playerPos = player.blockPosition();

        for (BlockPos pos : BlockPos.betweenClosed(
                playerPos.offset(-radius,-radius,-radius),
                playerPos.offset(radius,radius,radius))) {

            Block block = player.level()
                    .getBlockState(pos)
                    .getBlock();

            if (block instanceof RadiationInterface radiationInterface) {
                radiation += radiationInterface.getRadiation();
            }
        }
        return radiation;
    }

    public static float calculateRadiation(Player player) {
        float totalradiation = 0f;
        float protection = 0f;

        MobEffectInstance RadiationResistance =  player.getEffect(ModEffects.RadiationResistance.get());

        if (RadiationResistance != null) {
            protection = switch (RadiationResistance.getAmplifier()) {
                case 1 -> 0.25f;
                case 2 -> 0.5f;
                default -> protection;
            };
        }

        totalradiation = (calculateInventoryRadiation(player) + calculateNearbyBlockRadiation(player)) * (1 - protection);

        return totalradiation;
    }

    public static float calculateOutsideRadiation(Player player) {
        float outsideradiation = 0f;
        outsideradiation = calculateInventoryRadiation(player) + calculateNearbyBlockRadiation(player);
        return outsideradiation;
    }

    public static void applyIrradiatedEffect(Player player) {
        float radiation = calculateRadiation(player);

        int amplifier = -1;

        if (radiation >= 300) {
            amplifier = 2;
        }

        else if (radiation >= 100) {
            amplifier = 1;
        }

        else if (radiation >= 20) {
            amplifier = 0;
        }
        else {
            amplifier = -1;
        }

        MobEffectInstance current = player.getEffect(ModEffects.IRRADIATED_EFFECT.get());

        if (amplifier == -1) {
            if (current == null) {
                player.removeEffect(ModEffects.IRRADIATED_EFFECT.get());
            }
            return;
        }

        if (current != null && current.getAmplifier() == amplifier) {
            if (current.getDuration() < 100) {
                current.update(
                        new MobEffectInstance(
                                ModEffects.IRRADIATED_EFFECT.get(),
                                400,
                                amplifier
                        )
                );
            }
        }

        else {
            player.addEffect(
                    new MobEffectInstance(
                            ModEffects.IRRADIATED_EFFECT.get(),
                            400,
                            amplifier
                    )
            );
        }
    }
}