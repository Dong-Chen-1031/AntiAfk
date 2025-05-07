package me.cioco.antiafk;

import me.cioco.antiafk.commands.*;
import me.cioco.antiafk.config.AntiAfkConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientTickEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fmlclient.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.glfw.GLFW;

@Mod("antiafk")
public class Main {
    public static final AntiAfkConfig config = new AntiAfkConfig();
    public static KeyBinding keyBinding;
    public static boolean toggled = false;

    public Main() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        config.loadConfiguration();

        keyBinding = new KeyBinding(
                "key.antiafk.toggle",
                GLFW.GLFW_KEY_UNKNOWN,
                "key.categories.antiafk"
        );
        ClientRegistry.registerKeyBinding(keyBinding);
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        if (event.phase == ClientTickEvent.Phase.END) {
            if (event.getClient().world != null && event.getClient().player != null) {
                if (event.getClient().world.getGameTime() == 1L) {
                    toggled = false;
                    event.getClient().player.sendMessage(new StringTextComponent("AntiAfk: Disabled").mergeStyle(TextFormatting.RED), false);
                }

                if (keyBinding.isPressed()) {
                    toggled = !toggled;
                    String statusMessage = toggled ? "Enabled" : "Disabled";
                    TextFormatting statusColor = toggled ? TextFormatting.GREEN : TextFormatting.RED;
                    event.getClient().player.sendMessage(new StringTextComponent("AntiAfk: " + statusMessage).mergeStyle(statusColor), false);
                }
            }
        }
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        IntervalCommand.register(event.getDispatcher());
        JumpCommand.register(event.getDispatcher());
        SneakCommand.register(event.getDispatcher());
        SpinCommand.register(event.getDispatcher());
        SwingCommand.register(event.getDispatcher());
        MouseMovementCommand.register(event.getDispatcher());
        MovementCommand.register(event.getDispatcher());
    }
}
