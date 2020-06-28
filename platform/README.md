# SmartCommute platform

The platform is microservice based. The communication between services is message-oriented. All the services are implemented in Java, using the framework [Quarkus](https://quarkus.io/). 

With the following sections, the architecture is introduced.

## Architecture
The architecture diagram is the following: 
![openpooling (2)](https://user-images.githubusercontent.com/18282531/85943154-77564c80-b92e-11ea-93a0-cea4ef74116c.png)

The API gateway takes all API calls from clients, then routes them to the appropriate microservice with the request. The 4 main services of the platform are the following: 

1) The so called Live-trip service, that provides all the capabilities to upload a new trip (or a trip of a registered route) and the management of a live trip. The API exposed are the following: 
![Screenshot from 2020-06-28 11-04-10](https://user-images.githubusercontent.com/18282531/85943277-5a6e4900-b92f-11ea-80f3-4faef6a9692d.png)
This service at `/index.html?userId={userId}&sessionId={sessionId}` expose also a web application to monitor a live trip of a user. Once a passenger gets the notification that the trip of the driver has started, he can have a look at the current position of the driver (the android application might use a `WebView` or use the api to do the same job.). 
2) The Enrichment-Scoring service that calculate the route matching using [HERE](https://www.here.com/) services and calculates the driving score. The API exposed are the following: 
![Screenshot from 2020-06-28 11-06-48](https://user-images.githubusercontent.com/18282531/85943303-7bcf3500-b92f-11ea-9b81-bef770e073ef.png)
3) The route-analytics service that interacts with IBM watson to calculate and retrieve the matchings between users/routes. This service does not expose any API to the API gateway. 
4) The User service that manages routes, users and notifications. The API:
![Screenshot from 2020-06-28 11-07-26](https://user-images.githubusercontent.com/18282531/85943321-96091300-b92f-11ea-9d9c-5363015bcd20.png)


All the services expose the swagger specs at the endpoint `/swagger-ui`, please have a look at them for a detailed overview of the capabilities of each service. 
### Security
For the security documentation, please have a look at [this](./SECURITY.md).
