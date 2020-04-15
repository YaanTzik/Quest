Pair 18:
Chen Hao Tao
Yaan Tzi Kan

to compile :
javac QuestEngine.java

to run:
java QuestEngine <BoardHeight> <BoardWidth>

where Board height/ boardwidth are the desired board height/width for the gameplay.
Most of the logic is built around having the board dimensions being 8,8 but 8,11 should provide
a good take on the game too. 

Classes:

Sellable: Interface for sellable items. although there are no sellable items. In the future there could be non sellable items
Items: Abstract class that is extended by the 4 categories of items.
Weapons: Extends Item
Armor : Extends Item
Spells: Extends Item (was unsure if Spells were single use, kept them as single use due to the attack values they had.)
Potions: Extends Item
Inventory: Allows User to navigate inventory of heroes. Uses Generics to streamline and avoid Boiler plate code.

BuyerInterface: Implemented by hero. Created if in the future we require to make monsters to purchase items 
Combatant: Abstract class for entities that do battle
Hero abstract class that extends Combatants, Different classes are defined by inheritance Warrior, Paladin, Sorcerer. Handles Tile Buff
Monster class that extends Combatants, Different classes are also defined within the Enum Types.
Warrior:Warrior class inherits Hero
Sorcer: Inherits Hero
Paladin: Inherits Hero
Dragon: Inherits Monster
ExoSkeleton: Inherits Monster
Spirits: Inherits Monster

Party: Generic class for a party that will be used to hold teams or heroes
HeroParty: extends Party for operations relating to hero party but not Monster party
MonsterParty: Extends Party, Also initializes randomnized parties for fights

BoardInterface: Board Interface. I made a board interface so that it can be reused by other games.(Can be used by TTT or the original quest.) 
Board: can be inherited and resized as needed main world of play. handles movement logic, Teleport interface
Coordinates: class to encapsulate, (r,c) coordinates 
Tile: different enum tile classes for board to create world of play

QuestEngine  Class that holds main game loop. Handles main input of User and ties fight, board and market together.
Fight: Creates an instance of fight for the fights to be played out.
Market creates a market instance for a hero to enter and buy/sell items

How Teleport works:
Teleport lets us teleport to the lanes of other heroes
We implemented rules that we are allowed to teleport upto
the hero in the lane but not further. If not we are only 
allowed to teleport to the nexus.



