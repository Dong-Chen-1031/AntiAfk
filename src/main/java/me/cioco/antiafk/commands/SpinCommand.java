package me.cioco.antiafk.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SpinCommand {
    public static boolean autoSpinEnabled = false;

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(ClientCommandManager.literal("antiafk")
                .then(ClientCommandManager.literal("spin").executes(SpinCommand::spin)));
    }

    private static int spin(CommandContext<FabricClientCommandSource> context) {
        autoSpinEnabled = !autoSpinEnabled;
        String statusMessage = autoSpinEnabled ? "Auto Spin Enabled" : "Auto Spin Disabled";
        Formatting statusColor = autoSpinEnabled ? Formatting.GREEN : Formatting.RED;
        context.getSource().sendFeedback(Text.literal("AntiAfk: " + statusMessage).formatted(statusColor));
        return 1;
    }
}
