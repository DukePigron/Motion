package io.github.dukepigron.motion;

import io.github.dukepigron.motion.commands.MotionCommand;
import io.github.dukepigron.motion.commands.PositionCommand;
import io.github.dukepigron.motion.commands.RotationCommand;
import io.github.dukepigron.motion.commands.LookAtCommand;
import io.github.dukepigron.motion.commands.LaunchCommand;
import io.github.dukepigron.motion.commands.DistanceCommand;
import io.github.dukepigron.motion.commands.ExplodeCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Motion extends JavaPlugin {
    @Override
    public void onEnable() {
        new MotionCommand().registerMotionCommand();
        new PositionCommand().registerPositionCommand();
        new RotationCommand().registerRotationCommand();
        new LookAtCommand().registerLookAtCommand();
        new LaunchCommand().registerLaunchCommand();
        new DistanceCommand().registerDistanceCommand();
        new ExplodeCommand().registerExplodeCommand();

        Bukkit.getLogger().info("[Motion] Sliding in!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[Motion] Taking off!");
    }

    private void RegisterCommandsAndEvents(){

    }
}
