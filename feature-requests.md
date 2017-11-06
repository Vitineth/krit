# Krit Feature Planning

## Virus Transmission
**Feature Short Summary**: Krits can become infected with a virus that reduces their health at a much greater speed than normal. This virus has a chance of being passed to another Krit on contact and has a much higher chance of being passed on to the child

### Virus Effect
The virus effect accelerates the health decay on the Krits. It has a random modifier between 2 and 4 with which is multiplies the `decayRate` and divides the `decayFrequency`

```java
decayRate *= virus.getModifier();
decayFrequency /= virus.getModifier();
```

### Virus Transmission
There are two methods by which the virus can be transmitted: on contact and on birth. The chance of transmission on touch will be variable for each of the methods on launch.

#### On Contact
When adjacent to an infected Krit (top, left, bottom or right, not within 1 radius), there will be a `0.3` chance of being infected.  In future there may be more vulnerabilities that increase the chance such as a genetic trait that multiplies the chance but it will default to a standard value.

#### On Mating
When mating, there will be an increased chance for transmission but it is not guaranteed. These are standard viruses, not STIs. This will be about `0.5` in order to give a reasonable chance.

#### On Birth
There is a much higher chance for transmission on birth but as it is not programmed as a dominant gene, it is not 100% guaranteed. The rates will initialise at `0.8` but can be modified by the configuration window

### Virus Representation
Infected Krits will be represented by a red outline which should show the progression of the virus across the world and across the tribe.

### Virus Introduction
Depending on the configuration, the virus will have a very low chance of being introduced naturally (eg `0.002` or lower). Otherwise, it will have to be added manually by the user clicking on a victim which will become a 'patient zero'.

### Shunning (uncertain)
Optionally, the Krits may try to avoid the members of the tribe that have the virus. This would mean that Krits at the side of the tribe would become avoided and members of the tribe would begin to move away.

## Tribal Relations
In an attempt to change how aggression works, tribes will begin developing relations with each other. When the relationship between two tribes is high, they will avoid being aggressive and after the members stop being aggressive they will become neutral.Relations will remain neutral by default and will be improved by mating across tribes but it will be damaged by aggression and random chance.

## Resources
**Feature Short Summary**: Each tile of the map has a set of resources which can be depleated and regenerated over time. Krits can pick up resources and members with more resources are more likely to mate and tribes with more accumlative resources are more likely to be attacked by tribes with lower resources.

### Map Generation
When the map is generated, we can use a modified version of the diamond square algorithm which will generate us a random terrain map. There is a pre-existing implementation of this already built in JavaScript [here](https://vitineth.me/testing/terrain) and in Python [here](https://gist.github.com/Vitineth/7913d0dcc56bef8e7a49e042005e4312). Due to the nature of the algorithm, we will no longer be able to keep a 100x100 size as the width and height must satisfy the expression 2^n+1 meaning that the closest values are 129 and 65. We will relate the returned values to a set amount of resources which will be able to be configured by the user. We can then use the arduino map function to convert the values:

``` java
long map(long x, long in_min, long in_max, long out_min, long out_max){
  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
}
```

## Aggression Exemptions
Mothers and Fathers should avoid attacking either each other or their children when becoming aggressive. When finding potential enemies to either attack or become aggressive with, there should be an exception for these types:

``` java
possibilities.filter(krit -> krit.getID() != mate.getID()).filter(krit -> sex == Sex.MALE ? this.mate.children.indexOf(krit) == 0 : this.children.indexOf(krit) == 0)
```

This gives a general idea of the steps required to perform this procedure. First we filter out any that are the mate of the krit, and then secondly if the current krit is male, we filter out any possibilities that are listed within their mates children or if they are female we check that it is not in their own children.

## Family Trees
`TODO(@vitineth): 2017/10/12 10:15`

## Configuration Windows
`TODO(@vitineth): 2017/10/12 10:15`

### Drawing
`TODO(@vitineth): 2017/10/12 10:15`

## Natural Disasters
`TODO(@vitineth): 2017/10/12 10:15`

## Property Inspection Window
`TODO(@vitineth): 2017/10/12 10:15`

## Moving Upgrades
`TODO(@vitineth): 2017/10/12 10:15`

## Genetic Upgrades
`TODO(@vitineth): 2017/10/12 10:15`

## Bug Fixes
`TODO(@vitineth): 2017/10/12 10:15`
