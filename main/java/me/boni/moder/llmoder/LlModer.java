package me.boni.moder.llmoder;

import me.boni.moder.llmoder.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class LlModer extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("moder").setExecutor(new Moderator());
        getCommand("moder").setTabCompleter(new TCompleter());


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
