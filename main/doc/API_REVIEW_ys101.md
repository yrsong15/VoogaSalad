# API Review

### Contributors
+ Ray Song(ys101)
+ Jordan Frazier(jrf30)

### Part 1
1. Creating an XML file that contains the game-related information. Because XML is a semi-structured format, as long as both the Game Author and the Game Engine know about what features are to be implemented, the Author can literally add any element/attribute without being confined to a specific number of possibilities.
2. The only thing that the Authoring environment lets people know is the actual XML file; the details are all hidden under the hood, according to encapsulation principles.
3. The game authoring environment receives commands from the frontend, which triggers certain methods that append elements/attributes to the XML file.
4. Invalid XML elements/attributes are checked for errors; for example, if the user uploads a corrupt image for a Sprite, it will display a user-friendly error message.
5. Flexible API that is open for extension but closed for modification.

### Part 2
1. Dragging a sprite onto the screen, because it is not only intuitive and game-like, but also should be a huge challenge.
2. Defining results of collisions; how can we specify each interaction between each GObject? Because there are so many cases for this, it should be a hassle to deal with.
3. I plan to implement the XML creation process for Flappy Bird, which is the initial version of the Scroller Platform genre that our team plans to implement.
4. The 40 use cases that we have implemented for our project are not only in-depth, but also diverse enough so that they cover many of the actual situations that the user will encounter while using our program.
5. Because my part of the program accepts inputs from the frontend buttons, there is little room for errors. However, if we do add features that allow the user to upload images for Sprites, we will need an exception for corrupt images.