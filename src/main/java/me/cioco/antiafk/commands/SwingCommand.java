package me.cioco.antiafk.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import static me.cioco.antiafk.Main.config;
import static me.cioco.antiafk.config.AntiAfkConfig.shouldSwing;

public class SwingCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.literal("antiafk")
                .then(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.literal("swing").executes(SwingCommand::toggleSwing)));
    }

    private static int toggleSwing(CommandContext<net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD> context) {
        shouldSwing = !shouldSwing;
        config.saveConfiguration();

        String statusMessage = shouldSwing ? "Swinging Enabled" : "Swinging Disabled";
        TextFormatting statusColor = shouldSwing ? TextFormatting.GREEN : TextFormatting.RED;
        context.getSource().sendFeedback(new StringTextComponent("AntiAfk: " + statusMessage).mergeStyle(statusColor));
        return 1;
    }
}
