## Use Cases 

### GameEditor Use Cases
- Create main character
- Create different levels 
- Create enemies and obstacles
- Set movement behaviour
- Set attack options
- Create time-based game rules
- Create objective-based ending conditions
- Initialize BGM
- Initialize background image
- Enable High Score
- Create other items (treasures, health,etc.)
- Send GameEditor information to GameEngine


### Integration Use Cases
- Change winning condition
- Set infinite bounds or limited bounds
- Create new game 
    * The user would click on “Create Game” button in the splash screen. The GameEditor would then be loaded and the user would be able to create a new game.
    * Once the Game is created in the Game editor, the user can then save the game and the game would be sent to the gallery.
- Load old game
    * When the user clicks on the GameFile of a game that has recently been played, the preferences for that game will already be saved and the old game would be loaded
- Copying game as new file
    * When the user clicks on the GameFile in the gallery, they will have the option to copy the GameFile into another file
- Load new game from external file
    * The User will click the load button from the splash screen and then select the XML file to load
- Playing game from gallery
    * When a user clicks a GameFile in gallery, there would be an option that says play game
    * The GameFile is then sent to the GameEngine, and the game is ran and played
- Create template (playable or not) as basis for other games
    * In the gallery view, users would be able to view saved GameFiles on one part of the screen and saved GameTemplates in another 
    * There will be a button inside of the GameEditor that would call a saveGameTemplate method in the controller. The XML file would then be added as a game template in the GalleryView
    * Since the GameEditor will follow a data driven design, it would be easy to load an XML file back into the editor to make further edits to the template.
- Deleting game from gallery
    * When a clicks views a GameFile in the gallery, they will have the option to delete that GameFile
    * The GameFile is then removed from the Gallery and is no longer displayed


### GameEngine Use Cases
- Start playing game after finishing selections in visual editor
 
 GameEngine Controller receives this XML file from the main controller and instantiates needed objects through reflection in its back-end
 
 Front-end of GameEngine visualizes these objects passed from the controller and starts rendering a view based on type of scrolling the user has selected
 
- The user moves main character off the screen

Front-end of GameEngine notifies the GameEngine controller that the user has pressed the control to move the character

The Game Engine Controller tells the GameEngine model to update the character's position

The Game Engine back-end scrolls the screen in the direction of movement by moving all objects on the screen in the opposite direction

The Game Engine back-end notifies the Controller that the model has been updated

The Controller updates the character's position in the view based on the model

- The game has met a time win/lose condition

The GameEngine will have instantiated a rule corresponding to this condition, whose consequences will be applied to the game (either winning/losing the entire game, or moving to a new level)

- Scroll based on scrolling type that user has selected for game

GameEngine will start rendering the corresponding scroll type based on the scroll type given in the XML file

- User loads another game while one game is running

User presses a button on the UI and loads in an XML file for a new game.

The XML file is passed to the back-end where it is interpreted.

The back-end passes the interpreted info back to the front-end and the new game is loaded in.

- User resets the game without having to close their window

User presses the reset button, which re-loads the XML file that was originally loaded and resets the game.

User is able to save the progress in their game

User presses the save button, which creates an XML file with information regarding the progress that they have made.

- Seeing a HUD

The HUD is constantly updated by the controller, which constantly checks the the values that are supposed to appear on the HUD.

- Main character collides with an object

The Collision rule selected by the user or otherwise defaulted is applied.

- User presses the up arrow key

Controls class recognizes whether the up arrow key is one of the controls defined for the game and updates the controller according to what the control entails.

- User selects music to go along with the game

The Settings class in the Game Engine front-end recognizes this and changes the music.

- The Main Character acquires a treasure

The GameEngine back-end recognizes that a treasure was opened and checks the defined rules to update the model accordingly

- The Main character dies

The GameEngine checks the rule defined for death and either resets the game or respawns the character.

- The User presses the control corresponding to the main character jumping

The GameEngine front-end recognizes that the control is a jump control and notifies the controller

The controller instantiates the jump class in the back-end that makes the character jump

The GameEngine back-end notifies the controller to update the front-end accordingly

- The User Pauses the Game 

The GameEngine front-end recognizes that the user has clicked on the pause button

It notifies the Controller to tell the model to pause the current animation, thus pausing the game

- The User presses an invalid control

Nothing happens, because the control is not mapped in the GameEngine front-end, since it was not in the XML

- The main character is hit by a projectile

The Collision class recognizes that the main character is hit and performs the corresponding action based on the defined rules

- The user wants to edit the current game

The user presses the menu option to return to the main menu where there will be an option to edit saved games.

- The main character is falling

The defined gravity rules are checked and executed. If the character collides with a object while falling then collision rules are checked.

- An enemy spawns

Depending on the defined spawning rules, the corresponding Sprites are instantiated in the GameEngine back-end and the controller is notified to update the view to show these enemies.

