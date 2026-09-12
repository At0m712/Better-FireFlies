package com.atom.firefly.config;

import com.atom.firefly.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FireflyConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = Paths.get("config", "firefly.json");
    private static FireflyConfig INSTANCE;

    public boolean enableDynamicLight = true;
    public boolean enableParticles = true;
    public int maxFireflies = 35;
    public int spawnChance = 60;
    public float pulseSpeed = 1.8F;

    public static FireflyConfig get() {
        if (INSTANCE == null) {
            load();
        }
        return INSTANCE;
    }

    public static void load() {
        File file = CONFIG_PATH.toFile();
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                INSTANCE = GSON.fromJson(reader, FireflyConfig.class);
                if (INSTANCE != null) {
                    Constants.LOG.info("FireFly configuration loaded successfully.");
                    return;
                }
            } catch (Exception e) {
                Constants.LOG.error("Failed to load FireFly configuration, using defaults", e);
            }
        }
        INSTANCE = new FireflyConfig();
        save();
    }

    public static void save() {
        if (INSTANCE == null) {
            INSTANCE = new FireflyConfig();
        }
        try {
            File file = CONFIG_PATH.toFile();
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            try (FileWriter writer = new FileWriter(file)) {
                GSON.toJson(INSTANCE, writer);
            }
        } catch (IOException e) {
            Constants.LOG.error("Failed to save FireFly configuration", e);
        }
    }
}
