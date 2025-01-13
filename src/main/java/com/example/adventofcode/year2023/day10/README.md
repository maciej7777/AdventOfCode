This task can be solved in multiple ways. I decided to create a set of points on the left and right side of the line. 
By definition of cycle one of those sets contains only internal points and the other one will contain only external points.
After that I can use bfs to find out which set contains external points and then use bfs again on all the points that I need to check
in order to find out if I can reach internal points or point outside the map (which gives me the answer).

Some other solutions are to increase the scale twice (and be able to do the bfs to outside the map then) or to do ray 
detection for each point (and count how many times it intersects with the cycle). This solution is very interesting, 
it is worth to check the solutions thread - https://www.reddit.com/r/adventofcode/comments/18evyu9/2023_day_10_solutions/.