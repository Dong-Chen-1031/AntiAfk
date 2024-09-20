package me.cioco.antiafk.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import me.cioco.antiafk.config.AntiAfkConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static me.cioco.antiafk.Main.config;
import static me.cioco.antiafk.config.AntiAfkConfig.mouseMovement;

public class MouseMovementCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("antiafk")
                .then(ClientCommandManager.literal("mousemovement")
                        .executes(MouseMovementCommand::toggleMouseMovement)
                        .then(ClientCommandManager.argument("horizontalMultiplier", FloatArgumentType.floatArg())
                                .executes(context -> setMouseMovementMultipliers(context, FloatArgumentType.getFloat(context, "horizontalMultiplier"), AntiAfkConfig.verticalMultiplier))
                                .then(ClientCommandManager.argument("verticalMultiplier", FloatArgumentType.floatArg())
                                        .executes(context -> setMouseMovementMultipliers(context, FloatArgumentType.getFloat(context, "horizontalMultiplier"), FloatArgumentType.getFloat(context, "verticalMultiplier")))))));
    }

    private static int toggleMouseMovement(CommandContext<FabricClientCommandSource> context) {
        mouseMovement = !mouseMovement;
        config.saveConfiguration();

        String statusMessage = mouseMovement ? "Mouse movement enabled with default multipliers: Horizontal: " + AntiAfkConfig.horizontalMultiplier
                + ", Vertical: " + AntiAfkConfig.verticalMultiplier : "Mouse movement disabled.";
        Formatting statusColor = mouseMovement ? Formatting.GREEN : Formatting.RED;

        context.getSource().sendFeedback(Text.literal("AntiAfk: " + statusMessage).formatted(statusColor));
        return 1;
    }

    private static int setMouseMovementMultipliers(CommandContext<FabricClientCommandSource> context, float horizontalMultiplier, float verticalMultiplier) {
        me.cioco.antiafk.config.AntiAfkConfig.horizontalMultiplier = horizontalMultiplier;
        me.cioco.antiafk.config.AntiAfkConfig.verticalMultiplier = verticalMultiplier;
        mouseMovement = true;

        config.saveConfiguration();

        context.getSource().sendFeedback(Text.literal("AntiAfk: Mouse movement updated with Horizontal: " + horizontalMultiplier
                + " and Vertical: " + verticalMultiplier).formatted(Formatting.GREEN));

        return 1;
    }
}
