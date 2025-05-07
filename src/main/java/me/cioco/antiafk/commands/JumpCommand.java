package me.cioco.antiafk.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import static me.cioco.antiafk.Main.config;
import static me.cioco.antiafk.config.AntiAfkConfig.autoJumpEnabled;

public class JumpCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.literal("antiafk")
                .then(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.literal("jump").executes(JumpCommand::jump)));
    }

    private static int jump(CommandContext<net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD> context) {
        autoJumpEnabled = !autoJumpEnabled;
        config.saveConfiguration();

        String statusMessage = autoJumpEnabled ? "Auto Jump Enabled" : "Auto Jump Disabled";
        TextFormatting statusColor = autoJumpEnabled ? TextFormatting.GREEN : TextFormatting.RED;
        context.getSource().sendFeedback(new StringTextComponent("AntiAfk: " + statusMessage).mergeStyle(statusColor));
        return 1;
    }
}
