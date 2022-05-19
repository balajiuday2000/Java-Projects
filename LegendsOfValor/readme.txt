Wanzhi Wang, z1wang@bu.edu, U72807790
Shekhar Sharama, shekhars@bu.edu, U43306392
Balaji Udayakumar, balaji@bu.edu, U17952240

This program implements an RPG game: Legends of Valor, where the player form a team of three heroes
to fight against a team of monsters (controlled by computer). Heroes are placed on three different
lanes and for each round the player would choose the action for each hero (moving, fighting, teleporting etc.).
Once a hero breaks through monsters' defenses and reaches the opponent's base (Nexus), it counts as a win;
otherwise if a monster reaches hero's base, the player loses.
Specific gaming instructions included in the game.

Execution:
[NOTE: The game runs on Linux terminal with all the colours and sounds, it also runs on a IDE like IntelliJ, 
       It does'nt display colours on the windows command prompt.
       Please either use an IDE or run this on a Linux terminal for best possible experience.]
       
Running Instructions:
>javac *.java
>java Main.java

Class description:
Armory:             Sub-class of gear, representing Armours for heroes
Weaponry:           Sub-class of gear, representing Weapons for heroes
Potion:             Sub-class of gear, representing Potions for heroes
FireSpell:         Sub-class of abstract base class Spell representing Fire Spells
IceSpell:          Sub-class of abstract base class Spell representing Ice Spells
LightningSpell:    Sub-class of abstract base class Spell representing Lightning Spells
Spell:              Sub-class of gear, representing Spells for heroes
Gear:               Abstract base class which makes sure all sub-classes of items which can be used by the team of heroes,
                    have a name, cost and level associated with them.
Inventory:          Class representing a group of items carried by a unit (hero or market), classified by item type
Market:             Class representing the market, capable of selling and purchasing items

Warrior:            Sub-class of Hero, representing Warriors
Sorcerer:           Sub-class of Hero, representing Sorcerers
Paladin:            Sub-class of Hero, representing Paladins
Hero:               Sub-class of character representing all the characteristics of heroes
Dragon:             Sub-class of Monster, representing Dragons
Exoskeleton:        Sub-class of Monster, representing Exoskeletons
Spirit:             Sub-class of Monster, representing Spirits
Monster:            Sub-class of character representing all the characteristics of monsters
Character:          Base class representing all heroes and monsters, which makes sure each character has a name, level and hp
HeroTeam:          Class representing a group of heroes controlled by a single player
MonsterTeam:       Class representing a group of monsters controlled by the computer

Bush_Cell:          Class representing a bush cell
Cave_Cell:          Class representing a cave cell
Plain_Cell:         Class representing Plain cell
Koulou_Cell:        Class representing Koulou cell
Inaccessible_Cell:  Class representing Inaccessible cell
Hero_Nexus_Cell:    Class representing Nexus cell for heroes
Monster_Nexus_Cell: Class representing Nexus cell for monsters
Cell:               Interface implemented by all the different types of cells
GameBoard:         Class representing the game board which includs different cells

Parser:             Class that defines methods of reading game files and parsing data into arrays
Printer:            Class that defines static methods of printing map/instructions/etc. in the game
Music:              Class that defines static methods of playing different musics in the game
levelRequirement:   Interface that makes sure an item can only be used by a hero satisfying the minimum level requirement
manaRequirement:    Interface that makes sure an item can only be used by a hero satisfying the minimum mana requirement

LOV:                Sub-class of RPG, a file that contains the main logic and element interaction of game Legends of Valor
GameManager:        Class used for user to select different games
RPG:                Class that contains the major elements of an RPG game
Main:               The main file of the program that calls Game Manager.

Other info:
Singleton pattern has been implemented where ever necessary
The program ensures the scalability of the number/types of items and characters.
The game handles every possible invalid input.
The game has colored output.
The game has a music. (Music by Jason Shaw on Audionautix.com)
