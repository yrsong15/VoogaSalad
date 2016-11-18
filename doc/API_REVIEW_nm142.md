# API Review

People: Noel Moon (nm142), Tripp Whaley (djw11)

**Part 1**
 1. My API for the game engine front-end is designed to have to be flexible enough to visualize any of the possible objects that that the back-end passes to the front-end. It also has to have a utility for a HUD that has to be flexible enough to be used in other games.
 2. My API is encapsulating all of the details about visualizing the objects that the back-end needs to visualize. The only methods that are offered to the public are the methods to visualize new objects on the screen and to move around existing objects.
 3. My part is mainly linked to the back-end of the game engine. The back-end is going to have an observable list containing a list of every object that has to be visualized, and the front-end will observe this list and visualize the changes.
 4. One possible error case will be if the back-end passes an object on the list that I did not account for. For example, if the back-end passed a sprite for a plane, but I hadn't implemented a visual for the plane, then that would result in an error.
 5. I think my API is good because it encapsulates mostly all of the implementation details related to visualization. This greatly simplifies the API and makes the API easy for the back-end to use and understand.

**Part 2**
 1. I am most excited to work on the HUD utility because I have to make the HUD flexible enough to be used in other games aside from the ones in my group. The HUD also has to be easy to implement, so I have to be sure that the API for the HUD is simple but powerful.
 2. I am most worried about working on the HUD because like I said in question 1, it has to be simple, but powerful, so I have to design the HUD to make sure that other group's games could use it as well.
 3. This weekend, I plan to implement the methods for visualizing the objects on the screen, so once I have implemented the actual specific objects that I have to visualize, I'll have a working system for the game engine front-end.
 4. The use cases for my piece seem pretty challenging, especially the ones related to saving and loading the state of the game. Those seem challenging because saving requires saving every aspect of the game and then making sure that those aspects can be loaded back using a file.
 5. I do not have use cases for errors that might occur, but I should account for a use case that deals with the back-end passing an object that I am not able to visualize.