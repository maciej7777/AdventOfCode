# Solution
In this task we need to solve multiple sets of equations. For each of the problem we receive 3 variables (which are 
tuples with two values) - a, b and prize. A defines change of x and y coordinates when button a is pushed, b defines 
change of x and y coordinates when button b is pushed and prize defines coordinates x and y that we want to achieve. 
Let's define all that variables with a.x, a.y, b.x, b.y and prize.x, prize.y. Also, let's represent the number of button pushes 
with variables aPushes and bPushes. Then we can define 2 equations that we will use in both solutions:

```math
a.x * aPushes + b.x * bPushes = prize.x
a.y * aPushes + b.y * bPushes = prize.y
```

## Solving the task with solver
Solution with the solver is quite simple - we just provide both equations to the solver and define aPushes and bPushes 
as roots. The result may not be found among integers, then there is no mix of aPushes and bPushes that would allow to 
achieve prize.x and prize.y values at the same time.
## Solving the task with linear algebra
Solving the task using algebra requires few additional calculations to be done on the paper. At first we can try to 
define a relation between aPushes and bPushes based on the first equation:
```math
a.x * aPushes + b.x * bPushes = prize.x
b.x * bPushes = prize.x - a.x * aPushes
bPushes = (machine.prize.x - machine.a.x * aPushes) / machine.b.x;
```
To solve this we would still need to find a way to define an equation for aPushes that would not be dependent on bPushes.
We can do that with multiple steps. Let's start by multiplying first equation by b.y and the second one by b.x:
```math
b.y * a.x * aPushes + b.y * b.x * bPushes = prize.x * b.y 
b.x * a.y * aPushes + b.x * b.y * bPushes = prize.y * b.x
```
We can see that now both equations contain component `b.x * b.y * bPushes`. We can now subtract second equation from the 
first one (as the values on the left and right sides still need to be the same):
```math
(b.y * a.x * aPushes + b.y * b.x * bPushes) - (b.x * a.y * aPushes + b.x * b.y * bPushes) = prize.x * b.y - prize.y * b.x
```
This can be simplified to:
```math
b.y * a.x * aPushes - b.x * a.y * aPushes = prize.x * b.y - prize.y * b.x
aPushes * (b.y * a.x - b.x * a.y) = prize.x * b.y - prize.y * b.x
aPushes = (prize.x * b.y - prize.y * b.x) / (a.x * b.y - a.y * b.x)
```

And now we can just calculate aPushes by putting the numbers in place of all the variables. After that we can calculate
bPushes by using the equation that we prepared earlier.