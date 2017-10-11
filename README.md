# Krit　

![Wile](https://i.imgur.com/4I33ELV.png)

Meet **Wile** or *4c17c1a8-2e31-4c67-8a5e-e7cbbcbb17e6*. He is a male Krit that was produced during a run of the program on 17.10.09. However, that is not the full details of Wile as a creature:

|Key|Value|
|-|-|
|Id|Wile     (4c17c1a8-2e31-4c67-8a5e-e7cbbcbb17e6)|
|Location|(66, 52)|
|Origin|(65, 53)|
|Health|14.65625|
|Initial Health|26.65625|
|Decay Rate|2.0 per 5 updates|
|Sex|MALE|
|Color|(44, 125, 15)|
|Is Aggressive|false|
|Tribe|748f3f5b-932f-4f7c-aa5e-f7aa8b09fdac(74, 51)|
|Has Mated|748f3f5b-932f-4f7c-aa5e-f7aa8b09fdac(74, 51)|
|Mate||
|Is Pregnant|false|
|Term||
|Children||
|Parents|6743b3e8-5d93-4f91-a8e0-6817726c05ff / 6743b3e8-5d93-4f91-a8e0-6817726c05ff|
|Children Count|0|
|Children IDs||
|Genetic Count|0|
|Genetic IDs||
|Separation Turns|30|
|Proximity Turns|0|
|Updates|30|

But let's take a step back and look at what a Krit actually is.

## What is a Krit　
A krit is just a creature that I've develoepd which 'lives' and moves around the screen. They obey a set of rules that dictate how they act and move which will be discussed later on.

## What inspired this program
After seeing a friend produce a version of the game of life, I thought I'd have a go at developing a type of that with many more rules which would let it simulate life in some way

## What rules do Krits follow
Krits exist on a 100x100 grid and follow the basic set of rules:

1. Only one Krit can occupy one tile at a time.
1. Krits can only move within a 1 square radius (x and y offset between -1 and 1).
1. A Krits health will decay at a constant rate which will be determined at its birth
1. At each update, each expressed genetic trait will receive an update which can edit the Krits details and each unexpressed trait will have its triggers ran
1. If a Krit is within 5 of a member of another tribe for 3 or more turns, it will become aggressive. If it is separated (greater than 5 away) from another tribe for 3 turns it will become passive
1. When a Krit is aggressive, it will identify the closest member of another tribe and move towards it. If it is next to it, it will attempt to strike for between 1 and 4 damage with ~80% chance of hitting.
1. If the creature is not aggressive, it will try to move towards the averaged center of the tribe or pick a random x and y offset between -1 and 1 with a chance of 30%.
1. Pregnancy will take 9 updates to complete.
1. A female krit can have between 1 and 3 children
1. A female krit has a ~0.8% chance of losing a child per update during pregnancy
1. A child should be placed within a 1 block radius of the parent and should be lost if a space is not available.
1. A child krit should determine its new health by averaging the parents values with a jitter of between -4 and 4 (including decimals).
1. A child krit should determine its new decay rate by averaging the parents with jitter of -0.2 to 0.2 for the decay rate and -3 to 3 for its frequency.
1. A child krit should have a randomly assigned sex
1. A child krit should take the average of the parents colours with a jitter of -20 to 20 for each of the r g and b values.
1. A child krit should have a minimum decay rate of 0.2/2 (set if the values are set below 0)
1. A child should take all dominant genes of the parents
1. A child should take all recessive genes which exist on both the parents
1. A child should take the tribe of the mother
1. A krit has ~100% chance of mating with its mate when adjacent to each other
1. A krit has ~60% chance of mating with a member of its own tribe if it does not have a Mate
1. A krit has a ~40% chance of mating with a member of another tribe if it does not have a mate
1. If a krit does not have a mate and mates with another krit, that krit will become its mate
　
## Interface
![Inspect](https://i.imgur.com/bR5MVIY.png)
![Map](https://i.imgur.com/lH7K2zb.png)
