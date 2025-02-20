package me.cioco.antiafk.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static me.cioco.antiafk.Main.config;
import static me.cioco.antiafk.config.AntiAfkConfig.movementEnabled;

public class MovementCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("antiafk")
                .then(ClientCommandManager.literal("movement")
                        .executes(MovementCommand::toggleStrafe)));

    }

    private static int toggleStrafe(CommandContext<FabricClientCommandSource> context) {
        movementEnabled = !movementEnabled;
        config.saveConfiguration();
        context.getSource().sendFeedback(Text.literal("AntiAfk movement has been " + (movementEnabled ? "enabled" : "disabled")).formatted(Formatting.YELLOW));
        return 1;
    }


}
