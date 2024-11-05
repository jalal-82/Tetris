

# The game: Copenhagen Roll & Write

The game is called
["Copenhagen Roll & Write"](https://boardgamegeek.com/boardgame/282463/copenhagen-roll-and-write).
It was published by [Queen games](https://new.queen-games.com/) in 2019,
but now appears to have disappeared from their web site. However, we
assume that they still have the copyright to the game, and note that we
are using it without express permission, and only for educational purposes.
Note that there is another game, called just "Copenhagen" (not the roll &
write version), that is similar, but that is not the game we're using.

You can find a copy of the rules booklet in the `assets` directory.
Below is a brief summary of how the game is played, but you should read
the game rules to make sure you have a clear understanding. If anything
is unclear, post a question to the course forum (though you should, of
course, first check if the same question has already been answered).

### Game overview

The game can be played by 2-4 players. Each player has their own
_score sheet_, on which they will mark the progress in building their
building facade, and completing and using their ability/bonus tracks.
There is also a common _facade sheet_, which shows the available
tiles.

Players take turns. Each turn, the current player rolls five dice with
six different colours: red, blue, purple, green, yellow and white.
The white colour acts as a wildcard (can represent any colour the
player wants). The player then uses the dice to choose an available
tile from the facade sheet, and places (draws) that tile on their
building (see description of placement rules below). Tiles of size two
and three are available in unlimited supply, but the larger tiles
(four and five) can only be used once (except with the use of special
abilities, see below): when a player uses one of those tiles, it is
crossed out on the facade sheet.
Next, all of the other players select one of the remaining dice (not
used by the player to pick a tile), and mark off one box on their
corresponding _ability track_ (again, if a white die remains, it is
a wildcard allowing the other players to mark any track they choose).
The turn then passes to the next player.

### Placing tiles

Each tile added to a player's building facade must not overlap with
any tile already placed, and at least one square of the tile must
"rest on" (i.e., be immediately above) a square that is already filled,
or the base of the building.

When a player places a tile, they also draw a "window" on all but
one of the squares covered by the tile. (Using one of the special
abilities allows the player to draw windows on all squares in the
tile; see below). The windows are important for scoring: completing
a full row or column of the building with all windows is worth twice
as many points as completing it with some windowless squares.

### Bonuses and abilities

As players advance (mark off) squares along their ability tracks, they
will unlock bonuses (shown with a +) and special abilities (shown
with a star) that they can use later (in their turns). Each unlocked
bonus or ability can only be used once.

Bonuses can be used as extra dice to select a bigger tile than the
player would otherwise be able to. For example, if a player has rolled
three blue and two other colours (not white), they would normally only
be able to choose a blue size three tile, but by using (crossing off)
a blue bonus, they can choose a size four instead, and by using two
blue bonuses, they can choose a size five. (There is no limit on how
many bonuses can be used at the same time, as long as the player has
enough unlocked and unused bonuses.)

The special abilities have different effects, depending on their colour:

*   The red ability allows the player to reroll one or more of their
	dice. (The player can choose which dice to reroll and which to keep.)
*   The blue ability allows the player to add an extra window when placing
	a tile. This means all squares on the tile will have windows.
*   The purple ability allows the player to place an extra single-square
	tile. The tile does not have a window, but using the combination of
	a purple and a blue ability allows the player to also put a window on
	the single-square tile.
*   The green ability allows the player to change any number of dice
	that have the same colour to another colour of their choice.
*   The yellow ability allows the player to pick one of the single-use
	(size four or five) tiles from the facade sheet even if that tile
	has already been used.

Note that each unlocked ability (star on the track) can be used only once,
and is crossed off when used.

In general, the player whose turn it is can used abilities at any time
during their turn, and can use as many abilities as they have available.
For example, a player can use a red ability to reroll some dice before
picking a tile to place, but can also reroll, or use the green ability
to change some dice, after they have placed their tile, but before the
other players select an ability track to mark. The player can also reroll
several times, as long as they have unlocked and unused red abilities
remaining. Likewise, a a player can use the purple (or purple and blue
combination) to place an extra single-square tile either before or after
picking and placing a tile from the facade sheet, and can place as many
extra single-square tiles as their available abilities allow.

Some rows and columns on the player's building are marked with a
coat-of-arms ("shield") in the margin. Whenever a player completes one
of these rows or columns, they get an immediate effect: they can either
add an extra single-square tile with a window, or they can advance one
of their ability tracks by two steps. Note that the coat-of-arms effect
has to be used immediately.

### Scoring and game end

Players score points as follows:

*   Completing a row of their building with windows in all squares of
	the row: 2 points.
*   Completing a row of their building, but not with windows in all
	squares of the row: 1 point.
*   Completing a column of their building with windows in all squares of
	the column: 4 points.
*   Completing a column of their building, but not with windows in all
	squares of the column: 2 points.
*   Completing one of their ability tracks: 2 points.

The game ends on the round when one player's score reaches 12. All players
who have not yet had their turn in the current round will take their turns
(so that all players have had the same number of turns) and after that the
player with the highest score is the winner.

## Graphical user interface

A graphical user interface (GUI) for the game will be provided, after the
design deliverable (week 6). An image of the GUI is shown below:

![the GUI in play](assets/screenshot.png)

The GUI has elements that represent all the components of the game: the
available dice, the selectable tiles from the facade sheet, and each
player's score sheet, and has functionality that lets the user interact
with them, for example, to select dice, select and place tiles, and so
on. However, the GUI implements none of the game's rules or logic. For
example, it does not restrict what tiles the user can select, or where
they can place them, it does not record them once placed, does not keep
track of score or turn order, and so on.

Your task is to design and implement a collection of classes that
represent and update the state of the game and enforce the game rules,
and to connect your implementation to the GUI such that the user can
play the game.
