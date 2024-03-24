package me.cioco.antiafk.commands;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class MouseMovementCommand {

    public static boolean mousemovement = false;

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(ClientCommandManager.literal("antiafk")
                .then(ClientCommandManager.literal("mousemovement").executes(MouseMovementCommand::mousemovement)));
    }

    private static int mousemovement(CommandContext<FabricClientCommandSource> context) {
        mousemovement = !mousemovement;
        String statusMessage = mousemovement ? "MouseMovement Enabled" : "MouseMovement Disabled";
        Formatting statusColor = mousemovement ? Formatting.GREEN : Formatting.RED;
        context.getSource().sendFeedback(Text.literal("AntiAfk: " + statusMessage).formatted(statusColor));
        return 1;
    }
}
