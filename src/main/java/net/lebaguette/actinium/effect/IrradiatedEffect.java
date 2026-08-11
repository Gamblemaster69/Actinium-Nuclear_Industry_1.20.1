package net.lebaguette.actinium.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class IrradiatedEffect extends MobEffect {

    public IrradiatedEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    private void addExhaustion(LivingEntity pLivingEntity, float exhaustionAmount) {
        if (pLivingEntity instanceof Player player ) {
            player.getFoodData().addExhaustion(exhaustionAmount);
        }
    }
    private void doDamage(LivingEntity pLivingEntity,float pAmount) {
        pLivingEntity.hurt(pLivingEntity.level().damageSources().magic(), pAmount);
    }
    private static final UUID WEAKNESS_UUID = UUID.fromString("3c3f2a57-be55-4dad-a094-7b7683a1a7a6");
    private void addWeakness(LivingEntity pLivingEntity, String pname, double pAmount) {
        AttributeInstance attribute = pLivingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attribute != null) {
            attribute.removeModifier(WEAKNESS_UUID);
            attribute.addTransientModifier(
                    new AttributeModifier(
                            WEAKNESS_UUID, // UUID identifies this modifier so Minecraft can find/remove it
                            pname, //Name
                            pAmount, //Amount of Weakness
                            AttributeModifier.Operation.MULTIPLY_TOTAL
                    )
            );
        }
    }
    private static final UUID SLOWNESS_UUID = UUID.fromString("e36857e4-2794-4c33-87a3-6c900dcd5b5b");
    private void addSlowness(LivingEntity pLivingEntity, String pname, double pAmount) {
        AttributeInstance attribute =  pLivingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attribute != null) {
            attribute.removeModifier(SLOWNESS_UUID);
            attribute.addTransientModifier(
                    new AttributeModifier(
                            SLOWNESS_UUID, // UUID identifies this modifier so Minecraft can find/remove it
                            pname, //Name
                            pAmount, //Amount of Slowness
                            AttributeModifier.Operation.MULTIPLY_TOTAL
                    )
            );
        }
    }

    private void removeWeakness(LivingEntity entity) {
        AttributeInstance attribute = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attribute != null) {
            attribute.removeModifier(WEAKNESS_UUID);
        }
    }
    private void removeSlowness(LivingEntity entity) {
        AttributeInstance attribute = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attribute != null) {
            attribute.removeModifier(SLOWNESS_UUID);
        }
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {

        //removeWeakness(pLivingEntity);
        //removeSlowness(pLivingEntity);

        switch (pAmplifier) {
            case 0:
                addExhaustion(pLivingEntity, 0.2f);
                addWeakness(pLivingEntity, "Minor Irradiated Weakness",-0.35);
                break;

            case 1:
                addExhaustion(pLivingEntity, 0.4f);
                addWeakness(pLivingEntity, "Irradiated Weakness",-0.5);
                addSlowness(pLivingEntity, "Irradiated Slowness", -0.25);
                break;

            case 2:
                addExhaustion(pLivingEntity, 1.5f);
                addWeakness(pLivingEntity, "Major Irradiated Weakness",-0.7);
                addSlowness(pLivingEntity, "Major Irradiated Slowness", -0.5);
                doDamage(pLivingEntity, 1.0f);
                break;
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        removeSlowness(pLivingEntity);
        removeWeakness(pLivingEntity);
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 20 == 0;
    }
}