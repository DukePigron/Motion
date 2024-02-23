package io.github.dukepigron.motion.commands;
import io.github.dukepigron.motion.commands.DistanceCommand;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.*;
import dev.jorel.commandapi.executors.ExecutorType;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Objective;

import java.util.ArrayList;

//Simulates an explosion-like effect (no particles)
//More customizeable blast radius, power, and power fall-off
public class ExplodeCommand {

    //CommandAPI already checks that inputs are not null
    @SuppressWarnings("unchecked")
    public void registerExplodeCommand() {
        new CommandTree("explode")
                .withPermission(CommandPermission.OP)
                //"center" refers to the center of the explosion
                .then(new EntitySelectorArgument.OneEntity("center")
                        //Entities affected by the explosion
                        .then(new EntitySelectorArgument.ManyEntities("targets")
                                //Literal arguments determine where the values come from (for radius, minPower, maxPower)
                                //scoreholders and objectives refer to scoreboards to retrieve values from
                                //scale scales the value of the score
                                //Radius is the explosion radius
                                //minPower is the minimum velocity it could launch entities
                                //maxPower is the maximum velocity it could launch entities
                                .then(new LiteralArgument("value")
                                        .then(new DoubleArgument("radius")
                                                .then(new LiteralArgument("value")
                                                        .then(new DoubleArgument("minPower")
                                                                .then(new LiteralArgument("value")
                                                                        .then(new DoubleArgument("maxPower")
                                                                                .then(new MultiLiteralArgument("fallOffType", "linear").setOptional(true)
                                                                                        .executes((sender, args) -> {
                                                                                            Entity center = ((Entity) args.get("center"));
                                                                                            ArrayList<Entity> targets = ((ArrayList<Entity>) args.get("targets"));
                                                                                            double radius = ((double) args.get("radius"));
                                                                                            double minPower = ((double) args.get("minPower"));
                                                                                            double maxPower = ((double) args.get("maxPower"));
                                                                                            String fallOffType = args.get("fallOffType") == null ? "LINEAR" : ((String) args.get("fallOffType")).toUpperCase();

                                                                                            blast(center, targets, radius, maxPower, minPower, fallOffType);
                                                                                        }, ExecutorType.ALL)
                                                                                )
                                                                        )
                                                                )
                                                                .then(new LiteralArgument("score")
                                                                        .then(new ScoreHolderArgument.Single("scoreholder3")
                                                                                .then(new ObjectiveArgument("objective3")
                                                                                        .then(new DoubleArgument("scale3")
                                                                                                .then(new DoubleArgument("maxPower")
                                                                                                        .then(new MultiLiteralArgument("fallOffType", "linear").setOptional(true)
                                                                                                                .executes((sender, args) -> {
                                                                                                                    Entity center = ((Entity) args.get("center"));
                                                                                                                    ArrayList<Entity> targets = ((ArrayList<Entity>) args.get("targets"));
                                                                                                                    double radius = ((double) args.get("radius"));
                                                                                                                    double minPower = ((double) args.get("minPower"));

                                                                                                                    String scoreholder3 = (String) args.get("scoreholder3");
                                                                                                                    Objective objective3 = (Objective) args.get("objective3");
                                                                                                                    double scale3 = (double) args.get("scale3");
                                                                                                                    double maxPower = ((double) objective3.getScore(scoreholder3).getScore())*scale3;

                                                                                                                    String fallOffType = args.get("fallOffType") == null ? "LINEAR" : ((String) args.get("fallOffType")).toUpperCase();

                                                                                                                    blast(center, targets, radius, maxPower, minPower, fallOffType);
                                                                                                                }, ExecutorType.ALL)
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                                .then(new LiteralArgument("score")
                                                        .then(new ScoreHolderArgument.Single("scoreholder2")
                                                                .then(new ObjectiveArgument("objective2")
                                                                        .then(new DoubleArgument("scale2")
                                                                                .then(new LiteralArgument("value")
                                                                                        .then(new DoubleArgument("maxPower")
                                                                                                .then(new MultiLiteralArgument("fallOffType", "linear").setOptional(true)
                                                                                                        .executes((sender, args) -> {
                                                                                                            Entity center = ((Entity) args.get("center"));
                                                                                                            ArrayList<Entity> targets = ((ArrayList<Entity>) args.get("targets"));
                                                                                                            double radius = ((double) args.get("radius"));

                                                                                                            String scoreholder2 = (String) args.get("scoreholder2");
                                                                                                            Objective objective2 = (Objective) args.get("objective2");
                                                                                                            double scale2 = (double) args.get("scale2");
                                                                                                            double minPower = ((double) objective2.getScore(scoreholder2).getScore())*scale2;

                                                                                                            double maxPower = ((double) args.get("maxPower"));
                                                                                                            String fallOffType = args.get("fallOffType") == null ? "LINEAR" : ((String) args.get("fallOffType")).toUpperCase();

                                                                                                            blast(center, targets, radius, maxPower, minPower, fallOffType);
                                                                                                        }, ExecutorType.ALL)
                                                                                                )
                                                                                        )
                                                                                )
                                                                                .then(new LiteralArgument("score")
                                                                                        .then(new ScoreHolderArgument.Single("scoreholder3")
                                                                                                .then(new ObjectiveArgument("objective3")
                                                                                                        .then(new DoubleArgument("scale3")
                                                                                                                .then(new DoubleArgument("maxPower")
                                                                                                                        .then(new MultiLiteralArgument("fallOffType", "linear").setOptional(true)
                                                                                                                                .executes((sender, args) -> {
                                                                                                                                    Entity center = ((Entity) args.get("center"));
                                                                                                                                    ArrayList<Entity> targets = ((ArrayList<Entity>) args.get("targets"));
                                                                                                                                    double radius = ((double) args.get("radius"));

                                                                                                                                    String scoreholder2 = (String) args.get("scoreholder2");
                                                                                                                                    Objective objective2 = (Objective) args.get("objective2");
                                                                                                                                    double scale2 = (double) args.get("scale2");
                                                                                                                                    double minPower = ((double) objective2.getScore(scoreholder2).getScore())*scale2;

                                                                                                                                    String scoreholder3 = (String) args.get("scoreholder3");
                                                                                                                                    Objective objective3 = (Objective) args.get("objective3");
                                                                                                                                    double scale3 = (double) args.get("scale3");
                                                                                                                                    double maxPower = ((double) objective3.getScore(scoreholder3).getScore())*scale3;

                                                                                                                                    String fallOffType = args.get("fallOffType") == null ? "LINEAR" : ((String) args.get("fallOffType")).toUpperCase();

                                                                                                                                    blast(center, targets, radius, maxPower, minPower, fallOffType);
                                                                                                                                }, ExecutorType.ALL)
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                                .then(new LiteralArgument("score")
                                        .then(new ScoreHolderArgument.Single("scoreholder1")
                                                .then(new ObjectiveArgument("objective1")
                                                        .then(new DoubleArgument("scale1")
                                                                .then(new LiteralArgument("value")
                                                                        .then(new DoubleArgument("minPower")
                                                                                .then(new LiteralArgument("value")
                                                                                        .then(new DoubleArgument("maxPower")
                                                                                                .then(new MultiLiteralArgument("fallOffType", "linear").setOptional(true)
                                                                                                        .executes((sender, args) -> {
                                                                                                            Entity center = ((Entity) args.get("center"));
                                                                                                            ArrayList<Entity> targets = ((ArrayList<Entity>) args.get("targets"));

                                                                                                            String scoreholder1 = (String) args.get("scoreholder1");
                                                                                                            Objective objective1 = (Objective) args.get("objective1");
                                                                                                            double scale1 = (double) args.get("scale1");
                                                                                                            double radius = ((double) objective1.getScore(scoreholder1).getScore())*scale1;

                                                                                                            double minPower = ((double) args.get("minPower"));
                                                                                                            double maxPower = ((double) args.get("maxPower"));
                                                                                                            String fallOffType = args.get("fallOffType") == null ? "LINEAR" : ((String) args.get("fallOffType")).toUpperCase();

                                                                                                            blast(center, targets, radius, maxPower, minPower, fallOffType);
                                                                                                        }, ExecutorType.ALL)
                                                                                                )
                                                                                        )
                                                                                )
                                                                                .then(new LiteralArgument("score")
                                                                                        .then(new ScoreHolderArgument.Single("scoreholder3")
                                                                                                .then(new ObjectiveArgument("objective3")
                                                                                                        .then(new DoubleArgument("scale3")
                                                                                                                .then(new DoubleArgument("maxPower")
                                                                                                                        .then(new MultiLiteralArgument("fallOffType", "linear").setOptional(true)
                                                                                                                                .executes((sender, args) -> {
                                                                                                                                    Entity center = ((Entity) args.get("center"));
                                                                                                                                    ArrayList<Entity> targets = ((ArrayList<Entity>) args.get("targets"));
                                                                                                                                    String scoreholder1 = (String) args.get("scoreholder1");
                                                                                                                                    Objective objective1 = (Objective) args.get("objective1");
                                                                                                                                    double scale1 = (double) args.get("scale1");
                                                                                                                                    double radius = ((double) objective1.getScore(scoreholder1).getScore())*scale1;

                                                                                                                                    double minPower = ((double) args.get("minPower"));

                                                                                                                                    String scoreholder3 = (String) args.get("scoreholder3");
                                                                                                                                    Objective objective3 = (Objective) args.get("objective3");
                                                                                                                                    double scale3 = (double) args.get("scale3");
                                                                                                                                    double maxPower = ((double) objective3.getScore(scoreholder3).getScore())*scale3;

                                                                                                                                    String fallOffType = args.get("fallOffType") == null ? "LINEAR" : ((String) args.get("fallOffType")).toUpperCase();

                                                                                                                                    blast(center, targets, radius, maxPower, minPower, fallOffType);
                                                                                                                                }, ExecutorType.ALL)
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                                .then(new LiteralArgument("score")
                                                                        .then(new ScoreHolderArgument.Single("scoreholder2")
                                                                                .then(new ObjectiveArgument("objective2")
                                                                                        .then(new DoubleArgument("scale2")
                                                                                                .then(new LiteralArgument("value")
                                                                                                        .then(new DoubleArgument("maxPower")
                                                                                                                .then(new MultiLiteralArgument("fallOffType", "linear").setOptional(true)
                                                                                                                        .executes((sender, args) -> {
                                                                                                                            Entity center = ((Entity) args.get("center"));
                                                                                                                            ArrayList<Entity> targets = ((ArrayList<Entity>) args.get("targets"));

                                                                                                                            String scoreholder1 = (String) args.get("scoreholder1");
                                                                                                                            Objective objective1 = (Objective) args.get("objective1");
                                                                                                                            double scale1 = (double) args.get("scale1");
                                                                                                                            double radius = ((double) objective1.getScore(scoreholder1).getScore())*scale1;

                                                                                                                            String scoreholder2 = (String) args.get("scoreholder2");
                                                                                                                            Objective objective2 = (Objective) args.get("objective2");
                                                                                                                            double scale2 = (double) args.get("scale2");
                                                                                                                            double minPower = ((double) objective2.getScore(scoreholder2).getScore())*scale2;

                                                                                                                            double maxPower = ((double) args.get("maxPower"));
                                                                                                                            String fallOffType = args.get("fallOffType") == null ? "LINEAR" : ((String) args.get("fallOffType")).toUpperCase();

                                                                                                                            blast(center, targets, radius, maxPower, minPower, fallOffType);
                                                                                                                        }, ExecutorType.ALL)
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                                .then(new LiteralArgument("score")
                                                                                                        .then(new ScoreHolderArgument.Single("scoreholder3")
                                                                                                                .then(new ObjectiveArgument("objective3")
                                                                                                                        .then(new DoubleArgument("scale3")
                                                                                                                                .then(new DoubleArgument("maxPower")
                                                                                                                                        .then(new MultiLiteralArgument("fallOffType", "linear").setOptional(true)
                                                                                                                                                .executes((sender, args) -> {
                                                                                                                                                    Entity center = ((Entity) args.get("center"));
                                                                                                                                                    ArrayList<Entity> targets = ((ArrayList<Entity>) args.get("targets"));

                                                                                                                                                    String scoreholder1 = (String) args.get("scoreholder1");
                                                                                                                                                    Objective objective1 = (Objective) args.get("objective1");
                                                                                                                                                    double scale1 = (double) args.get("scale1");
                                                                                                                                                    double radius = ((double) objective1.getScore(scoreholder1).getScore())*scale1;

                                                                                                                                                    String scoreholder2 = (String) args.get("scoreholder2");
                                                                                                                                                    Objective objective2 = (Objective) args.get("objective2");
                                                                                                                                                    double scale2 = (double) args.get("scale2");
                                                                                                                                                    double minPower = ((double) objective2.getScore(scoreholder2).getScore())*scale2;

                                                                                                                                                    String scoreholder3 = (String) args.get("scoreholder3");
                                                                                                                                                    Objective objective3 = (Objective) args.get("objective3");
                                                                                                                                                    double scale3 = (double) args.get("scale3");
                                                                                                                                                    double maxPower = ((double) objective3.getScore(scoreholder3).getScore())*scale3;

                                                                                                                                                    String fallOffType = args.get("fallOffType") == null ? "LINEAR" : ((String) args.get("fallOffType")).toUpperCase();

                                                                                                                                                    blast(center, targets, radius, maxPower, minPower, fallOffType);
                                                                                                                                                }, ExecutorType.ALL)
                                                                                                                                        )
                                                                                                                                )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
                .then(new LocationArgument("center")
                        //Entities affected by the explosion
                        .then(new EntitySelectorArgument.ManyEntities("targets")
                                //Literal arguments determine where the values come from (for radius, minPower, maxPower)
                                //scoreholders and objectives refer to scoreboards to retrieve values from
                                //scale scales the value of the score
                                //Radius is the explosion radius
                                //minPower is the minimum velocity it could launch entities
                                //maxPower is the maximum velocity it could launch entities
                                .then(new LiteralArgument("value")
                                        .then(new DoubleArgument("radius")
                                                .then(new LiteralArgument("value")
                                                        .then(new DoubleArgument("minPower")
                                                                .then(new LiteralArgument("value")
                                                                        .then(new DoubleArgument("maxPower")
                                                                                .then(new MultiLiteralArgument("fallOffType", "linear").setOptional(true)
                                                                                        .executes((sender, args) -> {
                                                                                            Location center = ((Location) args.get("center"));
                                                                                            ArrayList<Entity> targets = ((ArrayList<Entity>) args.get("targets"));
                                                                                            double radius = ((double) args.get("radius"));
                                                                                            double minPower = ((double) args.get("minPower"));
                                                                                            double maxPower = ((double) args.get("maxPower"));
                                                                                            String fallOffType = args.get("fallOffType") == null ? "LINEAR" : ((String) args.get("fallOffType")).toUpperCase();

                                                                                            blast(center, targets, radius, maxPower, minPower, fallOffType);
                                                                                        }, ExecutorType.ALL)
                                                                                )
                                                                        )
                                                                )
                                                                .then(new LiteralArgument("score")
                                                                        .then(new ScoreHolderArgument.Single("scoreholder3")
                                                                                .then(new ObjectiveArgument("objective3")
                                                                                        .then(new DoubleArgument("scale3")
                                                                                                .then(new DoubleArgument("maxPower")
                                                                                                        .then(new MultiLiteralArgument("fallOffType", "linear").setOptional(true)
                                                                                                                .executes((sender, args) -> {
                                                                                                                    Location center = ((Location) args.get("center"));
                                                                                                                    ArrayList<Entity> targets = ((ArrayList<Entity>) args.get("targets"));
                                                                                                                    double radius = ((double) args.get("radius"));
                                                                                                                    double minPower = ((double) args.get("minPower"));

                                                                                                                    String scoreholder3 = (String) args.get("scoreholder3");
                                                                                                                    Objective objective3 = (Objective) args.get("objective3");
                                                                                                                    double scale3 = (double) args.get("scale3");
                                                                                                                    double maxPower = ((double) objective3.getScore(scoreholder3).getScore())*scale3;

                                                                                                                    String fallOffType = args.get("fallOffType") == null ? "LINEAR" : ((String) args.get("fallOffType")).toUpperCase();

                                                                                                                    blast(center, targets, radius, maxPower, minPower, fallOffType);
                                                                                                                }, ExecutorType.ALL)
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                                .then(new LiteralArgument("score")
                                                        .then(new ScoreHolderArgument.Single("scoreholder2")
                                                                .then(new ObjectiveArgument("objective2")
                                                                        .then(new DoubleArgument("scale2")
                                                                                .then(new LiteralArgument("value")
                                                                                        .then(new DoubleArgument("maxPower")
                                                                                                .then(new MultiLiteralArgument("fallOffType", "linear").setOptional(true)
                                                                                                        .executes((sender, args) -> {
                                                                                                            Location center = ((Location) args.get("center"));
                                                                                                            ArrayList<Entity> targets = ((ArrayList<Entity>) args.get("targets"));
                                                                                                            double radius = ((double) args.get("radius"));

                                                                                                            String scoreholder2 = (String) args.get("scoreholder2");
                                                                                                            Objective objective2 = (Objective) args.get("objective2");
                                                                                                            double scale2 = (double) args.get("scale2");
                                                                                                            double minPower = ((double) objective2.getScore(scoreholder2).getScore())*scale2;

                                                                                                            double maxPower = ((double) args.get("maxPower"));
                                                                                                            String fallOffType = args.get("fallOffType") == null ? "LINEAR" : ((String) args.get("fallOffType")).toUpperCase();

                                                                                                            blast(center, targets, radius, maxPower, minPower, fallOffType);
                                                                                                        }, ExecutorType.ALL)
                                                                                                )
                                                                                        )
                                                                                )
                                                                                .then(new LiteralArgument("score")
                                                                                        .then(new ScoreHolderArgument.Single("scoreholder3")
                                                                                                .then(new ObjectiveArgument("objective3")
                                                                                                        .then(new DoubleArgument("scale3")
                                                                                                                .then(new DoubleArgument("maxPower")
                                                                                                                        .then(new MultiLiteralArgument("fallOffType", "linear").setOptional(true)
                                                                                                                                .executes((sender, args) -> {
                                                                                                                                    Location center = ((Location) args.get("center"));
                                                                                                                                    ArrayList<Entity> targets = ((ArrayList<Entity>) args.get("targets"));
                                                                                                                                    double radius = ((double) args.get("radius"));

                                                                                                                                    String scoreholder2 = (String) args.get("scoreholder2");
                                                                                                                                    Objective objective2 = (Objective) args.get("objective2");
                                                                                                                                    double scale2 = (double) args.get("scale2");
                                                                                                                                    double minPower = ((double) objective2.getScore(scoreholder2).getScore())*scale2;

                                                                                                                                    String scoreholder3 = (String) args.get("scoreholder3");
                                                                                                                                    Objective objective3 = (Objective) args.get("objective3");
                                                                                                                                    double scale3 = (double) args.get("scale3");
                                                                                                                                    double maxPower = ((double) objective3.getScore(scoreholder3).getScore())*scale3;

                                                                                                                                    String fallOffType = args.get("fallOffType") == null ? "LINEAR" : ((String) args.get("fallOffType")).toUpperCase();

                                                                                                                                    blast(center, targets, radius, maxPower, minPower, fallOffType);
                                                                                                                                }, ExecutorType.ALL)
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                                .then(new LiteralArgument("score")
                                        .then(new ScoreHolderArgument.Single("scoreholder1")
                                                .then(new ObjectiveArgument("objective1")
                                                        .then(new DoubleArgument("scale1")
                                                                .then(new LiteralArgument("value")
                                                                        .then(new DoubleArgument("minPower")
                                                                                .then(new LiteralArgument("value")
                                                                                        .then(new DoubleArgument("maxPower")
                                                                                                .then(new MultiLiteralArgument("fallOffType", "linear").setOptional(true)
                                                                                                        .executes((sender, args) -> {
                                                                                                            Location center = ((Location) args.get("center"));
                                                                                                            ArrayList<Entity> targets = ((ArrayList<Entity>) args.get("targets"));

                                                                                                            String scoreholder1 = (String) args.get("scoreholder1");
                                                                                                            Objective objective1 = (Objective) args.get("objective1");
                                                                                                            double scale1 = (double) args.get("scale1");
                                                                                                            double radius = ((double) objective1.getScore(scoreholder1).getScore())*scale1;

                                                                                                            double minPower = ((double) args.get("minPower"));
                                                                                                            double maxPower = ((double) args.get("maxPower"));
                                                                                                            String fallOffType = args.get("fallOffType") == null ? "LINEAR" : ((String) args.get("fallOffType")).toUpperCase();

                                                                                                            blast(center, targets, radius, maxPower, minPower, fallOffType);
                                                                                                        }, ExecutorType.ALL)
                                                                                                )
                                                                                        )
                                                                                )
                                                                                .then(new LiteralArgument("score")
                                                                                        .then(new ScoreHolderArgument.Single("scoreholder3")
                                                                                                .then(new ObjectiveArgument("objective3")
                                                                                                        .then(new DoubleArgument("scale3")
                                                                                                                .then(new DoubleArgument("maxPower")
                                                                                                                        .then(new MultiLiteralArgument("fallOffType", "linear").setOptional(true)
                                                                                                                                .executes((sender, args) -> {
                                                                                                                                    Location center = ((Location) args.get("center"));
                                                                                                                                    ArrayList<Entity> targets = ((ArrayList<Entity>) args.get("targets"));
                                                                                                                                    String scoreholder1 = (String) args.get("scoreholder1");
                                                                                                                                    Objective objective1 = (Objective) args.get("objective1");
                                                                                                                                    double scale1 = (double) args.get("scale1");
                                                                                                                                    double radius = ((double) objective1.getScore(scoreholder1).getScore())*scale1;

                                                                                                                                    double minPower = ((double) args.get("minPower"));

                                                                                                                                    String scoreholder3 = (String) args.get("scoreholder3");
                                                                                                                                    Objective objective3 = (Objective) args.get("objective3");
                                                                                                                                    double scale3 = (double) args.get("scale3");
                                                                                                                                    double maxPower = ((double) objective3.getScore(scoreholder3).getScore())*scale3;

                                                                                                                                    String fallOffType = args.get("fallOffType") == null ? "LINEAR" : ((String) args.get("fallOffType")).toUpperCase();

                                                                                                                                    blast(center, targets, radius, maxPower, minPower, fallOffType);
                                                                                                                                }, ExecutorType.ALL)
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                                .then(new LiteralArgument("score")
                                                                        .then(new ScoreHolderArgument.Single("scoreholder2")
                                                                                .then(new ObjectiveArgument("objective2")
                                                                                        .then(new DoubleArgument("scale2")
                                                                                                .then(new LiteralArgument("value")
                                                                                                        .then(new DoubleArgument("maxPower")
                                                                                                                .then(new MultiLiteralArgument("fallOffType", "linear").setOptional(true)
                                                                                                                        .executes((sender, args) -> {
                                                                                                                            Location center = ((Location) args.get("center"));
                                                                                                                            ArrayList<Entity> targets = ((ArrayList<Entity>) args.get("targets"));

                                                                                                                            String scoreholder1 = (String) args.get("scoreholder1");
                                                                                                                            Objective objective1 = (Objective) args.get("objective1");
                                                                                                                            double scale1 = (double) args.get("scale1");
                                                                                                                            double radius = ((double) objective1.getScore(scoreholder1).getScore())*scale1;

                                                                                                                            String scoreholder2 = (String) args.get("scoreholder2");
                                                                                                                            Objective objective2 = (Objective) args.get("objective2");
                                                                                                                            double scale2 = (double) args.get("scale2");
                                                                                                                            double minPower = ((double) objective2.getScore(scoreholder2).getScore())*scale2;

                                                                                                                            double maxPower = ((double) args.get("maxPower"));
                                                                                                                            String fallOffType = args.get("fallOffType") == null ? "LINEAR" : ((String) args.get("fallOffType")).toUpperCase();

                                                                                                                            blast(center, targets, radius, maxPower, minPower, fallOffType);
                                                                                                                        }, ExecutorType.ALL)
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                                .then(new LiteralArgument("score")
                                                                                                        .then(new ScoreHolderArgument.Single("scoreholder3")
                                                                                                                .then(new ObjectiveArgument("objective3")
                                                                                                                        .then(new DoubleArgument("scale3")
                                                                                                                                .then(new DoubleArgument("maxPower")
                                                                                                                                        .then(new MultiLiteralArgument("fallOffType", "linear").setOptional(true)
                                                                                                                                                .executes((sender, args) -> {
                                                                                                                                                    Location center = ((Location) args.get("center"));
                                                                                                                                                    ArrayList<Entity> targets = ((ArrayList<Entity>) args.get("targets"));

                                                                                                                                                    String scoreholder1 = (String) args.get("scoreholder1");
                                                                                                                                                    Objective objective1 = (Objective) args.get("objective1");
                                                                                                                                                    double scale1 = (double) args.get("scale1");
                                                                                                                                                    double radius = ((double) objective1.getScore(scoreholder1).getScore())*scale1;

                                                                                                                                                    String scoreholder2 = (String) args.get("scoreholder2");
                                                                                                                                                    Objective objective2 = (Objective) args.get("objective2");
                                                                                                                                                    double scale2 = (double) args.get("scale2");
                                                                                                                                                    double minPower = ((double) objective2.getScore(scoreholder2).getScore())*scale2;

                                                                                                                                                    String scoreholder3 = (String) args.get("scoreholder3");
                                                                                                                                                    Objective objective3 = (Objective) args.get("objective3");
                                                                                                                                                    double scale3 = (double) args.get("scale3");
                                                                                                                                                    double maxPower = ((double) objective3.getScore(scoreholder3).getScore())*scale3;

                                                                                                                                                    String fallOffType = args.get("fallOffType") == null ? "LINEAR" : ((String) args.get("fallOffType")).toUpperCase();

                                                                                                                                                    blast(center, targets, radius, maxPower, minPower, fallOffType);
                                                                                                                                                }, ExecutorType.ALL)
                                                                                                                                        )
                                                                                                                                )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
                .register();
    }

    public void blast(Location center, ArrayList<Entity> entities, double radius, double maxPower, double minPower, String fallOffType){
        for(int i = 0; i < entities.size(); i++){
            Entity entity = entities.get(i);
            Location location = entity.getLocation();
            Vector vel = (location.subtract(center)).toVector().normalize();
            double distance = (center.distance(location));

            switch(fallOffType){
                case "LINEAR":
                    vel.multiply(maxPower-((maxPower-minPower)/radius)*distance);
            }

            entity.setVelocity(entity.getVelocity().add(vel));
        }
    }

    public void blast(Entity center, ArrayList<Entity> entities, double radius, double maxPower, double minPower, String fallOffType){
        blast(center.getLocation(), entities, radius, maxPower, minPower, fallOffType);
    }
}
