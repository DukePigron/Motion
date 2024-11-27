package io.github.dukepigron.motion.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.*;

import dev.jorel.commandapi.executors.ExecutorType;
import org.bukkit.Location;
import org.bukkit.scoreboard.Objective;
import org.bukkit.entity.Entity;

import java.util.Collection;

public class DistanceCommand {

    //CommandAPI already checks that inputs are not null
    @SuppressWarnings("unchecked")
    public void registerDistanceCommand(){
        new CommandTree("distance")
                .withPermission(CommandPermission.OP)
                //returns the distance to the sender
                .then(new LiteralArgument("get")
                        //First entity
                        .then(new EntitySelectorArgument.OneEntity("target")
                                //Second entity
                                .then(new EntitySelectorArgument.OneEntity("source")
                                        .executes((sender, args) -> {
                                            Location pos1 = ((Entity) args.get("target")).getLocation();
                                            Location pos2 = ((Entity) args.get("source")).getLocation();
                                            double dist = getDistance(pos1, pos2);
                                            sender.sendMessage("Distance: " + dist);
                                        }, ExecutorType.ALL)
                                )
                                .then(new LocationArgument("pos")
                                        .executes((sender, args) -> {
                                            Location pos1 = ((Entity) args.get("target")).getLocation();
                                            Location pos2 = ((Location) args.get("pos"));
                                            double dist = getDistance(pos1, pos2);
                                            sender.sendMessage("Distance: " + dist);
                                        }, ExecutorType.ALL)
                                )
                        )
                )
                //Stores the distance in a scoreboard
                .then(new LiteralArgument("store")
                        //First entity
                        .then(new EntitySelectorArgument.OneEntity("target")
                                //Second entity
                                .then(new EntitySelectorArgument.OneEntity("source")
                                        //Optional choice to only get the distance on a certain axis
                                        //.then(new MultiLiteralArgument("axis", "x", "y", "z").setOptional(true)
                                                //Scoreholder that the distance will be stored to
                                                .then(new ScoreHolderArgument.Multiple("scoreholder")
                                                        //objective that the distance will be stored in
                                                        .then(new ObjectiveArgument("objective")
                                                                //Optional argument for scaling the value
                                                                .then(new DoubleArgument("scale").setOptional(true)
                                                                        .executes((sender, args) -> {
                                                                            Location pos1 = ((Entity) args.get("target")).getLocation();
                                                                            Location pos2 = ((Entity) args.get("source")).getLocation();
                                                                            double scale = args.get("scale") == null ? 1 : (double) args.get("scale");

                                                                            Collection<String> scoreholders = (Collection<String>) args.get("scoreholder");
                                                                            Objective objective = (Objective) args.get("objective");

                                                                            for(String scoreholder : scoreholders){
                                                                                double dist;
                                                                                if(args.get("axis") == null)
                                                                                    dist = getDistance(pos1, pos2)*scale;
                                                                                else
                                                                                    dist = getDistance(pos1, pos2, (String) args.get("axis"))*scale;
                                                                                objective.getScore(scoreholder).setScore((int) dist);
                                                                            }
                                                                        }, ExecutorType.ALL)
                                                                )
                                                        )
                                                )
                                        //)
                                )
                                .then(new LocationArgument("pos")
                                        //Optional choice to only get the distance on a certain axis
                                        //.then(new MultiLiteralArgument("axis", "x", "y", "z").setOptional(true)
                                                //Scoreholder that the distance will be stored to
                                                .then(new ScoreHolderArgument.Multiple("scoreholder")
                                                        //objective that the distance will be stored in
                                                        .then(new ObjectiveArgument("objective")
                                                                //Optional argument for scaling the value
                                                                .then(new DoubleArgument("scale").setOptional(true)
                                                                        .executes((sender, args) -> {
                                                                            Location pos1 = ((Entity) args.get("target")).getLocation();
                                                                            Location pos2 = ((Location) args.get("pos"));
                                                                            double scale = args.get("scale") == null ? 1 : (double) args.get("scale");

                                                                            Collection<String> scoreholders = (Collection<String>) args.get("scoreholder");
                                                                            Objective objective = (Objective) args.get("objective");

                                                                            for(String scoreholder : scoreholders){
                                                                                double dist;
                                                                                if(args.get("axis") == null)
                                                                                    dist = getDistance(pos1, pos2)*scale;
                                                                                else
                                                                                    dist = getDistance(pos1, pos2, (String) args.get("axis"))*scale;
                                                                                objective.getScore(scoreholder).setScore((int) dist);
                                                                            }
                                                                        }, ExecutorType.ALL)
                                                                )
                                                        )
                                                )
                                        //)
                                )
                        )
                )
                .register();
    }

    public double getDistance(Location pos1, Location pos2){
        return pos1.toVector().distance(pos2.toVector());
    }

    public double getDistance(Location pos1, Location pos2, String axis){
        switch(axis){
            case "x":
                pos1.setY(0);
                pos1.setZ(0);

                pos2.setY(0);
                pos2.setZ(0);
                break;
            case "y":
                pos1.setX(0);
                pos1.setZ(0);

                pos2.setX(0);
                pos2.setZ(0);
                break;
            case "z":
                pos1.setX(0);
                pos1.setY(0);

                pos2.setX(0);
                pos2.setY(0);
                break;
        }
        return getDistance(pos1, pos2);
    }

}
