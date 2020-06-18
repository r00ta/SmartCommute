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

## Repository structure

[Platform documentation](platform/README.md) 

[Android app documentation](app/README.md) 

[Deployment documentation](deploy/README.md)

## Architecture

![openpooling](https://user-images.githubusercontent.com/18282531/79280365-7a55ab00-7eb0-11ea-81da-200223b2e157.png)

## Features

1. Login and sign up of a user.

2. Every user has:
	- `Community Score` which represents the average score of the reviews of the users that shared a ride with him.
	- `Driving Score` which represents the driving score of a user from `0` to `100`. It is calculated using contextual information like the overspeeding.
	- `Eco Score` which represents how much the driver is "ecological", meaning that is following some rules that are relevant for the CO2 emictions (like harsh accelerating and nervous driving).
	This is screenshot of the homepage of the android application
  
![Scores](https://user-images.githubusercontent.com/18282531/85065349-4a2ad100-b1ad-11ea-87bd-c2b2b5e73a3c.png)

3. Record occasional trips to improve the driving style and the scores.

4. Visualize the trips, with the information about the overspeeding. The trip visualization is improved using the [route matching service provided by HERE](https://developer.here.com/documentation/route-match/dev_guide/topics/quick-start-gps-trace-route.html).

5. Create a recurrent route.

6. Record a recurrent trip, so that the system can analyze it and find the matchings between users.

7. 

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
