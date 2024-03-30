# Motion
A small plugin that adds motion (and similar) commands intended for datapack development

## Dependencies

CommandAPI (recommended version for the corresponding minecraft version)

## Commands
### /motion
```
/motion [add|set] <target> [value] <vector>
/motion [add|set] <target> [from] [entity] <source>

/motion [add|set] <target> [x|y|z] [value] <amount>
/motion [add|set] <target> [x|y|z] [from] [score] <scoreholder> <objective>
/motion [add|set] <target> [x|y|z] [from] [score] <scoreholder> <objective> [<scale>]

/motion [get] <target>
/motion [get] <target> [x|y|z]

/motion [store] <target> [x|y|z] <scoreholder> <objective>
/motion [store] <target> [x|y|z] <scoreholder> <objective> [<scale>]
```

Corresponds to the minecraft NBT data Motion. <br />

### /position (/pos, /location)
```
/position [add|set] <target> [value] <vector>
/position [add|set] <target> [from] [entity] <source>

/position [add|set] <target> [x|y|z] [value] <amount>
/position [add|set] <target> [x|y|z] [from] [score] <scoreholder> <objective>
/position [add|set] <target> [x|y|z] [from] [score] <scoreholder> <objective> [<scale>]

/position [get] <target>
/position [get] <target> [x|y|z]

/position [store] <target> [x|y|z] <scoreholder> <objective>
/position [store] <target> [x|y|z] <scoreholder> <objective> [<scale>]
```

Corresponds to the minecraft NBT data Pos. <br />

### /rotation
```
/rotation [add|set] <target> [value] <rotation>
/rotation [add|set] <target> [from] [entity] <source>

/rotation [add|set] <target> [yaw|pitch] [value] <amount>
/rotation [add|set] <target> [yaw|pitch] [from] [score] <scoreholder> <objective>
/rotation [add|set] <target> [yaw|pitch] [from] [score] <scoreholder> <objective> [<scale>]

/rotation [get] <target>
/rotation [get] <target> [yaw|pitch]

/rotation [store] <target> [yaw|pitch] <scoreholder> <objective>
/rotation [store] <target> [yaw|pitch] <scoreholder> <objective> [<scale>]
```

Corresponds to the minecraft NBT data Rotation. <br />

### /lookat
```
/lookat <target> <source>
/lookat <target> <source> [<anchoredEyes>]
/lookat <target> <location>
```
Makes the target look at the source

### /launch
```
/launch [add|set] <target> <direction> <speed>
```
Both <direction> and <speed> can be given a `[value]` directly, taking two floats and a single double, respectively. <br />
Alternatively, each can take a `[score]` from objectives, with the direction taking two objectives (first for yaw and second for pitch) and the speed taking one. <br />
Launches the target with the speed and direction given, can either add or set their current speed

### /distance
```
/distance [get] <target1> <target2>
/distance [store] <target1> <target2> <scoreholder> <objective>
/distance [store] <target1> <target2> <scoreholder> <objective> [<scale>]
```
Retrieves the distance between an entity and either another entity or a position

### /explode
```
/explode [entity|location] <radius> <minPower> <maxPower> <fallOffType>
```
Simulates the motion of an explosion (without particles). The radius, minPower, and maxPower can take double values or scoreboard values. Power refers to the motion added to the entities hit. fallOffType refers to the way power falls off for targets further from the center

### Operators
`add` adds the motion given (by a vector or amount) to the motion the target currently has <br />
`set` sets the target's motion to the motion given (by a vector or amount) <br />
`get` sends a message to the sender of the command that contains the motion (as a vector or number) <br />
`store` stores the target's motion in the scoreboard specified

### Argument Glossary
`<amount>` is just the number value you want to use to modify the motion <br />
`[<anchoredEyes>]` takes a boolean (true/false) and, if true, it sets the look position at the source entity's eyes <br />
`[entity|score]` the type of source that the motion value will be taken from <br />
`[from]` means that the value that the target's motion is being modified by will come from a source (entity or scoreboard) <br />
`<location>` takes 3 values, corresponding to an x, y, and z position <br />
`<objective>` is the scoreboard objective you want to store or retrieve <br />
`<rotation>` two values, with the first being the yaw and the second being the pitch <br />
`[<scale>]` is an optional value that you want to multiply by the score you're retrieving or the amount you are storing <br />
`<scoreholder>` is the name (entity or fakeplayer) whose score you want to store or retrieve <br />
`<source>` is the entity that is used for reference. For example, if you use `/lookat @e @p` the nearest player (@p) will be the entity that the targets (@e) look at <br />
`<target>` is the entity (or entities) that has their data modified/stored/returned <br />
`[value]` means that a value (vector or amount) will be given to modify target's data <br />
`<vector>` a vector is 3 number values that correspond to a magnitude (distance) and direction. For example, if you want the closest player to move at a speed of 10 blocks/second upward then you would use `/motion set @p value 0 10 0`. <br />
`[x|y|z]` is the axis of motion that is being returned/modified. For example, if a player is falling straight down, their motion in the y axis would be a negative value. <br />

## To-Do
-nothing
