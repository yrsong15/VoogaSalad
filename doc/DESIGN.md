# Design - VOOGASalad DreamTeam
## Introduction

The objective of this project is to design a platform on which users can create a game by specifying what content it contains through the use of a visual editor. Our primary design goal for this project is to make it as flexible as possible, thus allowing users to make as many decisions about what they would like their game content to be. Furthermore, we also want to design it in such a way that we could easily add more options for the user to customize different aspects of a game. In order to do this, we have decided to use a data-driven design so that we can avoid hard-coding features into the code base and instead define objects/values in configuration files. This means that abstraction, composition, inheritance, and encapsulation will be the main keys to our project. Our chosen game genre is scrolling platformer, which involves a side-view camera angle of the gameplay, and a screen that “scrolls”in different direction according to the movement of the sprites in the game. The qualities specific to this genre that the design will need to support are scrolling, physics for certain movements such as jumping, and collisions between objects. We also plan to support multiple different game-winning and game-losing objectives that are not just specific to scrolling platformers. Additionally, we have decided that our design will support the main varieties within this genre, which are forced (i.e. flappy bird), limited (i.e. doodle jump), and free range (i.e. mario). 

## Overview
At a high-level, the design of the project is divided into three parts: game editor, game engine, and integration. Both the game editor and game engine have their own front-end, controller, and back-end. Integration is the part that will be in control of getting both the game editor and the game engine to work together. The user will first choose their settings for a game in the game editor where there will be an interface with options, buttons, and menus. This user interface will be defined by the game editor front-end while the saving of objects and other characteristics of the game being created is implemented by the game editor backend. Once the user finishes the game, game editor will generate an XML file that contains all of the information about the game and send it to the game engine controller through integration. This controller will then reflectively instantiate any objects needed and the game will then be visualized by the game engine frontend. The game engine backend will take care of updates to the state of the world based on user interaction with the user interface.

The modules in Game Editor are Rules, Game Object, Behaviour and Settings. The Game Object is a superclass that is extended by Sprite, Obstacles and Items classes. The Rules module will take care of different ways of winning a game. The Behaviour module will provide options for the user to select different movement, position  behaviour for the game objects. The settings module will consist of classes that allow the user to change basic settings of the game, for example, background image, background music,etc. 

The GameEngine is responsible for saving the state of each object in the game world, updating it according to user interactions, and rendering the actual game view itself. The following are the modules that will be included in this sub-class:
- Rules

represents the different rules available for what happens when certain conditions are met within the game (i.e. collisions, off-screen, etc.)

- Scrolling

different types of scrolling that can be used for rendering

- Action

actions that can be taken (i.e. jump, left, right, etc.)

- Controls

keyboard functions that correspond to various actions

- Parser

uses the XML factory to parse file given by GameEditor

## User Interface

### Game Editor:
![Game Editor UI](DesignImages/gameEditor_UI.jpg)

The user will be displayed with options to either create a game or load a game file initially. Essentially, if the user wants to create a new game, another screen will pop up with different options to create the desired game. Various game objects, such as the Player, Enemies, Obstacles can be added through the option boxes laid out in the scene. The user can add different objects into the scene for each level. The behaviour for the sprites and obstacles can also be set up from the front end. The user can decide on other game items such as health and treasures, etc.  

### Game Engine:
The game engine is the execution environment for the scene established or created within the Game Editor. Within the game, we animate the objects and sprites identified within the editor while implementing the timeline, listed rules, objectives, and other components of the game archetype. Depending on the game type, the scrollable screen will behave in a way to properly reflect the behavior of the objects and will be playable using the default (WASD, UpDownLeftRight keys) or commands listed within the Game Editor. The user will continue playing until the end of level point is reached or the game as created within the Game Editor is finished (e.g. game-over or game completion).

## Design Details 
### Game Editor: 
The modules for the game editor are given below:
* GameObject


This is an abstract class from which all specific sprites and obstacles will extend. 
It includes a boolean field called isDestructible, which decides whether the object in question can be destroyed (ex: Main Character, enemy characters, certain obstacles) or not (ex: walls, ground). 
Each isDestructible field will point to other GameObject instances; for example, the user will be able to choose which obstacles/enemies can destroy the Main Character
It also includes a boolean field called isStatic, which decides whether the object in question can move(ex: Main Character, certain enemy characters) or not (ex: walls, items)
Main Character will have a Behavior module linked to it, so that the user can configure how the character move around
* Behavior


Input
Keys : The keys that are used to set up different movement behaviour options for sprites in the game. 

* Command


Direction of Movement 
Attack 

* Movement Method


Smooth (ex: Mario) - the movement is seemingly unaffected by gravity, in that the character is permanently displaced after each move
Step (ex: Flappy Bird) - the movement is seemingly affected by gravity, in that after each movement, the character returns to where it was originally

* Rules


Score Method

Time-based: User has to achieve something in a limited window or time, or the user has to stay alive for as long as possible

Objective-based: User has to achieve a certain number of goals, or the user has to collect/achieve as many goals as possible
* Settings

Background Music

Background Image

### GameEngine:

* Rule

This module specifies ways to handle things that happen in the game world. It will have a function applyConsequences(...) that applies any consequences of a certain event happening in the world (i.e. a collision, or the character going off-screen. Any rules instantiated at runtime will be checked for whether consequences need to be applied, which allows flexibility in creating this rules as new rules can be easily added. There will also be multiple sub-classes within rule types such as collision

collision: specifies what happens when there is a collision

off-screen: what happens when the character goess off the screen
win/lose conditions
 
 time - specifies a time win/lose condition

score - specifies a score win/lose condition

* Scrolling

Different types of scrolling will be available for the user to choose and these will all implement an interface of Scrollable, which will each, at the very least, have a scroll method that the backend of the game engine can call once it instantiates the right type of scrolling class. The following class will also extend from an abstract class of BasicScrolling, which specifies how to scroll left, right, up, or down, since these are all methods that the below classes may need. 

forced - forces the main character to move in a certain direction

limited

free-range

* Action

It is an interface that has one method, doAction(..). The purpose of this is to define and complete any type of action available in the game. This means that new actions can easily be added for the user to choose from.
examples are: jump, up, down, left, right, shoot

* Controls

keyboard functions that correspond to various actions; how they correspond will rely on the XML file to figure out

Parser - uses the XML factory to parse file given by GameEditor

## Example games


* Mario (Limited, free-scroller)

Allows movement in all directions, game limited in length and divided into distinct levels

* Flappy Bird (Unlimited, forced scroller)

Horizontal movement is forced, vertical movement is user controlled but stepped/limited, time based game with unlimited length
* Doodle Jump (Unlimited, one direction scroller)

Horizontal movement is entirely user controlled, though using a toroidal screen to limit location, vertical movement is forced upwards but user controlled to an extent


Above are three examples of scrolling games, differing in their movement methods, scrolling methods and level design. Within our design, various movement methods will be available (from a finite set of options) to define; a scrolling method will be defined at the beginning of the editing process, allowing for different object definition methods (I.e. For distinct levels exact placements of objects, for infinite games one would designate regions in which objects can be automatically generated with time). In addition, these games differ significantly in rules. To enable this, direct interaction will allow for the setting of different in game behaviors and inter-object interactivity.


Through this design, we are able to create any of the above games, or, alternatively, an amalgamation of them, due to the freedom in design.



## Design Considerations 


One design consideration was whether the front-ends for the game editor and game engine should share classes. It is likely that the front-ends will both deal with classes for sprites, obstacles, and other various game objects. The pro of sharing the classes is that a lot of duplicate code could be avoided, but the con is that if the classes need to serve different functions for the game editor vs the game engine, then the classes shouldn’t be shared.


Another design consideration was figuring out how to deal with scrolling. One option that we thought of was moving every object on the screen to the left when the player’s sprite moves right. The pro of this is that it is simple to implement, but the con is that the game engine would have to keep track of every object on the screen to know which objects they have to move when the player’s sprite moves.





