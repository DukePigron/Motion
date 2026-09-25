package io.github.dukepigron.motion;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIPaperConfig;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;

public final class MotionBootstrap implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context){
        CommandAPI.onLoad(
                new CommandAPIPaperConfig(context)
        );

        Motion.registerCommands();
    }
}
