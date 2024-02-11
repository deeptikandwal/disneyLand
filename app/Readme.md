DisneyLand : Meet your favorite Disney character

The project shows a list of Disney characters with their details. 
This project is made using CLEAN architecture, where application code is divided into three layers namely data, domain and presentation layer. 
Hilt is used for injection dependencies and retrofit is used for making api calls.

The Presentation layer comprises of ui logic which is designed using jetpack compose library and
internally MVI pattern is used for state management and unidirectional flow of state from model to view.
Reusable components are used for achieving modularity. Dark and light theme is provided for the app.

The Domain layer is made of usecase and repository. 
Repository acts as an interface between data layer and the whole application code.
Application is not aware of the data source, whether data is coming from api calls or database.

The third layer is data layer that handles our data source. It contains implementation logic of for repository.

Key Features of  App:
1.	Kotlin coroutines are used for asynchronous operation
2.	Retrofit for networking
3.	Jetpack compose
4.	MVI pattern + CLEAN Architecture
