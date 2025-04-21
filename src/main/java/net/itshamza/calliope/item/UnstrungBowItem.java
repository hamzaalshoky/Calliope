package net.itshamza.calliope.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class UnstrungBowItem extends Item {

    public UnstrungBowItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 74000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack offhand = player.getOffhandItem();
        if (!offhand.is(Items.STRING)) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void onUseTick(Level level, LivingEntity user, ItemStack stack, int remainingUseDuration) {
        if (!(user instanceof Player player)) return;
        int useTime = getUseDuration(stack, user) - remainingUseDuration;

        if (useTime >= 60) {
            ItemStack offhand = player.getOffhandItem();

            if (offhand.is(Items.STRING)) {
                if (!level.isClientSide) {
                    offhand.shrink(1);
                    player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.JOURNEY_BOW.get()));
                }
            }
        }
    }
}
