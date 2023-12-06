# Motion
A small plugin that adds motion (and similar) commands intended for datapack development

## Commands
```
/motion <target> [get]
/motion <target> [get] <x|y|z>
/motion <target> [set] <vector>
/motion <target> [set] <x|y|z> <amount>
```

Corresponds to the minecraft NBT data Motion. Get returns a vector (if no axis specified) or double (axis specified). Set must take 3 doubles (if no axis is specified) or 1 double (if an axis is specified)

## To-Do
-Implementing CommandAPI to make commands compatible with /execute
