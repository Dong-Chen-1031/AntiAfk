package me.cioco.antiafk.mixin;

import me.cioco.antiafk.Main;
import me.cioco.antiafk.commands.IntervalCommand;
import me.cioco.antiafk.commands.SpinCommand;
import me.cioco.antiafk.config.AntiAfkConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(PlayerEntity.class)
public class MixinClientPlayerEntity {

    @Unique
    private static final Random random = new Random();

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {

        MinecraftClient mc = MinecraftClient.getInstance();

        if (mc == null) return;

        PlayerEntity player = mc.player;
        if (player == null) return;

        boolean utiltick = player.age % IntervalCommand.interval == 0;

        int moveX = (random.nextInt(11) - 5) * (int) AntiAfkConfig.horizontalMultiplier;
        int moveY = (random.nextInt(11) - 5) * (int) AntiAfkConfig.verticalMultiplier;

        if (Main.toggled) {
            if (AntiAfkConfig.autoJumpEnabled && utiltick && player.isOnGround()) {
                player.jump();
            }

            if (AntiAfkConfig.mouseMovement && utiltick) {
                player.setYaw(player.getYaw() + moveX);
                player.setPitch(player.getPitch() + moveY);
            }

            if (AntiAfkConfig.autoSpinEnabled) {
                player.setYaw(player.getYaw() + SpinCommand.spinSpeed);
            }

            if (AntiAfkConfig.sneak && utiltick) {
                mc.options.sneakKey.setPressed(true);
            } else if (AntiAfkConfig.sneak) {
                mc.options.sneakKey.setPressed(false);
            }

            if (AntiAfkConfig.shouldSwing && mc.world != null && mc.player != null && utiltick) {
                mc.player.swingHand(mc.player.getActiveHand());
            }
        }
    }
}
