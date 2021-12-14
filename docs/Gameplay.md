"Heavy MACH: Defense" is a real-time turret defense style game with an additional objective:
the enemy base must be destroyed before yours.

## Basics

The whole game happens in stages, where each stage is harder than the previous one.
Each stage is composed of two bases (yours and the enemy one) on each side (top & bottom) of a rectangle, with predefined
positions for some enemy turrets.
The goal of each stage is to create machines and turrets to destroy the enemy base (who is able to do just the same to destroy yours).

## The base

The base is the most important point of the game as it defines how one can win/lose.

The enemy base and the friendly base are very similar: both have an HP gauge and get destroyed if it goes to 0.
However, the friendly base has quite a few additional perks:
- it possesses weak weapons which can deal damage to incoming enemy turrets/machines
- it can be upgraded at any point in the middle of a game (the enemy base has a fixed set of upgrades for each stage)

Here are all the available upgrades:
- TODO

## Machines

Machines are mobile units trying to attack the first enemy unit (might be their base) the see on the map.
Each has specific characteristics such as:
- HP
- moving speed
- attack damage
- attack range
- reload speed

Each machine type also has the possibility to be upgraded, which mainly improve attack damage and HP.
Some slight improvements on moving speed, reload speed and attack range may also be registered for some of them.

## Turrets

Turrets are fixed units with high health which also attack the first unit they see.
However, because turrets are fixed, it is possible to completely block machines from going forwards by
creating a wall of turrets without gaps.
Every turret has the same set of characteristics as machines, as well as upgrades on them.

## Special attacks

