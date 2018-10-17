# Zenjob

This project consumes Zenjob's staging APIs and displays latest offers available for a particular candidate. The candidate can decline any offer or check its details.

## Architecture
Android studio v3.2 used for development. Project is written in Kotlin and uses MVVM design pattern with android architectural components. 

## Libraries
### app:
1) Support Libs: AppCompat, recyclerview, design, support-annotations are used to provide backward support.
2) Kotlin : I used Kotlin as a language.
3) Arch Lifecycle: lifecycle extensions to achieve the MVVM pattern
4) Dependency Injection: Dagger2 for managing dependencies.
5) Okhttp and Retrofit: for networking purpose
6) Kotshi with moshi: for JSON Parsing
7) Rxjava2 & RxKotlin: for asynchronous tasks management functional reactive programming by Rx was used  

### test:
1) Junit & Robolectric: Used to write unit test cases.
2) Mocking: used mockito-kotlin

### androidTest
1) Espresso: core, rules, runner with orchestrator are used for the UITests.


## Information:
When You launch Zenjob app it will show you the login screen with username and password field. Enter them and click login button it will take you to offers list screen. 

The login success will save the auth token coming in response to shared preferences and rest other web service after login screen will use it for authorization using retrofit interceptor. At any point if the unauthorized error occurs then login screen will be launched with clearing all other activities. Please check the Network module regarding it. 

I tried to follow the UI design shown in Zeplin but could get the image assets so I used vector drawables wherever I thought they are useful.

The offers list page contains available open vacancies and you can either checkout its details or just select 'not interested'. 

I tried not to over-engineer the project and used a limited set of suitable libraries. If in-case we need to download and cache image then I would suggest to go with Glide. We can use internationalization too if multiple language support is required.

Happy Coding!