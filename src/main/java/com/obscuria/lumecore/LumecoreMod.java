package com.obscuria.lumecore;

import com.mojang.logging.LogUtils;
import com.obscuria.lumecore.world.blocks.WallChandelierBlock;
import com.obscuria.lumecore.world.blocks.WallChandelierLeverBlock;
import com.obscuria.lumecore.world.blocks.WallChandelierMonoBlock;
import com.obscuria.lumecore.world.entities.AshenWitchEntity;
import com.obscuria.lumecore.world.entities.props.LyingItemEntity;
import com.obscuria.lumecore.world.entities.props.ReliquaryEntity;
import com.obscuria.lumecore.world.items.DebugTool;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(LumecoreMod.MODID)
public class LumecoreMod {
    public static final String MODID = "lumecore";
    public static final Logger LOGGER = LogUtils.getLogger();

    public LumecoreMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        LumecoreMod.Blocks.BLOCKS.register(bus);
        LumecoreMod.Items.ITEMS.register(bus);
        LumecoreMod.Entities.ENTITY_TYPES.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::commonSetup);
        bus.addListener(LumecoreMod.Entities::registerAttributes);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }

    public static class Items {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

        public static final RegistryObject<Item> DEBUG_TOOL = ITEMS.register("debug_tool", DebugTool::new);

        public static final RegistryObject<Item> BASEBOARD = ITEMS.register("baseboard",
                () -> new BlockItem(Blocks.BASEBOARD.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

        public static final RegistryObject<Item> WALL_CHANDELIER = ITEMS.register("wall_chandelier",
                () -> new BlockItem(Blocks.WALL_CHANDELIER.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        public static final RegistryObject<Item> WALL_CHANDELIER_MONO = ITEMS.register("wall_chandelier_mono",
                () -> new BlockItem(Blocks.WALL_CHANDELIER_MONO.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        public static final RegistryObject<Item> WALL_CHANDELIER_LEVER = ITEMS.register("wall_chandelier_lever",
                () -> new BlockItem(Blocks.WALL_CHANDELIER_LEVER.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    }

    public static class Blocks {
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

        public static final RegistryObject<Block> BASEBOARD = BLOCKS.register("baseboard",
                () -> new Block(BlockBehaviour.Properties.of(Material.WOOD)));

        public static final RegistryObject<Block> WALL_CHANDELIER = BLOCKS.register("wall_chandelier", WallChandelierBlock::new);
        public static final RegistryObject<Block> WALL_CHANDELIER_MONO = BLOCKS.register("wall_chandelier_mono", WallChandelierMonoBlock::new);
        public static final RegistryObject<Block> WALL_CHANDELIER_LEVER = BLOCKS.register("wall_chandelier_lever", WallChandelierLeverBlock::new);
    }

    public static class Entities {
        public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

        public static final RegistryObject<EntityType<ReliquaryEntity>> RELIQUARY = register("reliquary",
                EntityType.Builder.<ReliquaryEntity>of(ReliquaryEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(false)
                        .setTrackingRange(64).setUpdateInterval(3).fireImmune().sized(0.5f, 0.5f));
        public static final RegistryObject<EntityType<LyingItemEntity>> LYING_ITEM = register("lying_item",
                EntityType.Builder.<LyingItemEntity>of(LyingItemEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(false)
                        .setTrackingRange(64).setUpdateInterval(3).fireImmune().sized(0.5f, 0.1f));

        public static final RegistryObject<EntityType<AshenWitchEntity>> ASHEN_WITCH = register("ashen_witch",
                EntityType.Builder.<AshenWitchEntity>of(AshenWitchEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
                        .setTrackingRange(64).setUpdateInterval(3).fireImmune().sized(0.6f, 1.7f));

        private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> entityTypeBuilder) {
            return ENTITY_TYPES.register(name, () -> entityTypeBuilder.build(name));
        }

        private static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(ASHEN_WITCH.get(), AshenWitchEntity.createAttributes().build());
        }
    }
}
