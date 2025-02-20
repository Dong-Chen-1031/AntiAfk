package me.cioco.antiafk.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static me.cioco.antiafk.Main.config;

public class IntervalCommand {
    public static int interval = config.getInterval();

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        config.saveConfiguration();

        dispatcher.register(ClientCommandManager.literal("antiafk")
                .then(ClientCommandManager.literal("interval")
                        .then(ClientCommandManager.literal("set")
                                .then(ClientCommandManager.argument("seconds", DoubleArgumentType.doubleArg())
                                        .executes(IntervalCommand::setFixedInterval)))
                        .then(ClientCommandManager.literal("random")
                                .then(ClientCommandManager.argument("minSeconds", DoubleArgumentType.doubleArg())
                                        .then(ClientCommandManager.argument("maxSeconds", DoubleArgumentType.doubleArg())
                                                .executes(IntervalCommand::setRandomInterval))))));
    }

    private static int setFixedInterval(CommandContext<FabricClientCommandSource> context) {
        double seconds = DoubleArgumentType.getDouble(context, "seconds");

        if (seconds <= 0) {
            context.getSource().sendError(Text.literal("Interval must be greater than 0.").formatted(Formatting.RED));
            return 0;
        }

        interval = (int) (seconds * 20);
        config.setInterval(interval);
        config.setRandomInterval(false);
        config.saveConfiguration();
        context.getSource().sendFeedback(Text.literal("AntiAfk interval set to " + seconds + " seconds.").formatted(Formatting.YELLOW));
        return 1;
    }

    private static int setRandomInterval(CommandContext<FabricClientCommandSource> context) {
        double minSeconds = DoubleArgumentType.getDouble(context, "minSeconds");
        double maxSeconds = DoubleArgumentType.getDouble(context, "maxSeconds");

        if (minSeconds <= 0 || maxSeconds <= 0) {
            context.getSource().sendError(Text.literal("Intervals must be greater than 0.").formatted(Formatting.RED));
            return 0;
        }

        if (minSeconds >= maxSeconds) {
            context.getSource().sendError(Text.literal("Minimum interval must be less than maximum interval.").formatted(Formatting.RED));
            return 0;
        }

        config.setMinInterval((int) (minSeconds * 20));
        config.setMaxInterval((int) (maxSeconds * 20));
        config.setRandomInterval(true);
        config.saveConfiguration();
        context.getSource().sendFeedback(Text.literal("AntiAfk interval set to a random value between " + minSeconds + " and " + maxSeconds + " seconds.").formatted(Formatting.YELLOW));
        return 1;
    }
}
