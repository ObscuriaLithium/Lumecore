package com.obscuria.lumecore.network;

import com.obscuria.lumecore.LumecoreMod;
import com.obscuria.lumecore.registry.LumecoreSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwarmInfectionMessage {
    int arg;

    public SwarmInfectionMessage(int type) {
        this.arg = type;
    }

    public SwarmInfectionMessage(FriendlyByteBuf buffer) {
        this.arg = buffer.readInt();
    }

    public static void buffer(SwarmInfectionMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.arg);
    }

    public static void handler(SwarmInfectionMessage message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forLocalAmbience(LumecoreSounds.SWARM_INFECTION.get(), 1f, 1f))));
        context.get().setPacketHandled(true);
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        LumecoreMod.addNetworkMessage(SwarmInfectionMessage.class, SwarmInfectionMessage::buffer, SwarmInfectionMessage::new, SwarmInfectionMessage::handler);
    }
}
