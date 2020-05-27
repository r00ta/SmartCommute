# Smart Commute
![Platform CI](https://github.com/r00ta/SmartCommute/workflows/Platform%20CI/badge.svg)
![Android App CI](https://github.com/r00ta/SmartCommute/workflows/Android%20App%20CI/badge.svg)

SmartCommute is an open-source project created by Jacopo Rota for the Call For Code 2020. 
Since the transport is responsible for nearly 30% of the EU’s total CO2 emissions ([1]), the aim of this open source project is to provide a solution to reduce the total CO2 emictions. 

Considering that
1. the **driving style** has an **impact of ~25/30% on the CO2 emictions** ([2]) and
2. the **car pooling can save 100% of the CO2 emictions** of a trip,

this project aims to provide a telematics platform and a mobile app that people can use to
1. improve the driving style 
2. and find people that can share a time regular based trip. The innovative component of the project is the matching between 2+ users that can share the same trip. In the following section a use case is presented

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


## References
[1] https://www.europarl.europa.eu/news/en/headlines/society/20190313STO31218/co2-emissions-from-cars-facts-and-figures-infographics

[2] https://www.researchgate.net/publication/257622980_Driving_style_influence_on_car_CO2_emissions
