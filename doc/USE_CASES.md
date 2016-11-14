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

 GameEditor creates XML file that contains all information about user selections
 
 GameEngine Controller receives this XML file from GameEditor and instantiates needed objects through reflection in its back-end
 
 Front-end of GameEngine visualizes these objects and starts rendering a view based on type of scrolling the user has selected
 
- The user moves main character off the screen

Front-end of GameEngine notifies the GameEngine controller that the user has moved the main character

GameEngine controller notifies

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

Collision rule, if user selected one, is applied
- User presses the up arrow key

controls class is used to see what happens when this key is pressed and call the appropriate action
- User selects music to go along with the game

front-end of gameEngine takes care of this through a Settings class
- Main character acquires the treasure

