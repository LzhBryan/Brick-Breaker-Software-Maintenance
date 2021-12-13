# Brick Breaker

## Key changes made for maintenance and extension

### Maintenance and refactoring
- Change package name to a more meaningful name.
- Extract Crack class out of the Brick abstract class into another class 
as a class should not contain another class within.
- Break down Wall class and created a new class GameLogic which contains the 
player and ball related code to separate the entities logic away from the levels logic.
- Rename Wall class to GameLevels as it is responsible for handling levels.
- Break down GameBoard class by extracting the PauseMenu related code into another 
class to promote single responsibility principle.
- Created a Brick Factory and Ball Factory class that follows the Factory design pattern to increase abstraction and reduce coupling.
- Further break down GameLevels class to put all the entitites such as brick, 
ball and player into the GameLogic class while GameLevels only contains levels related code.
  - Remove duplicated code by combining both makeSingleTypeLevel and makeChessboardLevel 
  into a single method while maintaining the same logic.
  - Improve the makeLevel method in GameLevels by extracting a block of code into a new method and
  reduce number of parameters.
- Encapsulation, renaming of methods and variables, remove unused code, create a few interface classes, the use of polymorphism.
- Create Junit tests and use maven build files.

### Complete conversion from Swing to JavaFX

### MVC pattern
- Created Models for some Controllers.
- Break down GameBoard class into MVC pattern.
- Rearrange classes into MVC package.
- Created a MvcManager class which handles most of the Model, Controller and View instantiation.

### Extension

#### Scoreboard
- Added a popup scoreboard window.
- Permanent scoreboard list using a text file to store the score. 
If such txt file is not found, a text file will be created to store the record.
- Player can enter their username to be saved along with their score on the 
permanent scoreboard, but not without playing the game.
- Scoreboard is sorted in descending order.

#### Additional playable levels
- Added two extra levels.
- The first added level contains only steel bricks while the second added level 
contains a new brick called Metal brick which has both the crack and probability to break feature
and stronger health point.

#### Exciting rewards and penalties
- When complete a level, players will be rewarded with additional points depending on the amount of ball left.
- Points will be deducted if player loses a ball.
- When all balls are lost, score will be reduced by a portion of the total score.
- Rewarding points will always be more than deducting points.
- A game ending screen will pop up if player completed all the levels.

#### Other features
- Info page and leaderboard page can be accessed at the home menu.
- Info page to display the game controls while leaderboard page display the permanent scoreboard.
- Improved design of all the FXML files with the use of background images, css styling.