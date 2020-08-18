# Wonka
Welcome to the most awesomest chocolate factory in the galaxy!!!1!1

## Installing and running

This project has been bootstrapped with [Leiningen](https://leiningen.org/). 

To run it, execute the command:
 
    $ lein run

To run the tests:

    $ lein test
    
## Input

Input for this program is set at [resources/input.txt](./resources/input.txt), on the form of an arbitrary number
of lines; each line has a single integer which represents the height of a wall on the track.

## Solution

First of all I tried a brute-force solution, comparing every tile on the list to all others to match heights and try to find
valleys. Of course that didn't work very well for large inputs, but it did help me to eventually come up with a solution based
on **streaks**.

In this algorithm, a streak is defined as a sequence of contiguous walls on the track, such that the first wall is a peak
(that is, a wall with height greater than 0) and its last wall has a height greater than or equal to the first wall
on the streak. 

Upon finding the end of a streak, we can calculate the area of the streak (# of walls X height of the shorter end)
and subtract the summation of the heights of the walls in between the ends to find the actual area. Because we calculate the area based on
the shorter end, we account for the overflowing chocolate on that end.