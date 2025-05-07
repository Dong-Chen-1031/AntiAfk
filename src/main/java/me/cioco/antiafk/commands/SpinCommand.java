package me.cioco.antiafk.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import static me.cioco.antiafk.Main.config;
import static me.cioco.antiafk.config.AntiAfkConfig.autoSpinEnabled;

public class SpinCommand {

    public static int spinSpeed = 1;

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.literal("antiafk")
                .then(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.literal("spin")
                        .executes(SpinCommand::toggleSpin)
                        .then(net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD.argument("speed", IntegerArgumentType.integer())
                                .executes(SpinCommand::setSpinSpeed))));
    }

    private static int toggleSpin(CommandContext<net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD> context) {
        autoSpinEnabled = !autoSpinEnabled;
        config.saveConfiguration();

        String statusMessage = autoSpinEnabled ? "Auto Spin Enabled" : "Auto Spin Disabled";
        TextFormatting statusColor = autoSpinEnabled ? TextFormatting.GREEN : TextFormatting.RED;
        context.getSource().sendFeedback(new StringTextComponent("AntiAfk: " + statusMessage).mergeStyle(statusColor));
        return 1;
    }

    private static int setSpinSpeed(CommandContext<net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD> context) {
        int speed = IntegerArgumentType.getInteger(context, "speed");
        if (speed <= 0) {
            context.getSource().sendError(new StringTextComponent("Spin speed must be greater than 0").mergeStyle(TextFormatting.RED));
            return 0;
        }
        spinSpeed = speed;
        String statusMessage = "Spin speed has been set to " + spinSpeed;
        context.getSource().sendFeedback(new StringTextComponent("AntiAfk: " + statusMessage).mergeStyle(TextFormatting.YELLOW));
        return 1;
    }
}
