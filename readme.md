# Spotify Search Application

Application to search music tracks and artists by Spotify Web API.

## How to run application?

1. Download project and open it in IDE e.g. [IntelIJ IDEA](https://www.jetbrains.com/idea/).
2. After run project create new user by using [Postman](https://www.getpostman.com/) application with this values:
   ````  
    Url: http://localhost:8080/users/register
    Method: POST
    Body - format type [RAW]
    {
      "username": "name",
      "password": "password"
    }
   ````

3. Go to page http://localhost:8080/ log in to app. After log in you will be redirected to swagger page where
you can find all available REST points.

## REST Points

#### User Controller

* GET /users/artist - Endpoint to add artist to user favorite collection
* GET /users/track - Endpoint to add track to user favorite collection
* GET /users/favorite-tracks - Endpoint to get list of user favorite tracks collection
* GET /users/favorite-artists - Endpoint to get list of user favorite artists collection
* POST /users/register - Register account endpoint
* DELETE /users/track-delete - Endpoint to delete track from list of user favorite tracks collection
* DELETE /users/artist-delete - Endpoint to delete artist from list of user favorite artists collection

#### Spotify Data Controller

* GET /spotify/search - Endpoint to search for tracks and artists from Spotify Web API 


## Links

[Spotify Web API Page](https://developer.spotify.com/documentation/web-api/)

[Swagger Documentation](https://swagger.io/)

[Spring Documentation](https://spring.io/docs)