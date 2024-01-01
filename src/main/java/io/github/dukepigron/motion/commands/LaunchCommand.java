package io.github.dukepigron.motion.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.*;
import dev.jorel.commandapi.executors.ExecutorType;
import dev.jorel.commandapi.wrappers.Rotation;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Objective;

import java.util.ArrayList;

public class LaunchCommand {

    //CommandAPI already checks that inputs are not null
    @SuppressWarnings("unchecked")
    public void registerLaunchCommand(){
        new CommandTree("launch")
                .withPermission(CommandPermission.OP)
                //Add adds the velocity to the targets while set sets their velocity to the value and direction
                .then(new MultiLiteralArgument("operation", "set", "add")
                        //The target that will be launched
                        .then(new EntitySelectorArgument.ManyEntities("target")
                                //Takes the rotation value from a source
                                .then(new LiteralArgument("from")
                                        //Takes the rotation value from an entity
                                        .then(new LiteralArgument("entity")
                                                //The entity whose rotation will be used
                                                .then(new EntitySelectorArgument.OneEntity("targetSource")
                                                        //Takes the speed value from a source
                                                        .then(new LiteralArgument("from")

                                                                .then(new LiteralArgument("score")

                                                                        .then(new ScoreHolderArgument.Single("scoreholder")

                                                                                .then(new ObjectiveArgument("objective")

                                                                                        .then(new DoubleArgument("scale").setOptional(true)
                                                                                                .executes((sender, args) -> {
                                                                                                    ArrayList<Entity> target = (ArrayList<Entity>) args.get("target");
                                                                                                    String operation = (String) args.get("operation");
                                                                                                    float yaw = ((Entity) args.get("targetSource")).getYaw();
                                                                                                    float pitch = ((Entity) args.get("targetSource")).getPitch();

                                                                                                    String scoreholder = (String) args.get("scoreholder");
                                                                                                    Objective objective = (Objective) args.get("objective");
                                                                                                    double scale = args.get("scale") == null ? 1 : (double) args.get("scale");

                                                                                                    double speed = ((double) objective.getScore(scoreholder).getScore())*scale;
                                                                                                    launch(target, operation, speed, yaw, pitch);
                                                                                                }, ExecutorType.ALL)
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )

                                                        //Takes a value for the speed
                                                        .then(new LiteralArgument("value")

                                                                .then(new DoubleArgument("speed")

                                                                        .executes((sender, args) -> {
                                                                            ArrayList<Entity> target = (ArrayList<Entity>) args.get("target");
                                                                            String operation = (String) args.get("operation");
                                                                            float yaw = ((Entity) args.get("targetSource")).getYaw();
                                                                            float pitch = ((Entity) args.get("targetSource")).getPitch();
                                                                            double speed = (double) args.get("speed");
                                                                            launch(target, operation, speed, yaw, pitch);
                                                                        }, ExecutorType.ALL)
                                                                )
                                                        )
                                                )
                                        )
                                        .then(new LiteralArgument("score")
                                                //The scoreholder whose scores will be used for the yaw and pitch
                                                .then(new ScoreHolderArgument.Single("scoreholder1")

                                                        .then(new ObjectiveArgument("yawObjective")

                                                                .then(new ObjectiveArgument("pitchObjective")

                                                                        .then(new DoubleArgument("rotationScale")

                                                                                .then(new LiteralArgument("from")

                                                                                        .then(new LiteralArgument("score")

                                                                                                .then(new ScoreHolderArgument.Single("scoreholder2")

                                                                                                        .then(new ObjectiveArgument("objective")

                                                                                                                .then(new DoubleArgument("scale").setOptional(true)
                                                                                                                        .executes((sender, args) -> {
                                                                                                                            ArrayList<Entity> target = (ArrayList<Entity>) args.get("target");
                                                                                                                            String operation = (String) args.get("operation");

                                                                                                                            Objective obj1 = (Objective) args.get("yawObjective");
                                                                                                                            Objective obj2 = (Objective) args.get("pitchObjective");
                                                                                                                            String rotationHolder = (String) args.get("scoreholder1");
                                                                                                                            double rotationScale = args.get("rotationScale") == null ? 1 : (double) args.get("rotationScale");
                                                                                                                            float yaw = (float) (obj1.getScore(rotationHolder).getScore() * rotationScale);
                                                                                                                            float pitch = (float) (obj2.getScore(rotationHolder).getScore() * rotationScale);

                                                                                                                            String scoreholder = (String) args.get("scoreholder2");
                                                                                                                            Objective objective = (Objective) args.get("objective");
                                                                                                                            double scale = args.get("scale") == null ? 1 : (double) args.get("scale");

                                                                                                                            double speed = ((double) objective.getScore(scoreholder).getScore())*scale;
                                                                                                                            launch(target, operation, speed, yaw, pitch);
                                                                                                                        }, ExecutorType.ALL)
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )

                                                                                //Takes a value for the speed
                                                                                .then(new LiteralArgument("value")

                                                                                        .then(new DoubleArgument("speed")

                                                                                                .executes((sender, args) -> {
                                                                                                    ArrayList<Entity> target = (ArrayList<Entity>) args.get("target");
                                                                                                    String operation = (String) args.get("operation");

                                                                                                    Objective obj1 = (Objective) args.get("yawObjective");
                                                                                                    Objective obj2 = (Objective) args.get("pitchObjective");
                                                                                                    String rotationHolder = (String) args.get("scoreholder1");
                                                                                                    double rotationScale = args.get("rotationScale") == null ? 1 : (double) args.get("rotationScale");
                                                                                                    float yaw = (float) (obj1.getScore(rotationHolder).getScore() * rotationScale);
                                                                                                    float pitch = (float) (obj2.getScore(rotationHolder).getScore() * rotationScale);

                                                                                                    double speed = (double) args.get("speed");
                                                                                                    launch(target, operation, speed, yaw, pitch);
                                                                                                }, ExecutorType.ALL)
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                                .then(new LiteralArgument("value")

                                        .then(new RotationArgument("direction")

                                                //Takes a value for the speed
                                                .then(new LiteralArgument("value")

                                                        .then(new DoubleArgument("speed")

                                                                .executes((sender, args) -> {
                                                                    ArrayList<Entity> target = (ArrayList<Entity>) args.get("target");
                                                                    String operation = (String) args.get("operation");
                                                                    float yaw = ((Rotation) args.get("direction")).getNormalizedYaw();
                                                                    float pitch = ((Rotation) args.get("direction")).getNormalizedPitch();
                                                                    double speed = (double) args.get("speed");
                                                                    launch(target, operation, speed, yaw, pitch);
                                                                }, ExecutorType.ALL)
                                                        )
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
