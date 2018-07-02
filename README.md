# TravellingAntColonySalesman

This is a school project for my Object Oriented Programming course. The program attempts to find the optimal route for a user submitted text file. It utilises Ant Colony Simulation in solving the travelling salesman problem (TSP) (Dorigo & Gambardella 1997). This project was ranked as best in class.

Features

  -Reads .txt files. The format is "name x y" and each city is on it's own line. Where name defines the city, and x and y are coordinates   defining the location of the cities in 2-dimensional space.
  
  -Reliable at solving TSPs that contain less than 100 cities. Variance between results and total time of the simulation increases 
  heavily after that.
 
  -Window for setting parameters
 
  -Window that draws the shortest route found. The dots on the window are scaled, so it doesn't matter if the x and y coordinates range 
  from 0 to 100 or from 3534 to 634256
  
  ![img](https://i.imgur.com/sxM2HOF.png)

  
References

Dorigo, M. & Gambardella, L. M. 1997. Ant colony system: a cooperative learning approach to the traveling salesman problem.  IEEE Transactions on Evolutionary Computation, 1(1), 53-66.).



