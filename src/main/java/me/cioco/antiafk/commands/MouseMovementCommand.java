package me.cioco.antiafk.commands;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static me.cioco.antiafk.Main.config;
import static me.cioco.antiafk.config.AntiAfkConfig.mouseMovement;

public class MouseMovementCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("antiafk")
                .then(ClientCommandManager.literal("mousemovement").executes(MouseMovementCommand::mousemovement)));
    }

    private static int mousemovement(CommandContext<FabricClientCommandSource> context) {
        mouseMovement = !mouseMovement;
        config.saveConfiguration();

        String statusMessage = mouseMovement ? "MouseMovement Enabled" : "MouseMovement Disabled";
        Formatting statusColor = mouseMovement ? Formatting.GREEN : Formatting.RED;
        context.getSource().sendFeedback(Text.literal("AntiAfk: " + statusMessage).formatted(statusColor));
        return 1;
    }
}
