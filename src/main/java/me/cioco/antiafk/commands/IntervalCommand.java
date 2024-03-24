package me.cioco.antiafk.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class IntervalCommand {
    public static int interval = 100;

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(ClientCommandManager.literal("antiafk")
                .then(ClientCommandManager.literal("interval")
                        .then(ClientCommandManager.argument("seconds", IntegerArgumentType.integer())
                                .executes(IntervalCommand::toggleInterval))));
    }

    private static int toggleInterval(CommandContext<FabricClientCommandSource> context) {
        int seconds = 10;

        try {
            seconds = IntegerArgumentType.getInteger(context, "seconds");
        } catch (NumberFormatException e) {
            context.getSource().sendError(Text.literal("Invalid input. Please enter a valid integer.").formatted(Formatting.RED));
            return 0;
        }

        if (seconds <= 0) {
            context.getSource().sendError(Text.of("Interval must be greater than 0."));
            return 0;
        }

        interval = seconds * 20;
        context.getSource().sendFeedback(Text.literal("AntiAfk jump interval set to " + seconds + " seconds").formatted(Formatting.YELLOW));
        return 1;
    }
}