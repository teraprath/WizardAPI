package dev.mending.wizard.plugin.config;

import com.github.teraprath.tinylib.config.ConfigHandler;
import com.github.teraprath.tinylib.lang.Lang;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class WizardConfig extends ConfigHandler {

    private long clickTimeout;

    private boolean clickSequenzSoundEnabled;
    private String clickSequenzSoundType;
    private float clickSequenzSoundVolume;
    private float clickSequenzSoundPitch;

    private boolean clickSequenzMessageEnabled;
    private String clickSequenzType;
    private Component clickSequenzLeftComponent;
    private Component clickSequenzRightComponent;
    private Component clickSequenzEmptyComponent;
    private Component clickSequenzSpacerComponent;

    public WizardConfig(@NotNull JavaPlugin plugin) {
        super(plugin, "config");
    }

    @Override
    public void onLoad(FileConfiguration config) {

        this.clickTimeout = config.getLong("clickSequenz.timeout");

        this.clickSequenzSoundEnabled = config.getBoolean("clickSequenz.sound.enabled");
        this.clickSequenzSoundType = config.getString("clickSequenz.sound.type");
        this.clickSequenzSoundVolume = (float) config.getDouble("clickSequenz.sound.volume");
        this.clickSequenzSoundPitch = (float) config.getDouble("clickSequenz.sound.pitch");

        this.clickSequenzMessageEnabled = config.getBoolean("clickSequenz.message.enabled");
        this.clickSequenzType = config.getString("clickSequenz.message.type");
        this.clickSequenzLeftComponent = Lang.parse(config.getString("clickSequenz.message.components.left"));
        this.clickSequenzRightComponent= Lang.parse(config.getString("clickSequenz.message.components.right"));
        this.clickSequenzEmptyComponent = Lang.parse(config.getString("clickSequenz.message.components.empty"));
        this.clickSequenzSpacerComponent = Lang.parse(config.getString("clickSequenz.message.components.spacer"));
    }

    @Override
    public void onPreSave(FileConfiguration config) {

    }

    public long getClickTimeout() {
        return clickTimeout;
    }

    public boolean isClickSequenzSoundEnabled() {
        return clickSequenzSoundEnabled;
    }

    public String getClickSequenzSoundType() {
        return clickSequenzSoundType;
    }

    public float getClickSequenzSoundVolume() {
        return clickSequenzSoundVolume;
    }

    public float getClickSequenzSoundPitch() {
        return clickSequenzSoundPitch;
    }

    public boolean isClickSequenzMessageEnabled() {
        return clickSequenzMessageEnabled;
    }

    public String getClickSequenzType() {
        return clickSequenzType;
    }

    public Component getClickSequenzLeftComponent() {
        return clickSequenzLeftComponent;
    }

    public  Component getClickSequenzRightComponent() {
        return clickSequenzRightComponent;
    }

    public Component getClickSequenzEmptyComponent() {
        return clickSequenzEmptyComponent;
    }

    public Component getClickSequenzSpacerComponent() {
        return clickSequenzSpacerComponent;
    }
}
