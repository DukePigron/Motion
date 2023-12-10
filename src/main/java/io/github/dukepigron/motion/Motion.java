package io.github.dukepigron.motion;

import io.github.dukepigron.motion.commands.MotionCommand;
//import io.github.dukepigron.motion.commands.MotionTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Motion extends JavaPlugin {
    @Override
    public void onEnable() {
        new MotionCommand().registerMotionCommand();

        Bukkit.getLogger().info("[Motion] Sliding in!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[Motion] Taking off!");
    }

    private void RegisterCommandsAndEvents(){

    }
}
