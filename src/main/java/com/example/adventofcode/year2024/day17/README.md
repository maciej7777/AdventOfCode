# Solution
Part 1 of the task is a simple calculation of operations defined in the task. Part 2 is more interesting, as we need to 
revert the operations and find the value in register A that would make the program to copy itself. The key element of my 
solution was to realise that thanks to the module operation we can actually test with brute force only 8 possible 
numbers on each position. In practice, we can find more than 1 result for a given position - that is the reason why we 
do the operation recursively for all the results. At the end we also need to sort the results found, so that we can 
return the smallest one.

# Interesting facts
Some programs can be very concise for this task:
- https://topaz.github.io/paste/#XQAAAQAUAwAAAAAAAAA0m0pnuFI8c/T1e9wGkBxKUSvtu9yE5JkeaJpg180hv2o353qEPkvxmBObZAGYJ7uZAbUF4paiK1fSbH9YmXuO9j+BCvnDrN0RrNBqIFrq0FvNfScUvpsViQi1dblywbINJZQC+xGOQ2ZKNGHtoAVKdS4quwl2X8PDSpq5eps6f6bND7QyH/Nxxs89XileDDQNbxC+jZYouK8eSwuKx9EHo5pJFhdEE77GfvtITddDMp6sNprHoH/RduYoaGmZ+3ru7/ggMwibScmtlnTysXRMpsrJqzrVYmlFq/920IwIPqFtJoYKVNcex9h2juf+jHt05diKEVUWClIoHB5cvKoV7O7BoQxSaA5IzjQTqZY50cb6ycLfR9d24NOfAKfm/LghrULCxSfKen8To+kwGIshXuYaP7t3QPP5vXcpqGiJZQfHRZemjxfmzbJG9hGjYoq3bOK31+63r279L5C4vo+2o7pZhdpob3UZeeSRWLEoQ9IQ5or6ns9yx+WJ3edH7c3JcyOUDTRAbVv8/wE1WKGa/04i4AA=
- https://github.com/GassaFM/aoc2024/blob/main/day17/d17p1.d