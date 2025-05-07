package me.cioco.antiafk.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import static me.cioco.antiafk.Main.config;
import static me.cioco.antiafk.config.AntiAfkConfig.movementEnabled;

public class MovementCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.literal("antiafk")
                .then(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.literal("movement")
                        .executes(MovementCommand::toggleStrafe)));
    }

    private static int toggleStrafe(CommandContext<net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD> context) {
        movementEnabled = !movementEnabled;
        config.saveConfiguration();
        context.getSource().sendFeedback(new StringTextComponent("AntiAfk movement has been " + (movementEnabled ? "enabled" : "disabled")).mergeStyle(TextFormatting.YELLOW));
        return 1;
    }
}
