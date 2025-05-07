package me.cioco.antiafk.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import static me.cioco.antiafk.Main.config;

public class IntervalCommand {
    public static int interval = config.getInterval();

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        config.saveConfiguration();

        event.getDispatcher().register(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.literal("antiafk")
                .then(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.literal("interval")
                        .then(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.literal("set")
                                .then(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.argument("seconds", DoubleArgumentType.doubleArg())
                                        .executes(IntervalCommand::setFixedInterval)))
                        .then(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.literal("random")
                                .then(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.argument("minSeconds", DoubleArgumentType.doubleArg())
                                        .then(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.argument("maxSeconds", DoubleArgumentType.doubleArg())
                                                .executes(IntervalCommand::setRandomInterval))))));
    }

    private static int setFixedInterval(CommandContext<net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD> context) {
        double seconds = DoubleArgumentType.getDouble(context, "seconds");

        if (seconds <= 0) {
            context.getSource().sendError(new StringTextComponent("Interval must be greater than 0.").mergeStyle(TextFormatting.RED));
            return 0;
        }

        interval = (int) (seconds * 20);
        config.setInterval(interval);
        config.setRandomInterval(false);
        config.saveConfiguration();
        context.getSource().sendFeedback(new StringTextComponent("AntiAfk interval set to " + seconds + " seconds.").mergeStyle(TextFormatting.YELLOW));
        return 1;
    }

    private static int setRandomInterval(CommandContext<net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD> context) {
        double minSeconds = DoubleArgumentType.getDouble(context, "minSeconds");
        double maxSeconds = DoubleArgumentType.getDouble(context, "maxSeconds");

        if (minSeconds <= 0 || maxSeconds <= 0) {
            context.getSource().sendError(new StringTextComponent("Intervals must be greater than 0.").mergeStyle(TextFormatting.RED));
            return 0;
        }

        if (minSeconds >= maxSeconds) {
            context.getSource().sendError(new StringTextComponent("Minimum interval must be less than maximum interval.").mergeStyle(TextFormatting.RED));
            return 0;
        }

        config.setMinInterval((int) (minSeconds * 20));
        config.setMaxInterval((int) (maxSeconds * 20));
        config.setRandomInterval(true);
        config.saveConfiguration();
        context.getSource().sendFeedback(new StringTextComponent("AntiAfk interval set to a random value between " + minSeconds + " and " + maxSeconds + " seconds.").mergeStyle(TextFormatting.YELLOW));
        return 1;
    }
}
