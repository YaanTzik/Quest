to compile :
javac QuestEngine.java

to run:
java QuestEngine

Classes:

Items: Abstract class that is extended by the 4 categories of items.
Weapons: Extends Item
Armor : Extends Item
Spells: Extends Item (was unsure if Spells were single use, kept them as single use due to the attack values they had.)
Potions: Extends Item
Inventory: Allows User to navigate inventory of heroes. Uses Generics to streamline and avoid Boiler plate code.

Combatant: Abstract class for entities that do battle
Hero class that extends Combatants, Different classes are defined by the various Enum Types defined in HeroType
Monster class that extends Combatants, Different classes are also defined within the Enum Types.

Party: Generic class for a party that will be used to hold teams or heroes
HeroParty: extends Party for operations relating to hero party but not Monster party
MonsterParty: Extends Party, Also initializes randomnized parties for fights

Board: can be inherited and resized as needed main world of play. handles movement logic
Tile: different enum tile classes for board to create world of play
QuestEngine  Class that holds main game loop. Handles main input of User and ties fight, board and market together.
Fight: Creates an instance of fight for the fights to be played out.
Market creates a market instance for a hero to enter and buy/sell items

every time the heroes
visit a common tile there is a chance 
that they will engage in a fight with 
monsters that will
have the same level as the level of the 
highest leveled hero. (I set this to 1 in 5 chances of an encounter)

I reset the chance of market tiles to 0.3 however, i found the game more easily playeble if it was set to 0.15
this gave more common tiles for even more chance encounters. 

