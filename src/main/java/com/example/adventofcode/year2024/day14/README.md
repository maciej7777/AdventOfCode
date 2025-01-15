# Solution
Part 1 of the task is a simple simulation of robot positions over time. Part 2 is much mor interesting. Finding a 
Christmas tree built from drones in the image is a task that is not strictly defined, and multiple people can achieve it
in a different way. In my solution I decided to check for a setup in my simulation when there are no two robots placed 
in exactly same position, as I assumed that we need all of them in unique position to draw a picture. This works out 
nicely and already the first configuration found is the searched one. People had also other ideas including:
- counting threshold of grouped robots in area
- low safety factor (https://www.reddit.com/r/adventofcode/comments/1he0g67/2024_day_14_part_2_the_clue_was_in_part_1/)
- searching for a line of x (for example 5) robots standing in line next to each other
- chinise theorem based on the variances of x and y axes being independent to each other
(https://www.reddit.com/r/adventofcode/comments/1he0asr/2024_day_14_part_2_why_have_fun_with_image/)

More ideas can be found in the solutions page: https://www.reddit.com/r/adventofcode/comments/1hdvhvu/2024_day_14_solutions/
One more discussion about this problem is also worth reading on Reddit: https://www.reddit.com/r/adventofcode/comments/1hehu14/unpopular_opinion_day_14_part_2s_problem_was_great/