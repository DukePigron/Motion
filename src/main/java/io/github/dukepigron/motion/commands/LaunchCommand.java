package io.github.dukepigron.motion.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.*;
import dev.jorel.commandapi.executors.ExecutorType;
import dev.jorel.commandapi.wrappers.Rotation;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.entity.Entity;

import java.util.ArrayList;

public class LaunchCommand {

    //CommandAPI already checks that inputs are not null
    @SuppressWarnings("unchecked")
    public void registerLaunchCommand(){
        new CommandTree("launch")
                .withPermission(CommandPermission.OP)
                //Add adds the velocity to the targets while set sets their velocity to the value and direction
                .then(new MultiLiteralArgument("operation", "set", "add")

                        .then(new EntitySelectorArgument.ManyEntities("target")

                                .then(new DoubleArgument("speed")

                                        .then(new LiteralArgument("from")

                                                .then(new LiteralArgument("entity")

                                                        .then(new EntitySelectorArgument.OneEntity("targetSource")

                                                                .executes((sender, args) -> {
                                                                    ArrayList<Entity> target = (ArrayList<Entity>) args.get("target");
                                                                    String operation = (String) args.get("operation");
                                                                    double speed = (double) args.get("speed");
                                                                    float yaw = ((Entity) args.get("targetSource")).getYaw();
                                                                    float pitch = ((Entity) args.get("targetSource")).getPitch();
                                                                    launch(target, operation, speed, yaw, pitch);
                                                                }, ExecutorType.ALL)
                                                        )
                                                )
                                        )
                                        .then(new LiteralArgument("value")

                                                .then(new RotationArgument("direction")

                                                        .executes((sender, args) -> {
                                                            ArrayList<Entity> target = (ArrayList<Entity>) args.get("target");
                                                            String operation = (String) args.get("operation");
                                                            double speed = (double) args.get("speed");
                                                            Rotation direction = (Rotation) args.get("direction");
                                                            launch(target, operation, speed, direction.getNormalizedYaw(), direction.getNormalizedPitch());
                                                        }, ExecutorType.ALL)
                                                )
                                        )
                                )
                        )
                )
                .register();
    }

    public void launch(ArrayList<Entity> entities, String operation, double speed, float yaw, float pitch){
        Vector newVel = ((new Location(entities.get(0).getWorld(), 0, 0, 0, yaw, pitch)).getDirection()).multiply(speed);

        for(Entity entity : entities){
            Vector vel = newVel;
            if(operation.equals("add"))
                vel.add(entity.getVelocity());
            entity.setVelocity(vel);
        }
    }
}
