package me.cioco.antiafk.mixin;

import me.cioco.antiafk.Main;
import me.cioco.antiafk.commands.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(PlayerEntity.class)
public class MixinClientPlayerEntity {
    private static final Random random = new Random();

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        MinecraftClient client = MinecraftClient.getInstance();

        int moveX = random.nextInt(11) - 5;


        if (Main.toggled && JumpCommand.autoJumpEnabled && player.age % IntervalCommand.interval == 0 && player.isOnGround()) {
            player.jump();
        }

        if (Main.toggled && SpinCommand.autoSpinEnabled) {
            player.setYaw(player.getYaw() + 1.0F);
        }

        if (Main.toggled && MouseMovementCommand.mousemovement && player.age % IntervalCommand.interval == 0) {
            player.setYaw(player.getYaw() + moveX);
        }

        if (Main.toggled && SneakCommand.sneak && player.age % IntervalCommand.interval == 0) {
            client.options.sneakKey.setPressed(true);
        } else if (Main.toggled && SneakCommand.sneak) {
            client.options.sneakKey.setPressed(false);
        }


        if (SwingCommand.shouldSwing && Main.toggled && client != null && client.world != null && client.player != null && client.world.getTime() % IntervalCommand.interval == 0) {
            client.player.swingHand(client.player.getActiveHand());
        }

    }
}
