# Smart Commute
![Platform CI](https://github.com/r00ta/SmartCommute/workflows/Platform%20CI/badge.svg)
![Android App CI](https://github.com/r00ta/SmartCommute/workflows/Android%20App%20CI/badge.svg)
[![Gitter](https://img.shields.io/badge/gitter-join%20chat-1dce73.svg)](https://gitter.im/SmartCommute/community)

SmartCommute is an open-source project created by Jacopo Rota for the Call For Code 2020. 
Since the transport is responsible for nearly 30% of the EU’s total CO2 emissions [[1](https://www.europarl.europa.eu/news/en/headlines/society/20190313STO31218/co2-emissions-from-cars-facts-and-figures-infographics)], the aim of this open source project is to provide a solution to reduce the total CO2 emictions. 

Considering that
1. the **driving style** has an **impact of ~25/30% on the CO2 emictions** [[2](https://www.researchgate.net/publication/257622980_Driving_style_influence_on_car_CO2_emissions)] and
2. the **car pooling can save 100% of the CO2 emictions** of a trip,

this project aims to provide a telematics platform and a mobile app that people can use to
1. improve the driving style 
2. and find people that can share a regular based trip. The innovative component of the project is the matching between 2+ users that can share the same trip. In the following section a use case is presented

Imagine that a user commute from `A` to `D`, crossing the points `B` and `C` (for example, many people commute to the workplace by car every day at the same time more or less). There might be another user that has to go from `B` to `C` in that time range. Actually, those two users might get in contact and decide to organize the trip together (the `SmartCommute` platform might also suggest what price the passenger should pay to the driver, but it's just a suggestion and they can always agree on something different). This is one of the innovative features of this project, 'cause the users just have to drive and the platform will analyze the potential matchings between users. Once a matching is found, this is proposed to the users: if both users agree, the contact information is shared and the users can get in touch to organize the trip. 

## Repository structure

[Platform documentation](platform/README.md) 

[Android app documentation](app/README.md) 

[Deployment documentation](deploy/README.md)

## Features

1. Login and sign up of a user.

2. Every user has:
	- `Community Score` which represents the average score of the reviews of the users that shared a ride with him.
	- `Driving Score` which represents the driving score of a user from `0` to `100`. It is calculated using contextual information like the overspeeding.
	- `Eco Score` which represents how much the driver is "ecological", meaning that is following some rules that are relevant for the CO2 emictions (like harsh accelerating and nervous driving).
	This is screenshot of the homepage of the android application

3. Record occasional trips to improve the driving style and the scores.

4. Visualize the trips, with the information about the overspeeding. The trip visualization is improved using the [route matching service provided by HERE](https://developer.here.com/documentation/route-match/dev_guide/topics/quick-start-gps-trace-route.html).

5. Create a recurrent route (like a commute trip).

6. Record a recurrent trip, so that the system can analyze it and find the matchings between users.

7. Once a matching is accepted by the users, the passenger can monitor the live position of the driver when the common trip is started.

8. Visualize the recorded trips, and view the details of the trip. 

9. Get tips to improve the driving style and reduce the Co2 emictions. 

10. Additional dongles (like OBDII dongles) might be supported to provide more detailed information about the driving style. 

## Short live demonstration
https://www.youtube.com/watch?v=PjVRMoJZg4E

## References
[1] https://www.europarl.europa.eu/news/en/headlines/society/20190313STO31218/co2-emissions-from-cars-facts-and-figures-infographics

[2] https://www.researchgate.net/publication/257622980_Driving_style_influence_on_car_CO2_emissions

