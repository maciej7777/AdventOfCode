# Solution description

First important observation is that we can transform our initial graph of connections (with distance always equal to 1) 
into a new graph that contains only nodes with flow rate greater than 0 (and distance dependent on number of nodes in between). 
We can calculate minimal distances between those nodes (in this exercise we want to optimise the final result, so we can assume
that the min distance is always taken). 

After translating the graph we can try to run a bfs on possible node connections in the graph. Starting from the start node
we add on a queue new sequences of nodes by trying to visit all connected (and not visited) nodes. To track if a node is visited 
we use a long variable - to reduce the time for obtaining solution we can represent every visited node as a separated bit in the long.

At the end we return all solutions in a map - where we keep the long variable with visited nodes as a key and best result as a value. 
Obviously we will find many solutions with the same set of visited nodes - but we can return just the value of the best one. 
To return the best value we can just find the highest value in the map (for the part 1).

For the part 2 we can do a nice trick in order to avoid simulating the steps of elephants and human together. We can run 
the simulation with the limited time resources (as described in the task description) and receive the map of results as before.
Now we can try to combine every pair of results that visited only different nodes. The highest sum of values for that results
is the answer for hte part 2.