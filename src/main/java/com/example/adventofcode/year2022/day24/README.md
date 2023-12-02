# Solution description
The important observation of an input is that most of the fields contain blizzards. 
In that case it should be easy to simulate all the possible positions in each point of time (as the number of possible points is going to be small).

In the solution I first move all the blizzards to the new position. After that I try to move each of the currently achieved positions to 5 potential positions - north, south, west, east and no movement. In case any of them is already occupied by a blizzard or a wall the point is not taken to the next round.
At the end of each round I check if the goal is reached. This guarantees us to find the shortest time of reaching a goal. 