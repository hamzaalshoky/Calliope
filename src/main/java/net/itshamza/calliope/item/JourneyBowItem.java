package net.itshamza.calliope.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.List;

public class JourneyBowItem extends BowItem {

    public JourneyBowItem(Properties properties) {
        super(properties);
    }

    public int getUseDuration(ItemStack stack) {
        return 60;
    }

    public static float getPowerForTime(int charge) {
        float f = (float)charge / 60.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int timeLeft) {
        if (!(user instanceof Player player)) return;

        boolean hasInfiniteArrows = player.getAbilities().instabuild;
        ItemStack ammo = player.getProjectile(stack);
        List<ItemStack> list = draw(stack, ammo, player);
        int i = this.getUseDuration(stack, user) - timeLeft;
        i = EventHooks.onArrowLoose(stack, level, player, i, !stack.isEmpty());
        if (i < 0) {
            return;
        }

        float f = getPowerForTime(i);
        if (!((double)f < 0.1)) {
            if (!ammo.isEmpty() || hasInfiniteArrows) {
                ArrowItem arrowItem = (ArrowItem)(ammo.getItem() instanceof ArrowItem ? ammo.getItem() : Items.ARROW);
                AbstractArrow arrow = arrowItem.createArrow(level, ammo, player, stack);

                arrow.setBaseDamage(arrow.getBaseDamage() + f);
                arrow.setOwner(player);
                if(player.getServer() != null){
                    ServerLevel serverlevel = player.getServer().getLevel(level.dimension());
                    this.shoot(serverlevel, player, player.getUsedItemHand(), stack, list, f * 4.0F, 1.0F, f == 1.0F, (LivingEntity)null);
                }

                stack.hurtAndBreak(1, player, stack.getEquipmentSlot());
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }

    }

    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
        projectile.getPersistentData().putBoolean("ShieldBreakerArrow", true);
        projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot() + angle, 0.0F, velocity, inaccuracy);
    }
}