package me.cioco.antiafk.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import static me.cioco.antiafk.Main.config;
import static me.cioco.antiafk.config.AntiAfkConfig.sneak;

public class SneakCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.literal("antiafk")
                .then(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.literal("sneak").executes(SneakCommand::sneakcommand)));
    }

    private static int sneakcommand(CommandContext<net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD> context) {
        sneak = !sneak;
        config.saveConfiguration();

        String statusMessage = sneak ? "Auto sneak Enabled" : "Auto sneak Disabled";
        TextFormatting statusColor = sneak ? TextFormatting.GREEN : TextFormatting.RED;
        context.getSource().sendFeedback(new StringTextComponent("AntiAfk: " + statusMessage).mergeStyle(statusColor));
        return 1;

    }
}
