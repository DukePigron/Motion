package io.github.dukepigron.motion;

import io.github.dukepigron.motion.commands.MotionCommand;
import io.github.dukepigron.motion.commands.MotionTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Motion extends JavaPlugin {
    @Override
    public void onEnable() {
        RegisterCommandsAndEvents();

        Bukkit.getLogger().info("[Motion] Sliding in!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[Motion] Taking off!");
    }

    private void RegisterCommandsAndEvents(){
        getCommand("motion").setExecutor(new MotionCommand());
        getCommand("motion").setTabCompleter(new MotionTabCompleter());
    }
}
