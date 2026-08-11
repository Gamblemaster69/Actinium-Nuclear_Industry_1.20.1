package net.lebaguette.actinium.events;

import net.lebaguette.actinium.Actinium;
import net.lebaguette.actinium.radiation.RadiationHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber (modid = Actinium.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Player player = event.player;

        if (player.level().isClientSide()) {
            return;
        }
        RadiationHandler.applyIrradiatedEffect(player);
    }
}
