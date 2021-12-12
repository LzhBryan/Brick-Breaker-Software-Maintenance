# COMP2042_CW_hfybl1

## Key changes made for maintenance and extension

### Maintenance and refactoring
- Extract Crack class out of the Brick abstract class into another class 
as a class should not contain another class within.
- Break down Wall class and created a new class GameLogic which contains the 
player and ball related code to separate the entities logic away from the levels logic.
- Rename Wall class to GameLevels as it is responsible for handling levels.
- Break down GameBoard class by extracting the PauseMenu related code into another 
class to promote single responsibility principle.
- Created a Brick Factory and Ball Factory class that follows the Factory design pattern to return objects accordingly.
- Further break down on GameLevels class to put all the entitites such as brick, 
ball and player into the GameLogic class while GameLevels only contains levels related code.
  - Remove duplicated code by combining both makeSingleTypeLevel and makeChessboardLevel 
  into a single method while maintaining the same logic.
  - Improve and simplify the makeLevel method in GameLevels by extracted a block of code into a new method,
  reduce the amount of parameters by putting the parameters in the constructor and 
  assign it to global variables so all methods can access easily without too many parameters.
- Encapsulation, renaming of methods and variables, remove unused code, create a few interface classes, the use of polymorphism.

### Complete conversion from Swing to JavaFX
- Converted every class into javaFx.

### MVC pattern
- Created Models for some Controllers.
- Break down GameBoard class into MVC pattern.
- Rearrange classes into MVC package.
- Created a MvcManager class which handles most of the Model, Controller and View instantiation.

### Extension
####Scoreboard
- Added a popup scoreboard window.
- Permanent scoreboard list using a txt file to store the score. 
If such txt file is not found, a txt file will be created to store the record.
- Player can enter their username to be saved along with their score on the 
permanent scoreboard, but not without playing the game.
- Scoreboard is sorted in descending order.

####Additional playable level
- Added two extra level.
- The first added level contains all steel bricks while the second added level 
contains a new brick called Metal brick which has the both the crack and probability to break feature
as well as stronger health point.

####Exciting rewards and penalties
- When complete a single level, players will be rewarded with additional points depending on the amount of ball left.
- Points will be deducted if player loses a ball.
- When all balls are lost, score will be reduced by a portion of the total score.
- Rewarding points will always be more than deducting points.
- A game ending screen will pop up if player completed all the levels.

####Other features
- Info page and leaderboard page can be accessed at the home menu.
- Info page to display the game controls while leaderboard page display the permanent scoreboard.
- Improved design of all the FXML files with the use of background images, css styling.