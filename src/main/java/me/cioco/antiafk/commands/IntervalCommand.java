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
                        .then(ClientCommandManager.argument("seconds", DoubleArgumentType.doubleArg())
                                .executes(IntervalCommand::toggleInterval))));
    }

    private static int toggleInterval(CommandContext<FabricClientCommandSource> context) {
        double seconds;

        try {
            seconds = DoubleArgumentType.getDouble(context, "seconds");
        } catch (NumberFormatException e) {
            context.getSource().sendError(Text.literal("Invalid input. Please enter a valid integer.").formatted(Formatting.RED));
            return 0;
        }

        if (seconds <= 0) {
            context.getSource().sendError(Text.of("Interval must be greater than 0."));
            return 0;
        }

        interval = (int) (seconds * 20);
        config.setInterval(interval);
        config.saveConfiguration();
        context.getSource().sendFeedback(Text.literal("AntiAfk interval has been set to " + seconds + " seconds").formatted(Formatting.YELLOW));
        return 1;
    }
}