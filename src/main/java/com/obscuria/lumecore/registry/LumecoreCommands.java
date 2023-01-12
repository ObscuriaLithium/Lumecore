package com.obscuria.lumecore.registry;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.obscuria.lumecore.LumecoreUtils;
import com.obscuria.lumecore.world.MansionParts;
import com.obscuria.lumecore.world.entities.props.MansionCoreEntity;
import com.obscuria.obscureapi.utils.TextHelper;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber
public class LumecoreCommands {
    private static final String MANSION_NOT_FOUND = "§cMansion not Found";

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("lumecore").requires(s -> s.hasPermission(4))
                .then(Commands.literal("debug").executes(arguments -> {
                    final ServerPlayer player = arguments.getSource().getPlayer();
                    if (player != null) player.addItem(LumecoreItems.DEBUG_TOOL.get().getDefaultInstance());
                    return 0;
                }))
                .then(Commands.literal("mansion").executes(arguments -> {
                    final ServerPlayer player = arguments.getSource().getPlayer();
                    if (player == null) return 0;
                    final MansionCoreEntity core = LumecoreUtils.Location.getCore(player);
                    if (core == null) return sendMessage(player, MANSION_NOT_FOUND);
                    final List<Monster> monsters = core.getLevel().getEntitiesOfClass(Monster.class, new AABB(core.blockPosition()).inflate(MansionParts.SIZE));
                    player.sendSystemMessage(TextHelper.component(
                            "\nThe Mansion at [" + core.getX() + ", " + core.getY() + ", " + core.getZ() + "]"
                                    + "\nRules: §7canBuild: " + (core.getRuleCanBuild() ? "§2" : "§c") + core.getRuleCanBuild() + "§7, "
                                    + "§7suppressExplosions: " + (core.getRuleSuppressExplosions() ? "§2" : "§c") + core.getRuleSuppressExplosions() + "§7, "
                                    + "§7reduceHealth: " + (core.getRuleReduceHealth() ? "§2" : "§c") + core.getRuleReduceHealth() + "§7, "
                                    + "§7equipmentDecay: " + (core.getRuleEquipmentDecay() ? "§2" : "§c") + core.getRuleEquipmentDecay() + "§7, "
                                    + "§7foodDecay: " + (core.getRuleFoodDecay() ? "§2" : "§c") + core.getRuleFoodDecay() + "§7, "
                                    + "§7wingsUpdate: " + (core.getRuleWingsUpdate() ? "§2" : "§c") + core.getRuleWingsUpdate()
                                    + "\n§fWings: §7tick: §f" + core.getWingsUpdateTick() + "§7, " + "§7states: "
                                    + core.getWingState(1).getColor() + core.getWingState(1).getName() + "§7, "
                                    + core.getWingState(2).getColor() + core.getWingState(2).getName() + "§7, "
                                    + core.getWingState(3).getColor() + core.getWingState(3).getName() + "§7, "
                                    + core.getWingState(4).getColor() + core.getWingState(4).getName()
                                    + "\n§fMonsters: §7" + monsters.size()));
                    return 0;
                }))
                .then(Commands.literal("location").executes(arguments -> {
                    final ServerPlayer player = arguments.getSource().getPlayer();
                    if (player == null) return 0;
                    if (LumecoreUtils.Location.getWingState(player) == MansionParts.WingState.HEALTHY) return sendMessage(player, "Healthy Wing");
                    if (LumecoreUtils.Location.getWingState(player) == MansionParts.WingState.REGENERATING) return sendMessage(player, "Regenerating Wing");
                    if (LumecoreUtils.Location.getWingState(player) == MansionParts.WingState.INFESTED) return sendMessage(player, "Infested Wing");
                    if (LumecoreUtils.Location.isInMansion(player)) return sendMessage(player, "Mansion, no Specific Part");
                    return sendMessage(player, MANSION_NOT_FOUND);
                }))
                .then(Commands.literal("rules")
                        .then(Commands.literal("set")
                                .then(Commands.literal("canBuild").then(Commands.argument("flag", BoolArgumentType.bool()).executes(arguments ->
                                        setRule(arguments.getSource().getPlayer(), "canBuild", arguments.getArgument("flag", boolean.class)))))
                                .then(Commands.literal("suppressExplosions").then(Commands.argument("flag", BoolArgumentType.bool()).executes(arguments ->
                                        setRule(arguments.getSource().getPlayer(), "suppressExplosions", arguments.getArgument("flag", boolean.class)))))
                                .then(Commands.literal("reduceHealth").then(Commands.argument("flag", BoolArgumentType.bool()).executes(arguments ->
                                        setRule(arguments.getSource().getPlayer(), "reduceHealth", arguments.getArgument("flag", boolean.class)))))
                                .then(Commands.literal("equipmentDecay").then(Commands.argument("flag", BoolArgumentType.bool()).executes(arguments ->
                                        setRule(arguments.getSource().getPlayer(), "equipmentDecay", arguments.getArgument("flag", boolean.class)))))
                                .then(Commands.literal("foodDecay").then(Commands.argument("flag", BoolArgumentType.bool()).executes(arguments ->
                                        setRule(arguments.getSource().getPlayer(), "foodDecay", arguments.getArgument("flag", boolean.class)))))
                                .then(Commands.literal("wingsUpdate").then(Commands.argument("flag", BoolArgumentType.bool()).executes(arguments ->
                                        setRule(arguments.getSource().getPlayer(), "wingsUpdate", arguments.getArgument("flag", boolean.class)))))
                        )
                )
                .then(Commands.literal("wings")
                        .then(Commands.literal("update").executes(arguments -> {
                            final ServerPlayer player = arguments.getSource().getPlayer();
                            if (player == null) return 0;
                            final MansionCoreEntity core = LumecoreUtils.Location.getCore(player);
                            if (core == null) return sendMessage(player, MANSION_NOT_FOUND);
                            core.setWingsUpdateTick(0);
                            return sendMessage(player, "Wings updated");
                        }))
                        .then(Commands.literal("set")
                                .then(Commands.argument("Wing Index", IntegerArgumentType.integer(1, 4))
                                        .then(Commands.literal("healthy").executes(arguments -> {
                                            final ServerPlayer player = arguments.getSource().getPlayer();
                                            if (player == null) return 0;
                                            final MansionCoreEntity core = LumecoreUtils.Location.getCore(player);
                                            if (core == null) return sendMessage(player, MANSION_NOT_FOUND);
                                            core.setWingState(arguments.getArgument("Wing Index", Integer.class), 0);
                                            return sendMessage(player, "Wing " + arguments.getArgument("Wing Index", Integer.class) + " §7is now §fHealthy");
                                        }))
                                        .then(Commands.literal("regenerating").executes(arguments -> {
                                            final ServerPlayer player = arguments.getSource().getPlayer();
                                            if (player == null) return 0;
                                            final MansionCoreEntity core = LumecoreUtils.Location.getCore(player);
                                            if (core == null) return sendMessage(player, MANSION_NOT_FOUND);
                                            core.setWingState(arguments.getArgument("Wing Index", Integer.class), 1);
                                            return sendMessage(player, "Wing " + arguments.getArgument("Wing Index", Integer.class) + " §7is now §fRegenerating");
                                        }))
                                        .then(Commands.literal("infested").executes(arguments -> {
                                            final ServerPlayer player = arguments.getSource().getPlayer();
                                            if (player == null) return 0;
                                            final MansionCoreEntity core = LumecoreUtils.Location.getCore(player);
                                            if (core == null) return sendMessage(player, MANSION_NOT_FOUND);
                                            core.setWingState(arguments.getArgument("Wing Index", Integer.class), 2);
                                            return sendMessage(player, "Wing " + arguments.getArgument("Wing Index", Integer.class) + " §7is now §fInfested");
                                        }))
                                )
                        )
                )
        );
    }

    private static int sendMessage(ServerPlayer player, String message) {
        player.sendSystemMessage(TextHelper.component(message));
        return 0;
    }

    private static int setRule(@Nullable ServerPlayer player, String rule, boolean flag) {
        if (player == null) return 0;
        final MansionCoreEntity core = LumecoreUtils.Location.getCore(player);
        if (core == null) return sendMessage(player, MANSION_NOT_FOUND);
        core.setRule(rule, flag);
        return sendMessage(player, "§7Rule §f" + rule + " §7is now §f" + flag);
    }
}
