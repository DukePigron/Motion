package io.github.dukepigron.motion.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.*;

import dev.jorel.commandapi.executors.CommandExecutor;
import dev.jorel.commandapi.executors.ExecutorType;
import dev.jorel.commandapi.executors.ProxyCommandExecutor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Objective;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class MotionCommand {

    //CommandAPI already checks that inputs are not null
    @SuppressWarnings("unchecked")
    public void registerMotionCommand(){
        new CommandTree("motion")
                .withPermission(CommandPermission.OP)

                //Branches for set and add operators of command
                //They both either take in a vector or a single double
                .then(new MultiLiteralArgument("operations", "set", "add")

                        .then(new EntitySelectorArgument.ManyEntities("targets")

                                //Lets sender set/add value manually
                                .then(new LiteralArgument("value")
                                        .then(new LocationArgument("vector", LocationType.PRECISE_POSITION, false)
                                            // Branch for taking a vector as input
                                            .executes((sender, args) -> {

                                                ArrayList<Entity> targets = (ArrayList<Entity>) args.get("targets");
                                                Vector vector = ((Location) args.get("vector")).toVector();

                                                changeVelocity(targets, (String) args.get("operations"), vector);
                                            }, ExecutorType.ALL)
                                        )
                                )

                                //Lets sender set/add value from a source
                                .then(new LiteralArgument("from")

                                        //Not necessary but keeps command syntax consistent
                                        .then(new LiteralArgument("entity")

                                                //The entity whose motion is the value being set/added
                                                .then(new EntitySelectorArgument.OneEntity("targetSource")

                                                        .executes((sender, args) -> {
                                                            ArrayList<Entity> entities = (ArrayList<Entity>) args.get("targets");
                                                            Entity targetSource = (Entity) args.get("targetSource");

                                                            changeVelocity(entities, (String) args.get("operations"), targetSource.getVelocity());
                                                        }, ExecutorType.ALL)
                                                )
                                        )
                                )

                                .then(new MultiLiteralArgument("axis", "x", "y", "z")

                                        //The source that the motion value is take from (a number)
                                        .then(new LiteralArgument("value")

                                                .then(new DoubleArgument("amount")
                                                        //Sets or adds the velocity in the specified axis
                                                        .executes((sender, args) -> {
                                                            ArrayList<Entity> entities = (ArrayList<Entity>) args.get("targets");
                                                            double amount = (double) args.get("amount");

                                                            changeVelocity(entities, (String) args.get("operations"), (String) args.get("axis"), amount);
                                                        }, ExecutorType.ALL)
                                                )
                                        )

                                        .then(new LiteralArgument("from")
                                                //The source that the motion value is take from (a scoreboard value)
                                                .then(new LiteralArgument("score")
                                                        //The scoreholder whose score will be used to change the velocity
                                                        .then(new ScoreHolderArgument.Single("scoreholder")

                                                                .then(new ObjectiveArgument("objective")

                                                                        .then(new DoubleArgument("scale").setOptional(true)
                                                                                .executes((sender, args) -> {
                                                                                    ArrayList<Entity> entities = (ArrayList<Entity>) args.get("targets");
                                                                                    String scoreholder = (String) args.get("scoreholder");
                                                                                    Objective objective = (Objective) args.get("objective");
                                                                                    double amount = args.get("scale") == null ? 1 : (double) args.get("scale");
                                                                                    amount *= objective.getScore(scoreholder).getScore();

                                                                                    changeVelocity(entities, (String) args.get("operations"), (String) args.get("axis"), amount);
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

                                //Axis of motion being stored (only stores a single double from the velocity vector)
                                .then(new MultiLiteralArgument("axis", "x", "y", "z")

                                        //Scoreholder whose score is being set to the motion value
                                        .then(new ScoreHolderArgument.Multiple("scoreholder")

                                                //Objective that the motion value is being stored in
                                                .then(new ObjectiveArgument("objective")

                                                        //Optional argument for scaling the value
                                                        .then(new DoubleArgument("scale").setOptional(true)
                                                                .executes((sender, args) -> {
                                                                    Entity entity = (Entity) args.get("target");
                                                                    String scoreholder = (String) args.get("scoreholder");
                                                                    Objective objective = (Objective) args.get("objective");
                                                                    double amount = args.get("scale") == null ? 1 : (double) args.get("scale");

                                                                    //sets vel to the velocity value of the axis specified
                                                                    double vel = 0;
                                                                    switch((String) args.get("axis")) {
                                                                        case "x":
                                                                            vel = entity.getVelocity().getX();
                                                                            break;
                                                                        case "y":
                                                                            vel = entity.getVelocity().getY();
                                                                            break;
                                                                        case "z":
                                                                            vel = entity.getVelocity().getZ();
                                                                            break;
                                                                    }

                                                                    amount *= vel;

                                                                    objective.getScore(scoreholder).setScore((int) amount);
                                                                }, ExecutorType.ALL)
                                                        )
                                                )
                                        )
                                )
                        )
                )

                //Get argument for returning velocity vectors or doubles
                .then(new LiteralArgument("get")

                        .then(new EntitySelectorArgument.OneEntity("target")

                                .then(new MultiLiteralArgument("axis", "x", "y", "z").setOptional(true)
                                        .executes((sender, args) -> {
                                            Entity entity = (Entity) args.get("target");
                                            Vector vel = entity.getVelocity();

                                            //Returns velocity vector from the target if no axis is given
                                            if(args.get("axis") == null){
                                                sender.sendMessage(((Entity) args.get("target")).getVelocity().toString());
                                            } else {

                                                //Returns the velocity in the axis specified as a double
                                                switch ((String) args.get("axis")) {
                                                    case "x":
                                                        sender.sendMessage(Double.toString(vel.getX()));
                                                        break;
                                                    case "y":
                                                        sender.sendMessage(Double.toString(vel.getY()));
                                                        //return vel.getY();
                                                        break;
                                                    case "z":
                                                        sender.sendMessage(Double.toString(vel.getZ()));
                                                        break;

                                                }

                                            }

                                        }, ExecutorType.ALL)
                                )
                        )
                )
                .register();
    }

    //Adds/sets the entities velocities by the amount given
    public void changeVelocity(ArrayList<Entity> entities, String operation, Vector vector){

        switch(operation){
            case "add":
                for(Entity entity : entities){
                    entity.setVelocity(entity.getVelocity().add(vector));
                }
                break;
            case "set":
                for(Entity entity : entities){
                    entity.setVelocity(vector);
                }
                break;
        }

    }

    //Same as the one that uses a vector but for a single axis of motion
    public void changeVelocity(ArrayList<Entity> entities, String operation, String axis, double value){

        //Creates a vector with two axes set to 0 and the one specified set to the value given
        Vector vector = new Vector();
        switch(axis){
            case "x":
                vector.setX(value);
                break;
            case "y":
                vector.setY(value);
                break;
            case "z":
                vector.setZ(value);
                break;
        }

        for(Entity entity : entities){

            switch(operation){
                case "add":
                    //If the operation is add, adds the vectors
                    entity.setVelocity(entity.getVelocity().add(vector));
                    break;
                case "set":
                    //If the operation is set, sets the entities current motion values to the new vector
                    if(vector.getX() == 0)
                        vector.setX(entity.getVelocity().getX());
                    if(vector.getY() == 0)
                        vector.setY(entity.getVelocity().getY());
                    if(vector.getX() == 0)
                        vector.setZ(entity.getVelocity().getZ());
                    entity.setVelocity(vector);
                    break;
            }
        }

    }

}
