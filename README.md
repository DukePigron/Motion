# Motion
A small plugin that adds motion (and similar) commands intended for datapack development

## Dependencies

CommandAPI (recommended version for the corresponding minecraft version)

## Commands
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

### Operators
`add` adds the motion given (by a vector or amount) to the motion the target currently has <br />
`set` sets the target's motion to the motion given (by a vector or amount) <br />
`get` sends a message to the sender of the command that contains the motion (as a vector or number) <br />
`store` stores the target's motion in the scoreboard specified

### Arguments
`<target>` is the entity (or entities for `add` and `set`) that is having their motion modified/stored/returned <br />
`[x|y|z]` is the axis of motion that is being returned/modified. For example, if a player is falling straight down, their motion in the y axis would be a negative value. <br />
`[value]` means that a value (vector or amount) will be given to modify the motion of the target <br />
`[from]` means that the value that the target's motion is being modified by will come from a source (entity or scoreboard) <br />
`[entity|score]` the type of source that the motion value will be taken from <br />
`<vector>` a vector is 3 number values that correspond to a magnitude (distance) and direction. For example, if you want the closest player to move at a speed of 10 blocks/second upward then you would use `/motion set @p value 0 10 0`. <br />
`<amount>` is just the number value you want to use to modify the motion <br />
`<scoreholder>` is the name (entity or fakeplayer) whose score you want to store or retrieve <br />
`<objective>` is the scoreboard objective you want to store or retrieve <br />
`[<scale>]` is an optional value that you want to multiply by the score you're retrieving or the amount you are storing

## To-Do
-Rotation and Position commands
