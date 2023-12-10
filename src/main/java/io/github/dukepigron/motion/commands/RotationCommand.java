package io.github.dukepigron.motion.commands;

import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.*;
import dev.jorel.commandapi.executors.ExecutorType;
import dev.jorel.commandapi.wrappers.Rotation;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Objective;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;

public class RotationCommand {

    //CommandAPI already checks that inputs are not null
    @SuppressWarnings("unchecked")
    public void registerRotationCommand() {
        new CommandTree("rotation")
                .withPermission(CommandPermission.OP)

                //Branches for set and add operators of command
                //They both either take in a rotation object or a single float
                .then(new MultiLiteralArgument("operations", "set", "add")

                        .then(new EntitySelectorArgument.ManyEntities("targets")

                                //Lets sender set/add value manually
                                .then(new LiteralArgument("value")
                                        .then(new RotationArgument("rotation")
                                                // Branch for taking a rotation object as input
                                                .executes((sender, args) -> {

                                                    ArrayList<Entity> targets = (ArrayList<Entity>) args.get("targets");
                                                    Rotation rotation = (Rotation) args.get("rotation");

                                                    changeRotation(targets, (String) args.get("operations"), rotation.getNormalizedYaw(), rotation.getNormalizedPitch());
                                                }, ExecutorType.ALL)
                                        )
                                )

                                //Lets sender set/add value from a source
                                .then(new LiteralArgument("from")

                                        //Not necessary but keeps command syntax consistent
                                        .then(new LiteralArgument("entity")

                                                //The entity whose rotation is the value being set/added
                                                .then(new EntitySelectorArgument.OneEntity("targetSource")

                                                        .executes((sender, args) -> {
                                                            ArrayList<Entity> entities = (ArrayList<Entity>) args.get("targets");
                                                            Entity targetSource = (Entity) args.get("targetSource");

                                                            changeRotation(entities, (String) args.get("operations"), targetSource.getYaw(), targetSource.getPitch());
                                                        }, ExecutorType.ALL)
                                                )
                                        )
                                )

                                .then(new MultiLiteralArgument("axis", "yaw", "pitch")

                                        //The source that the rotation value is take from (a number)
                                        .then(new LiteralArgument("value")

                                                .then(new FloatArgument("amount")
                                                        //Sets or adds the rotation in the specified axis
                                                        .executes((sender, args) -> {
                                                            ArrayList<Entity> entities = (ArrayList<Entity>) args.get("targets");
                                                            float amount = (float) args.get("amount");

                                                            changeRotation(entities, (String) args.get("operations"), (String) args.get("axis"), amount);
                                                        }, ExecutorType.ALL)
                                                )
                                        )

                                        .then(new LiteralArgument("from")
                                                //The source that the rotation value is take from (a scoreboard value)
                                                .then(new LiteralArgument("score")
                                                        //The scoreholder whose score will be used to change the rotation
                                                        .then(new ScoreHolderArgument.Single("scoreholder")

                                                                .then(new ObjectiveArgument("objective")

                                                                        .then(new DoubleArgument("scale").setOptional(true)
                                                                                .executes((sender, args) -> {
                                                                                    ArrayList<Entity> entities = (ArrayList<Entity>) args.get("targets");
                                                                                    String scoreholder = (String) args.get("scoreholder");
                                                                                    Objective objective = (Objective) args.get("objective");
                                                                                    float amount = args.get("scale") == null ? 1 : (float) args.get("scale");
                                                                                    amount *= objective.getScore(scoreholder).getScore();

                                                                                    changeRotation(entities, (String) args.get("operations"), (String) args.get("axis"), amount);
                                                                                }, ExecutorType.ALL)
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )

                //Branch for store operator, stores values in scoreboards
                .then(new LiteralArgument("store")
                        //Entity whose motion is being stored
                        .then(new EntitySelectorArgument.OneEntity("target")

                                //Axis of motion being stored (only stores a single float from the rotation)
                                .then(new MultiLiteralArgument("axis", "yaw", "pitch")

                                        //Scoreholder whose score is being set to the rotation value
                                        .then(new ScoreHolderArgument.Multiple("scoreholder")

                                                //Objective that the rotation value is being stored in
                                                .then(new ObjectiveArgument("objective")

                                                        //Optional argument for scaling the value
                                                        .then(new DoubleArgument("scale").setOptional(true)
                                                                .executes((sender, args) -> {
                                                                    Entity entity = (Entity) args.get("target");
                                                                    Collection<String> scoreholders = (Collection<String>) args.get("scoreholder");
                                                                    Objective objective = (Objective) args.get("objective");
                                                                    double amount = args.get("scale") == null ? 1 : (double) args.get("scale");

                                                                    //sets angle to the rotation value of the axis specified
                                                                    double angle = 0;
                                                                    switch ((String) args.get("axis")) {
                                                                        case "yaw":
                                                                            angle = entity.getYaw();
                                                                            break;
                                                                        case "pitch":
                                                                            angle = entity.getPitch();
                                                                            break;
                                                                    }

                                                                    amount *= angle;

                                                                    for (String scoreholder : scoreholders) {
                                                                        objective.getScore(scoreholder).setScore((int) amount);
                                                                    }
                                                                }, ExecutorType.ALL)
                                                        )
                                                )
                                        )
                                )
                        )
                )

                //Get argument for returning rotation values or floats
                .then(new LiteralArgument("get")

                        .then(new EntitySelectorArgument.OneEntity("target")

                                .then(new MultiLiteralArgument("axis", "yaw", "pitch").setOptional(true)
                                        .executes((sender, args) -> {
                                            Entity entity = (Entity) args.get("target");
                                            float yaw = entity.getYaw();
                                            float pitch = entity.getPitch();

                                            //Returns pitch and yaw from the target if no axis is given
                                            if (args.get("axis") == null) {
                                                sender.sendMessage("[" + yaw + ", " + pitch + "]");
                                            } else {

                                                //Returns the velocity in the axis specified as a float
                                                switch ((String) args.get("axis")) {
                                                    case "yaw":
                                                        sender.sendMessage(Float.toString(yaw));
                                                        break;
                                                    case "pitch":
                                                        sender.sendMessage(Float.toString(pitch));
                                                        break;
                                                }

                                            }

                                        }, ExecutorType.ALL)
                                )
                        )
                )
                .register();
    }

    //Adds/sets the entities rotation by the amount given
    public void changeRotation(ArrayList<Entity> entities, String operation, float yaw, float pitch){

        switch(operation){
            case "add":
                for(Entity entity : entities){
                    Location location = entity.getLocation();
                    location.setYaw(entity.getYaw()+yaw);
                    location.setPitch(entity.getPitch()+pitch);
                    entity.teleport(location);
                }
                break;
            case "set":
                for(Entity entity : entities){
                    Location location = entity.getLocation();
                    location.setYaw(yaw);
                    location.setPitch(pitch);
                    entity.teleport(location);
                }
                break;
        }

    }

    //Same as the one that uses a rotation object but for a single axis of rotation
    public void changeRotation(ArrayList<Entity> entities, String operation, String axis, float value){

        //Sets one of the axes to 0 and the other to the value provided
        float yaw = 0f;
        float pitch = 0f;
        switch(axis){
            case "yaw":
                yaw+=value;
                break;
            case "pitch":
                pitch+=value;
                break;
        }

        for(Entity entity : entities){
            Location location = entity.getLocation();

            switch(operation){
                case "add":
                    //If the operation is add, adds the rotations
                    location.setYaw(entity.getYaw()+yaw);
                    location.setPitch(entity.getPitch()+pitch);
                    break;
                case "set":
                    //If the operation is set, sets the entities rotation to the pitch and yaw values stored
                    if(yaw == 0)
                        yaw = entity.getYaw();
                    if(pitch == 0)
                        pitch = entity.getPitch();
                    location.setYaw(yaw);
                    location.setPitch(pitch);
                    break;
            }
            entity.teleport(location);
        }

    }
}
