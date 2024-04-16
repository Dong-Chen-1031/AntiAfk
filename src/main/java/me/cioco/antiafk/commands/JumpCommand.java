package me.cioco.antiafk.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class JumpCommand {
    public static boolean autoJumpEnabled = true;

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("antiafk")
                .then(ClientCommandManager.literal("jump").executes(JumpCommand::jump)));
    }

    private static int jump(CommandContext<FabricClientCommandSource> context) {
        autoJumpEnabled = !autoJumpEnabled;
        String statusMessage = autoJumpEnabled ? "Auto Jump Enabled" : "Auto Jump Disabled";
        Formatting statusColor = autoJumpEnabled ? Formatting.GREEN : Formatting.RED;
        context.getSource().sendFeedback(Text.literal("AntiAfk: " + statusMessage).formatted(statusColor));
        return 1;
    }
}
