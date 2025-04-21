package net.itshamza.calliope.item;

import net.itshamza.calliope.Calliope;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Calliope.MODID);

    public static final Supplier<CreativeModeTab> CALLIOPE = CREATIVE_MODE_TAB.register("calliope",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.JOURNEY_BOW.get()))
                    .title(Component.translatable("creativetab.calliope.calliope"))
                    .displayItems((displayParameters, output) -> {
                        ModItems.ITEMS.getEntries().forEach(item -> {
                            output.accept(item.get());
                        });
                    })
                    .build()
    );


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
