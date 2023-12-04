package io.github.dukepigron.motion.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Vector;
import org.bukkit.entity.Entity;
import static org.bukkit.Bukkit.selectEntities;

import static net.kyori.adventure.text.Component.text;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;

public class MotionCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(args.length > 1) {
            //Checks whether target selectors are valid
            try {
                List<Entity> entities = selectEntities(sender, args[0]);

                if(args[1].equals("get")){
                    //Checks that only one entity is being retrieved then either ends or retrieves said entity
                    if(entities.size() > 1){
                        sender.sendMessage(text("Only one entity is allowed").color(NamedTextColor.RED));
                        return true;
                    }
                    Vector vel = entities.get(0).getVelocity();

                    if(args.length == 2){
                        //Returns full velocity vector
                        sender.sendMessage("[" + vel.getX() + ", " + vel.getY() + ", " + vel.getZ() + "]");
                    } else if (args.length == 3){
                        //Returns velocity in a certain axis
                        switch(args[2].toLowerCase()){
                            case "x":
                                sender.sendMessage(Double.toString(vel.getX()));
                                break;
                            case "y":
                                sender.sendMessage(Double.toString(vel.getY()));
                                break;
                            case "z":
                                sender.sendMessage(Double.toString(vel.getZ()));
                                break;
                            default:
                                //Checks whether a valid axis was given
                                sender.sendMessage(text("Argument must be 'x', 'y', or 'z'").color(NamedTextColor.RED));
                                break;
                        }
                    } else {
                        //Checks if sender has too many arguments
                        sender.sendMessage(text("Incomplete or incorrect argument(s) for this command").color(NamedTextColor.RED));
                    }
                } else if(args[1].equals("set")){
                    //Sets velocity for each of the selected entities
                    for(Entity entity: entities) {
                        Vector vel = entity.getVelocity();

                        //Checks whether the sender is setting the entire velocity vector
                        if (args.length == 5){
                            try {
                                //Sets velocity vector
                                double x = Double.parseDouble(args[2]);
                                double y = Double.parseDouble(args[3]);
                                double z = Double.parseDouble(args[4]);

                                entity.setVelocity(new Vector(x, y, z));
                            } catch (NumberFormatException ex) {
                                sender.sendMessage(text("Incomplete or incorrect argument(s) for this command\nMust contain either 3 number values or an axis and a single number value").color(NamedTextColor.RED));
                            }
                        } else if (args.length < 5){
                            //Sets velocity in a certain axis
                            if (args.length < 4)
                                sender.sendMessage(text("Incomplete command").color(NamedTextColor.RED));
                            switch (args[2].toLowerCase()) {
                                case "x":
                                    try {
                                        entity.setVelocity(vel.setX(Double.parseDouble(args[3])));
                                    } catch (NumberFormatException exception) {
                                        sender.sendMessage(text("Given value is not a number").color(NamedTextColor.RED));
                                    }
                                    break;
                                case "y":
                                    try {
                                        entity.setVelocity(vel.setY(Double.parseDouble(args[3])));
                                    } catch (NumberFormatException exception) {
                                        sender.sendMessage(text("Given value is not a number").color(NamedTextColor.RED));
                                    }
                                    break;
                                case "z":
                                    try {
                                        entity.setVelocity(vel.setZ(Double.parseDouble(args[3])));
                                    } catch (NumberFormatException exception) {
                                        sender.sendMessage(text("Given value is not a number").color(NamedTextColor.RED));
                                    }
                                    break;
                                default:
                                    //Checks whether a valid value was given for each axis specified
                                    sender.sendMessage(text("Expected 3 numbers or an axis and a single number").color(NamedTextColor.RED));
                                    break;
                            }
                        } else {
                            //
                            sender.sendMessage(text("Expected 3 numbers or an axis and a single number").color(NamedTextColor.RED));
                        }
                    }
                } else {
                    //Checks if sender doesn't have set or get argument in correct place
                    sender.sendMessage(text("Incomplete or incorrect argument(s) for this command").color(NamedTextColor.RED));
                }
            } catch (IllegalArgumentException ex) {
                //Checks whether the selector is valid
                sender.sendMessage(text("Given value is not an entity").color(NamedTextColor.RED));
            }
        } else return false;

        return true;
    }
}
