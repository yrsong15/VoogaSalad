ANALYSIS.md

Ryan Bergamini (rb265)

### Part 1
* What about your API/design is intended to be flexible?
	* We intend the data driven design of the game editor to make it easier to load partial data files and make them editable.
* How is your API/design encapsulating your implementation decisions?
	* Our program is separated into 3 different parts: the Game Editor, the Game Engine, and the SplashScreen/Gallery. These 3 modules all reside under the MainController which calls the launch function in the Editor. The Game Editor part of the program just needs to put all their code to create the editor in the launch function without having to know about the rest of the program. 
* How is your part linked to other parts of the project?
	* My part is the part that connects the Game Editor to the Game Engine.
* What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?
	* People may try to load improper game files, I will display the error in a pop up window. 
* Why do you think your API/design is good (also define what your measure of good is)?
	* I think the API design is good because it is minimal and therefore flexible. It allows each part of the project to design their respective part with out relying as heavily on code from other projects.
### Part 2
* What feature/design problem are you most excited to work on?
	* I am most excited to work on the Gallery
* What feature/design problem are you most worried about working on?
	* I am most worried about working on multiple screens to display the GameEditor and the GameEngine because already in the basic implementation of our project, they are not working.
* What is do you plan to implement this weekend?
	* I plan to implement the basic implementation which contains the gallery and being able to load games into the editor and engine
* Discuss the use cases/issues created for your pieces: are they descriptive, appropriate, and reasonably sized?
	* They are appropriate and reasonably size and quite basic so they didn't have much description. For example, deleting a Game File is quite a basic and straight forward command
* Do you have use cases for errors that might occur?
	* Yes, we have use cases for when the user tries to load an improper game file
