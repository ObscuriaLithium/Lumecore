package com.obscuria.lumecore.registry;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.obscuria.lumecore.LumecoreUtils;
import com.obscuria.lumecore.world.MansionParts;
import com.obscuria.lumecore.world.entities.props.MansionCoreEntity;
import com.obscuria.obscureapi.utils.TextHelper;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

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
                    player.sendSystemMessage(TextHelper.component(
                            "The Mansion at " + core.getX() + ", " + core.getY() + ", " + core.getZ() + "\n"
                                    + "§7  canBuild: §8" + core.getRuleCanBuild() + "\n"
                                    + "§7  suppressExplosions: §8" + core.getRuleSuppressExplosions() + "\n"
                                    + "§7  reduceHealth: §8" + core.getRuleReduceHealth() + "\n"
                                    + "§7  equipmentDecay: §8" + core.getRuleEquipmentDecay() + "\n"
                                    + "§7  foodDecay: §8" + core.getRuleFoodDecay() + "\n"
                                    + "§7  Wings Update Tick: §8" + core.getWingsUpdateTick() + "\n"
                                    + "§7  Wings States: §8"
                                    + core.getWingState(1).getName() + ", "
                                    + core.getWingState(2).getName() + ", "
                                    + core.getWingState(3).getName() + ", "
                                    + core.getWingState(4).getName()));
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
                        )
                )
                .then(Commands.literal("wing")
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
        switch (rule) {
            case "canBuild" -> core.setRuleCanBuild(flag);
            case "suppressExplosions" -> core.setRuleSuppressExplosions(flag);
            case "reduceHealth" -> core.setRuleReduceHealth(flag);
            case "equipmentDecay" -> core.setRuleEquipmentDecay(flag);
            default -> core.setRuleFoodDecay(flag);
        }
        return sendMessage(player, "§7Rule §f" + rule + " §7is now §f" + flag);
    }
}
