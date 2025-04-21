package net.itshamza.calliope.item;


import net.itshamza.calliope.Calliope;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items  ITEMS = DeferredRegister.createItems(Calliope.MODID);

    //public static final DeferredItem<Item> MELLIFIED_SPAWN_EGG = ITEMS.register("mellified_spawn_egg",
    //        () -> new DeferredSpawnEggItem(HominidEntityCreator.MELLIFIED, 0xefe9d1, 0xf3b34a,
    //                new Item.Properties()));


    //public static final DeferredItem<Item> CHARRED_MUSIC_DISC = ITEMS.register("charred_music_disc",
    //        () -> new Item(new Item.Properties().jukeboxPlayable(HominidSounds.CHARRED_KEY).stacksTo(1)));

    public static final DeferredItem<Item> JOURNEY_BOW = ITEMS.register("journey_bow",
            () -> new JourneyBowItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> UNSTRUNG_JOURNEY_BOW = ITEMS.register("unstrung_journey_bow",
            () -> new UnstrungBowItem(new Item.Properties().stacksTo(1)));




    public static void register (IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
