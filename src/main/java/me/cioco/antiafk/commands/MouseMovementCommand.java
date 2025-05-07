package me.cioco.antiafk.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import me.cioco.antiafk.config.AntiAfkConfig;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import static me.cioco.antiafk.Main.config;
import static me.cioco.antiafk.config.AntiAfkConfig.mouseMovement;

public class MouseMovementCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.literal("antiafk")
                .then(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.literal("mousemovement")
                        .executes(MouseMovementCommand::toggleMouseMovement)
                        .then(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.argument("horizontalMultiplier", FloatArgumentType.floatArg())
                                .executes(context -> setMouseMovementMultipliers(context, FloatArgumentType.getFloat(context, "horizontalMultiplier"), AntiAfkConfig.verticalMultiplier))
                                .then(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.argument("verticalMultiplier", FloatArgumentType.floatArg())
                                        .executes(context -> setMouseMovementMultipliers(context, FloatArgumentType.getFloat(context, "horizontalMultiplier"), FloatArgumentType.getFloat(context, "verticalMultiplier")))))));
    }

    private static int toggleMouseMovement(CommandContext<net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD> context) {
        mouseMovement = !mouseMovement;
        config.saveConfiguration();

        String statusMessage = mouseMovement ? "Mouse movement enabled with default multipliers: Horizontal: " + AntiAfkConfig.horizontalMultiplier
                + ", Vertical: " + AntiAfkConfig.verticalMultiplier : "Mouse movement disabled.";
        TextFormatting statusColor = mouseMovement ? TextFormatting.GREEN : TextFormatting.RED;

        context.getSource().sendFeedback(new StringTextComponent("AntiAfk: " + statusMessage).mergeStyle(statusColor));
        return 1;
    }

    private static int setMouseMovementMultipliers(CommandContext<net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD> context, float horizontalMultiplier, float verticalMultiplier) {
        me.cioco.antiafk.config.AntiAfkConfig.horizontalMultiplier = horizontalMultiplier;
        me.cioco.antiafk.config.AntiAfkConfig.verticalMultiplier = verticalMultiplier;
        mouseMovement = true;

        config.saveConfiguration();

        context.getSource().sendFeedback(new StringTextComponent("AntiAfk: Mouse movement updated with Horizontal: " + horizontalMultiplier
                + " and Vertical: " + verticalMultiplier).mergeStyle(TextFormatting.GREEN));

        return 1;
    }
}
