## Project Overview
Weather App contains authentication using Firebase Authentication and API operation using OpenWeather API. There are two main features of the app, first is the displaying of the current weather and second is the displaying of weather history stored in the database.

The architecture used on this app is MVVM and Dagger Hilt as dependency injection. For API handling, I have used Retrofit and Coroutines to perform API operations. And lastly for data storage is Room Database.

## Project Setup
The API_KEY must put inside the Constants.kt file in order to fetch the weather data accordingly.
