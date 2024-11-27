package io.github.dukepigron.motion.commands;

import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.*;
import dev.jorel.commandapi.executors.ExecutorType;

import org.bukkit.entity.Entity;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class LookAtCommand {

    //CommandAPI already checks that inputs are not null
    @SuppressWarnings("unchecked")
    public void registerLookAtCommand(){
        new CommandTree("lookat")
                .withPermission(CommandPermission.OP)

                //In this case the target is all entities that you want to change the look direction of
                .then(new EntitySelectorArgument.ManyEntities("target")
                        //Source is who/where you want them to look
                        .then(new EntitySelectorArgument.OneEntity("source")
                                .then(new BooleanArgument("anchoredEyes").setOptional(true)
                                        .executes((sender, args) -> {
                                            boolean eyes = args.get("anchoredEyes") == null ? false : (boolean) args.get("anchoredEyes");
                                            lookAt((ArrayList<Entity>) args.get("target"), (Entity) args.get("source"), eyes);
                                        }, ExecutorType.ALL)
                                )
                        )
                        .then(new LocationArgument("pos")
                                .executes((sender, args) -> {
                                    lookAt((ArrayList<Entity>) args.get("target"), (Location) args.get("pos"));
                                }, ExecutorType.ALL)
                        )
                )
                .register();
    }

    //Subtracts the target's position from the source and sets the direction to the resulting vector
    public void lookAt(ArrayList<Entity> target, Location source){
        for(Entity entity : target) {
            Location posTarget = entity instanceof LivingEntity ? ((LivingEntity) entity).getEyeLocation() : entity.getLocation();
            Location location = entity.getLocation();
            location.setDirection(source.toVector().subtract(posTarget.toVector()));

            //Allows smooth change in direction entity is looking
            Vector sourceVec = location.getDirection().normalize().multiply(0.1);
            sourceVec.add(entity.getLocation().getDirection().normalize().multiply(0.9));
            sourceVec.add(sourceVec.normalize().multiply(1.0));
            location.setDirection(sourceVec);

            float yaw = location.getYaw();
            float pitch = location.getPitch();
            if(yaw != entity.getYaw() || pitch != entity.getPitch())
                entity.setRotation(yaw, pitch);
        }
    }
    //Sets the position of the source to their eyes if set to true
    public void lookAt(ArrayList<Entity> target, Entity source, boolean eyes){
        Location posSource = eyes && (source instanceof LivingEntity) ? ((LivingEntity) source).getEyeLocation() : source.getLocation();

        lookAt(target, posSource);
    }

    public void lookAt(Entity target, Location source){
        ArrayList<Entity> entity = new ArrayList<>();
        entity.add(target);
        lookAt(entity, source);
    }

    public void lookAt(Entity target, Entity source, boolean eyes){
        ArrayList<Entity> entity = new ArrayList<>();
        entity.add(target);

        lookAt(entity, source, eyes);
    }

}
