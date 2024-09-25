## Test Plan

*by Eileen:*  
**Class: GameState**  
methods:
- isTilePlacementValid()
- placeTile()

**Class: Tile**  
methods:
- isValidSelection()
- applyWindows()
- generateTiles()
- rotateTile()

**Class: Score**  
methods:
- addPoints()
- isCompleteRow()
- isAllWindowsRow()
- isCompleteColumn()
- isAllWindowsColumn()
- isWindow()

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


