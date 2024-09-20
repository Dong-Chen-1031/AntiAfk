package me.cioco.antiafk.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class AntiAfkConfig {

    public static final String CONFIG_FILE = "antiafk-config.properties";

    public static boolean autoJumpEnabled = true;
    public static boolean mouseMovement = false;
    public static boolean sneak = false;
    public static boolean autoSpinEnabled = false;
    public static boolean shouldSwing = false;
    public static float horizontalMultiplier = 2.0f;
    public static float verticalMultiplier = 1.5f;
    private static int interval = 100;

    public void saveConfiguration() {
        try {
            Path configPath = getConfigPath();
            Files.createDirectories(configPath.getParent());

            try (OutputStream output = Files.newOutputStream(configPath)) {
                Properties properties = new Properties();
                properties.setProperty("autoJumpEnabled", String.valueOf(autoJumpEnabled));
                properties.setProperty("mouseMovement", String.valueOf(mouseMovement));
                properties.setProperty("sneak", String.valueOf(sneak));
                properties.setProperty("autoSpinEnabled", String.valueOf(autoSpinEnabled));
                properties.setProperty("shouldSwing", String.valueOf(shouldSwing));
                properties.setProperty("interval", String.valueOf(interval));
                properties.setProperty("horizontalMultiplier", String.valueOf(horizontalMultiplier));
                properties.setProperty("verticalMultiplier", String.valueOf(verticalMultiplier));


                properties.store(output, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfiguration() {
        Path configPath = getConfigPath();
        if (!Files.exists(configPath)) {
            return;
        }
        try (InputStream input = Files.newInputStream(configPath)) {
            Properties properties = new Properties();
            properties.load(input);

            autoJumpEnabled = Boolean.parseBoolean(properties.getProperty("autoJumpEnabled"));
            mouseMovement = Boolean.parseBoolean(properties.getProperty("mouseMovement"));
            sneak = Boolean.parseBoolean(properties.getProperty("sneak"));
            autoSpinEnabled = Boolean.parseBoolean(properties.getProperty("autoSpinEnabled"));
            shouldSwing = Boolean.parseBoolean(properties.getProperty("shouldSwing"));
            interval = Integer.parseInt(properties.getProperty("interval"));
            horizontalMultiplier = Float.parseFloat(properties.getProperty("horizontalMultiplier", "2.0"));
            verticalMultiplier = Float.parseFloat(properties.getProperty("verticalMultiplier", "1.5"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE);
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        AntiAfkConfig.interval = interval;
    }
}
