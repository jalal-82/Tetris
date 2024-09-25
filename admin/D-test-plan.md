# Test Plan

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

### Methods to Test in Isolation
- `getAllDice()`
- `getColorCount()`
- `getAvailableColors()`
- `setColorCount(int[] count, int whiteCount)`
- `applyPresetDiceD2CP1(String a, String b, String c, String d, String e)`

### Conditions That Cannot Be Tested in Isolation
- For `setAvailableColors(String key)`: The availability of colors depends on the current state of the `colorCount`, which is modified by this method based on previously set values. Therefore, it should be tested in conjunction with prior calls to `setColorCount(...)`.
- For `rollDice()`: This method initializes the state of the `rolledDice` array, which can only be evaluated in the context of the complete object state after construction, making it reliant on the constructor's execution.


## Class: Abilities

### Methods to Test in Isolation
- `hasAbility(String color)`
- `hasBonus(String color)`
- `getAbility(String color)`
- `getTrack(String color)`
- `getBonus(String color)`
- `addTrack(String color)`

### Conditions That Cannot Be Tested in Isolation
- `useAbility(String color)`: This method modifies the ability count, which depends on the previous state of the abilities. Therefore, it should be tested in conjunction with other method calls, especially `addTrack(String color)` and `hasAbility(String color)`.
- `useBonus(String color)`: Similar to `useAbility`, this method requires a context of previous calls to determine if the bonus count has changed and how it affects gameplay.
- `updateBlue()`: all update methods will need to be tested with relevant getter methods. For example, after calling updateBlue() twice, getAbility("Blue") should return `1`
- `updateRed()` : see updateBlue
- `updatePurple()` : see updateBlue
- `updateGreen()` : see updateBlue
- `updateYellow()` : see updateBlue

## Class: GameState

### Methods to Test in Isolation
- `initializeBoard()`: Ensure that the game board is initialized with all '.' characters.
- `isTilePlacementValid(char[][] board, int row, int col)`: Test various scenarios for valid and invalid tile placements, including boundary conditions and overlapping tiles.
- `placeTile(int row, int col)`: Verify that a tile is correctly placed on the game board if the placement is valid.
- `placeTileWithRotationWindows(int col, int row, int rotation, boolean[] windows)`: Check that the tile is rotated and placed correctly based on the specified rotation and window states.
- `printBoard(char[][] board)`: Validate that the board is printed correctly to the console.

### Conditions That Cannot Be Tested in Isolation
- For `placeTile(int row, int col)`: After calling `placeTile(...)`, verify that the game board reflects the expected tile placement, indicating that `isTilePlacementValid(...)` has been correctly integrated.
- For integration of Tile and Dice Objects: After a tile is placed, ensure that the available colors in the dice object reflect the tiles used, validating the relationship between these components.