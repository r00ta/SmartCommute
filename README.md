# Smart Commute
![Platform CI](https://github.com/r00ta/SmartCommute/workflows/CI/badge.svg?branch=master)
![Android App CI](https://github.com/r00ta/SmartCommute/workflows/Android%20App%20CI/badge.svg)

## Architecture

![openpooling](https://user-images.githubusercontent.com/18282531/79280365-7a55ab00-7eb0-11ea-81da-200223b2e157.png)

## Features
1. real time streaming of data of the driver. People who have to be picked up can have a look at the current position of the driver. 
2. the app should record trips (manually) of the drivers. 
3. Score trips (speed limits, accelerations etc..) -> ecodrive score. 
4. Review mechanism (with stars)
5. find matchings for people on the same path.
	a. Driver A drives from X --> Y --> W --> Z. 
	b. Driver B drives from Y --> W.
	The system should propose A to pick up B and get paid for that. 
6. At sign up time:
	a. standard data of the driver
	b. What car he has
	c. Is he willing to be picked up by someone else (ok passenger?). Only driver? Only passenger? 
	d. ok to pick up somebody at departure place (radius in meter?)
	f. ok to pick up somebody on the path? (radius in meter of deviation?)
7. record a first commute trip. Ask if there were no deviations. 
8. the user might record the same trip multiple time in the next days -> will collect more points. 


## Installation 

