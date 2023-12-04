package io.github.dukepigron.motion.commands;

import org.bukkit.FluidCollisionMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.kyori.adventure.text.Component.text;
import net.kyori.adventure.text.format.NamedTextColor;

public class MotionTabCompleter implements TabCompleter{
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        ArrayList<String> parameters = new ArrayList<>();

        if(args.length == 1) {
            parameters.add("@a");
            parameters.add("@e");
            parameters.add("@p");
            parameters.add("@r");
            parameters.add("@s");
            if(sender instanceof Player player) {
                //Adds parameter for the player using the command
                parameters.add(sender.getName());
                /*World world = (player).getWorld();
                RayTraceResult ray = world.rayTrace(player.getEyeLocation(), player.getEyeLocation().getDirection(), 4, FluidCollisionMode.NEVER, true, 1, null);

                if(ray != null && ray.getHitEntity() != null)
                    player.sendMessage(ray.getHitEntity().toString());
                //Adds parameter for entity that player is facing
                if(world.rayTrace(player.getEyeLocation(), player.getEyeLocation().getDirection(), 4, FluidCollisionMode.NEVER, true, 2, null) instanceof Entity entity){
                    parameters.add(entity.getUniqueId().toString());
                }*/
            }

            //Removes the parameter what the player typed no longer matches
            parameters.removeIf(arg -> !arg.toLowerCase().startsWith(args[0].toLowerCase()));
            Collections.sort(parameters);
        }
        if(args.length == 2){
            parameters.add("get");
            parameters.add("set");

            //Removes the parameter what the player typed no longer matches
            parameters.removeIf(arg -> !arg.toLowerCase().startsWith(args[1].toLowerCase()));
            Collections.sort(parameters);
        }
        if(args.length == 3){
            parameters.add("x");
            parameters.add("y");
            parameters.add("z");

            //Removes the parameter what the player typed no longer matches
            parameters.removeIf(arg -> !arg.toLowerCase().startsWith(args[2].toLowerCase()));
            Collections.sort(parameters);
        }

        return parameters;
    }
}
