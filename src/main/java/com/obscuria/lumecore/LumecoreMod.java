package com.obscuria.lumecore;

import com.mojang.logging.LogUtils;
import com.obscuria.lumecore.registry.*;
import com.obscuria.lumecore.world.LumecoreEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod(LumecoreMod.MODID)
public class LumecoreMod {
    public static final String MODID = "lumecore";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final CreativeModeTab TAB = new CreativeModeTab("lumecore") {
        @Override @NotNull public ItemStack makeIcon() { return LumecoreItems.BOWL_OF_RICE.get().getDefaultInstance();}};
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int messageID = 0;

    public LumecoreMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        LumecoreBlocks.REGISTRY.register(bus);
        LumecoreItems.REGISTRY.register(bus);
        LumecoreEntities.REGISTRY.register(bus);
        LumecoreMobEffects.REGISTRY.register(bus);
        LumecoreStructures.REGISTRY.register(bus);
        LumecoreSounds.REGISTRY.register(bus);

        bus.addListener(LumecoreEntities::registerAttributes);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(LumecoreEvents::blockPlaced);
        MinecraftForge.EVENT_BUS.addListener(LumecoreEvents::blockBroken);
        MinecraftForge.EVENT_BUS.addListener(LumecoreEvents::itemUsed);
        MinecraftForge.EVENT_BUS.addListener(LumecoreEvents::explosion);
    }

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder,
                                             BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }
}
