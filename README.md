## Goal:

The Goal is to implement a game with two independent units – the players – communicating with each other using an API.

##### Description

When a player starts, it insects a random (whole) number and sends it to the second player as an approach of starting the game. 
The receiving player can now always choose between adding one of {-1, 0, 1} to get to a number that is divisible by 3. Divide it by three. 
The resulting whole number is then sent back to the original sender.

## Solution:

Application works and covers all features and also most cornercases. See `AcceptanceTests.java` for all tested user features.

This was implemented in `vanilla Java` (the only dependencies are Junit, mockito and log4j).

Once the server is started and a `tcp` client is connected, these are the commands available:
```
ADD_PLAYER:your_name  - to add yourself as player
ADD_MACHINE           - to add AI player (play with computer)
START                 - start game (2 players must registred. AI or human)
PLAY:number           - play a number. -1, 0 or 1 following game logic described above
STATE                 - print the current state of the game
EXIT                  - leave the game
``` 

See a little lower an example. 

Adding 2 AI players is also possible. Example:
```
ADD_MACHINE
Added AI player Machine2 to game.
ADD_MACHINE
Added AI player Machine3 to game.
START
Game started. The starting number is 111. Next to play is machine Machine2.
machine Machine2 played number 0. The result is Round result: outputNumber 37, winner false.
machine Machine3 played number -1. The result is Round result: outputNumber 12, winner false.
machine Machine2 played number 0. The result is Round result: outputNumber 4, winner false.
machine Machine3 played number -1. The result is Round result: outputNumber 1, winner true.
```