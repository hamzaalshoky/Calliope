package net.itshamza.calliope.event;


import net.itshamza.calliope.Calliope;
import net.itshamza.calliope.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.minecraft.sounds.SoundSource;

@EventBusSubscriber(modid = Calliope.MODID, bus = EventBusSubscriber.Bus.GAME, value =  Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onComputeFovModifierEvent(ComputeFovModifierEvent event) {
        if(event.getPlayer().isUsingItem() && event.getPlayer().getUseItem().getItem() == ModItems.JOURNEY_BOW.get()) {
            float fovModifier = 1f;
            int ticksUsingItem = event.getPlayer().getTicksUsingItem();
            float deltaTicks = (float)ticksUsingItem / 20f;
            if(deltaTicks > 1f) {
                deltaTicks = 1f;
            } else {
                deltaTicks *= deltaTicks;
            }
            fovModifier *= 1f - deltaTicks * 0.15f;
            event.setNewFovModifier(fovModifier);
        }
    }

    @SubscribeEvent
    public static void onArrowHit(LivingDamageEvent.Post event) {
        DamageSource source = event.getSource();
        if (!(source.getDirectEntity() instanceof AbstractArrow arrow)) return;
        if (!(event.getEntity() instanceof Player hitPlayer)) return;

        if (arrow.getPersistentData().getBoolean("ShieldBreakerArrow")) {

            if (hitPlayer.isBlocking()) {
                ItemStack activeItem = hitPlayer.getUseItem();

                    hitPlayer.getCooldowns().addCooldown(activeItem.getItem(), 100);
                    hitPlayer.disableShield();
                    hitPlayer.level().playSound(null, hitPlayer.blockPosition(),
                            SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
    }
}
