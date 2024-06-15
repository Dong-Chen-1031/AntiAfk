package me.cioco.antiafk.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static me.cioco.antiafk.Main.config;
import static me.cioco.antiafk.config.AntiAfkConfig.shouldSwing;

public class SwingCommand {


    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("antiafk")
                .then(ClientCommandManager.literal("swing").executes(SwingCommand::toggleSwing)));
    }

    private static int toggleSwing(CommandContext<FabricClientCommandSource> context) {
        shouldSwing = !shouldSwing;
        config.saveConfiguration();

        String statusMessage = shouldSwing ? "Swinging Enabled" : "Swinging Disabled";
        Formatting statusColor = shouldSwing ? Formatting.GREEN : Formatting.RED;
        context.getSource().sendFeedback(Text.literal("AntiAfk: " + statusMessage).formatted(statusColor));
        return 1;
    }
}