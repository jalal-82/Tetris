# Test Plan
 

The following classes and methods should have some form of unit testing.

#### Methods to Test in Isolation: 
These methods can be tested independently to verify their functionality.
#### Conditions That Cannot Be Tested in Isolation: 
These conditions refer to the interaction between methods, meaning the overall functionality or output relies on multiple methods being tested together.

## Class: Tile

### Methods to Test in Isolation
- `isValidSelection(String tileName)`
- `applyWindows(boolean[] windows)`
- `generateTiles(Dices rolledDices)`
- `rotateTile(int rotation)`
- `updateSelectedTile(String key)`

### Conditions That Cannot Be Tested in Isolation
- For `applyWindows(boolean[] windows)`: After calling `applyWindows(...)`, verify that the characters in `selectedTile` have been updated according to the provided `windows` array.
- For `generateTiles(Dices rolledDices)`: After calling `generateTiles(...)`, ensure that the length of the returned array is 4, and it only contains valid tile names based on the input dice.
- For `rotateTile(int rotation)`: After calling `rotateTile(...)`, check that the `selectedTile` has been rotated correctly based on the number of specified rotations.
- For `updateSelectedTile(String key)`: After calling `updateSelectedTile(...)`, confirm that `selectedTile` is updated correctly based on the provided key from `allTiles`.

## Class: Score
### Methods to Test in Isolation
- `addPoints(char[][] gameBoard)`
- `getScore()`

### Conditions That Cannot Be Tested in Isolation
- For `addPoints(char[][] gameBoard)`: After calling `addPoints(...)`, verify that the score is updated correctly based on completed rows and columns with or without windows.
- For `getScore()`: After updating the score using `addPoints(...)`, ensure that the returned score reflects all points earned from the game board.

## Class: Dice

## Methods to Test in Isolation
- `getAllDice()`: Verify that it returns the current rolled dice correctly.
- `getColorCount()`: Ensure it returns the current color count accurately.
- `getAvailableColors()`: Check that it returns the correct available colors after dice have been set.
- `setColorCount(int[] count, int whiteCount)`: Validate that it sets the color count correctly.
- `applyPresetDiceD2CP1(String a, String b, String c, String d, String e)`: Ensure that preset dice colors are applied as expected.

## Conditions That Cannot Be Tested in Isolation
- For `setAvailableColors(String key)`: The availability of colors depends on the current state of the `colorCount`, which is modified by this method based on previously set values. Therefore, it should be tested in conjunction with prior calls to `setColorCount(...)`.
- For `rollDice()`: This method initializes the state of the `rolledDice` array, which can only be evaluated in the context of the complete object state after construction, making it reliant on the constructor's execution.

**Class: Abilities**  
methods:
- addTrack()
will be tested in conjunction with get track
e.g. after addTrack("Red") is called, getTrack("Red"), should be called to check the track in fact increased
- hasAbility()
- hasBonus()
- useAbility()
will need to be tested in conjunction with getAbility()
e.g. after calling useAbility("Red"), getAbility("Red") should return one less than previously
- useBonus()
e.g. after calling useBonus("Red"), getBonus("Red") should return one less than previously
- updateBlue()
all update methods will need to be tested with relevant getter methods
e.g. given under getter methods below
- updateRed()
- updatePurple()
- updateGreen()
- updateYellow()
- getAbility()
should be tested with a specific colour and the relevant update method
e.g. after calling updateBlue() twice, getAbility("Blue") should return one 
- getTrack()
- getBonus()
will need to be called after a specific bonus is updated
e.g. after calling updateBlue(), getBonus() should return one


## Class: Abilities

## Methods to Test in Isolation
- `hasAbility(String color)`: Ensure it correctly returns whether the specified ability is unlocked based on the current ability counts.
- `hasBonus(String color)`: Check that it accurately determines if the specified bonus is available.
- `getAbility(String color)`: Validate that it returns the correct number of available abilities for a specified color.
- `getTrack(String color)`: Ensure it returns the current track value for the specified color correctly.
- `getBonus(String color)`: Check that it returns the correct number of available bonuses for a specified color.
- `addTrack(String color)`: Test that it updates the track correctly and increments abilities/bonuses as intended.

## Conditions That Cannot Be Tested in Isolation
- **`useAbility(String color)`**: This method modifies the ability count, which depends on the previous state of the abilities. Therefore, it should be tested in conjunction with other method calls, especially `addTrack(String color)` and `hasAbility(String color)`.
- **`useBonus(String color)`**: Similar to `useAbility`, this method requires a context of previous calls to determine if the bonus count has changed and how it affects gameplay.


## Class: GameState
## Methods to Test in Isolation
1. **initializeBoard()**
    - Test that the game board is initialized with all `'.'` characters.

2. **isTilePlacementValid(char[][] board, int row, int col)**
    - Test various scenarios for valid and invalid tile placements, including boundary conditions and overlapping tiles.

3. **placeTile(int row, int col)**
    - Test that a tile is correctly placed on the game board if the placement is valid.

4. **placeTileWithRotationWindows(int col, int row, int rotation, boolean[] windows)**
    - Test that the tile is rotated and placed correctly based on the specified rotation and window states.

5. **printBoard(char[][] board)**
    - Test that the board is printed correctly to the console.

## Conditions That Cannot Be Tested in Isolation
- **Interaction of `placeTile` and `isTilePlacementValid`:**
    - After calling `placeTile(...)`, verify that the game board reflects the expected tile placement, indicating that `isTilePlacementValid(...)` has been correctly integrated.

- **Integration of Tile and Dice Objects:**
    - Ensure that after a tile is placed, the available colors in the dice object reflect the tiles used, validating the relationship between these components.