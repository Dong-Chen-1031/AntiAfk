package me.cioco.antiafk.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static me.cioco.antiafk.Main.config;
import static me.cioco.antiafk.config.AntiAfkConfig.autoSpinEnabled;

public class SpinCommand {

    public static int spinSpeed = 1;

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("antiafk")
                .then(ClientCommandManager.literal("spin")
                        .executes(SpinCommand::toggleSpin)
                        .then(ClientCommandManager.argument("speed", IntegerArgumentType.integer())
                                .executes(SpinCommand::setSpinSpeed))));
    }

    private static int toggleSpin(CommandContext<FabricClientCommandSource> context) {
        autoSpinEnabled = !autoSpinEnabled;
        config.saveConfiguration();

        String statusMessage = autoSpinEnabled ? "Auto Spin Enabled" : "Auto Spin Disabled";
        Formatting statusColor = autoSpinEnabled ? Formatting.GREEN : Formatting.RED;
        context.getSource().sendFeedback(Text.literal("AntiAfk: " + statusMessage).formatted(statusColor));
        return 1;
    }

    private static int setSpinSpeed(CommandContext<FabricClientCommandSource> context) {
        int speed = IntegerArgumentType.getInteger(context, "speed");
        if (speed <= 0) {
            context.getSource().sendError(Text.literal("Spin speed must be greater than 0").formatted(Formatting.RED));
            return 0;
        }
        spinSpeed = speed;
        String statusMessage = "Spin speed has been set to " + spinSpeed;
        context.getSource().sendFeedback(Text.literal("AntiAfk: " + statusMessage).formatted(Formatting.YELLOW));
        return 1;
    }
}
