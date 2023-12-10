package io.github.dukepigron.motion.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.*;

import dev.jorel.commandapi.executors.ExecutorType;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Objective;

import java.util.ArrayList;
import java.util.Collection;

public class PositionCommand {
    //CommandAPI already checks that inputs are not null
    @SuppressWarnings("unchecked")
    public void registerPositionCommand(){
        new CommandTree("position")
                .withAliases("pos", "location")
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

                                                    changePosition(targets, (String) args.get("operations"), vector);
                                                }, ExecutorType.ALL)
                                        )
                                )

                                //Lets sender set/add value from a source
                                .then(new LiteralArgument("from")

                                        //Not necessary but keeps command syntax consistent
                                        .then(new LiteralArgument("entity")

                                                //The entity whose position is the value being set/added
                                                .then(new EntitySelectorArgument.OneEntity("targetSource")

                                                        .executes((sender, args) -> {
                                                            ArrayList<Entity> entities = (ArrayList<Entity>) args.get("targets");
                                                            Entity targetSource = (Entity) args.get("targetSource");

                                                            changePosition(entities, (String) args.get("operations"), targetSource.getLocation().toVector());
                                                        }, ExecutorType.ALL)
                                                )
                                        )
                                )

                                .then(new MultiLiteralArgument("axis", "x", "y", "z")

                                        //The source that the position value is take from (a number)
                                        .then(new LiteralArgument("value")

                                                .then(new DoubleArgument("amount")
                                                        //Sets or adds the position in the specified axis
                                                        .executes((sender, args) -> {
                                                            ArrayList<Entity> entities = (ArrayList<Entity>) args.get("targets");
                                                            double amount = (double) args.get("amount");

                                                            changePosition(entities, (String) args.get("operations"), (String) args.get("axis"), amount);
                                                        }, ExecutorType.ALL)
                                                )
                                        )

                                        .then(new LiteralArgument("from")
                                                //The source that the position value is take from (a scoreboard value)
                                                .then(new LiteralArgument("score")
                                                        //The scoreholder whose score will be used to change the position
                                                        .then(new ScoreHolderArgument.Single("scoreholder")

                                                                .then(new ObjectiveArgument("objective")

                                                                        .then(new DoubleArgument("scale").setOptional(true)
                                                                                .executes((sender, args) -> {
                                                                                    ArrayList<Entity> entities = (ArrayList<Entity>) args.get("targets");
                                                                                    String scoreholder = (String) args.get("scoreholder");
                                                                                    Objective objective = (Objective) args.get("objective");
                                                                                    double amount = args.get("scale") == null ? 1 : (double) args.get("scale");
                                                                                    amount *= objective.getScore(scoreholder).getScore();

                                                                                    changePosition(entities, (String) args.get("operations"), (String) args.get("axis"), amount);
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
                        //Entity whose position is being stored
                        .then(new EntitySelectorArgument.OneEntity("target")

                                //Axis of position being stored (only stores a single double from the position vector)
                                .then(new MultiLiteralArgument("axis", "x", "y", "z")

                                        //Scoreholder whose score is being set to the position value
                                        .then(new ScoreHolderArgument.Multiple("scoreholder")

                                                //Objective that the position value is being stored in
                                                .then(new ObjectiveArgument("objective")

                                                        //Optional argument for scaling the value
                                                        .then(new DoubleArgument("scale").setOptional(true)
                                                                .executes((sender, args) -> {
                                                                    Entity entity = (Entity) args.get("target");
                                                                    Collection<String> scoreholders = (Collection<String>) args.get("scoreholder");
                                                                    Objective objective = (Objective) args.get("objective");
                                                                    double amount = args.get("scale") == null ? 1 : (double) args.get("scale");

                                                                    //sets vel to the position value of the axis specified
                                                                    double pos = 0;
                                                                    switch((String) args.get("axis")) {
                                                                        case "x":
                                                                            pos = entity.getLocation().getX();
                                                                            break;
                                                                        case "y":
                                                                            pos = entity.getLocation().getY();
                                                                            break;
                                                                        case "z":
                                                                            pos = entity.getLocation().getZ();
                                                                            break;
                                                                    }

                                                                    amount *= pos;

                                                                    for(String scoreholder : scoreholders){
                                                                        objective.getScore(scoreholder).setScore((int) amount);
                                                                    }
                                                                }, ExecutorType.ALL)
                                                        )
                                                )
                                        )
                                )
                        )
                )

                //Get argument for returning position vectors or doubles
                .then(new LiteralArgument("get")

                        .then(new EntitySelectorArgument.OneEntity("target")

                                .then(new MultiLiteralArgument("axis", "x", "y", "z").setOptional(true)
                                        .executes((sender, args) -> {
                                            Entity entity = (Entity) args.get("target");
                                            Location pos = entity.getLocation();

                                            //Returns position vector from the target if no axis is given
                                            if(args.get("axis") == null){
                                                sender.sendMessage(((Entity) args.get("target")).getLocation().toVector().toString());
                                            } else {

                                                //Returns the position in the axis specified as a double
                                                switch ((String) args.get("axis")) {
                                                    case "x":
                                                        sender.sendMessage(Double.toString(pos.getX()));
                                                        break;
                                                    case "y":
                                                        sender.sendMessage(Double.toString(pos.getY()));
                                                        break;
                                                    case "z":
                                                        sender.sendMessage(Double.toString(pos.getZ()));
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
    public void changePosition(ArrayList<Entity> entities, String operation, Vector vector){

        switch(operation){
            case "add":
                for(Entity entity : entities){
                    entity.teleport(entity.getLocation().add(vector));
                }
                break;
            case "set":
                for(Entity entity : entities){
                    entity.teleport(new Location(entity.getWorld(), vector.getX(), vector.getY(), vector.getZ()));
                }
                break;
        }

    }

    //Same as the one that uses a vector but for a single axis of position
    public void changePosition(ArrayList<Entity> entities, String operation, String axis, double value){

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
                    entity.teleport(entity.getLocation().add(vector));
                    break;
                case "set":
                    //If the operation is set, sets the entities current position values to the new vector
                    if(vector.getX() == 0)
                        vector.setX(entity.getVelocity().getX());
                    if(vector.getY() == 0)
                        vector.setY(entity.getVelocity().getY());
                    if(vector.getX() == 0)
                        vector.setZ(entity.getVelocity().getZ());
                    entity.teleport(new Location(entity.getWorld(), vector.getX(), vector.getY(), vector.getZ()));
                    break;
            }
        }

    }
}
