# Zemoga Mobile Test

Within the features required by the test, all were completed:
- Load the ***posts*** from the JSON API and populate the sidebar.
-  The first 20 posts should have a blue dot indicator.
-  Remove the blue dot indicator once the related post is read.
-  Once a post is touched, its related content is shown in the main content area.
-  The related content also displays the user information.
-  Add a button in the navigation. It adds the current post to favorites.
-  Each cell should have the functionality to swipe and delete the post.
-  Add a button to the footer that removes all posts.
-  Add a button to navigation that reloads all posts.
-  Add a segmented control to filter posts (All / Favorites) 11.Favorite posts should have a star indicator.

In summary, I liked the test, even if this is a small implementation, the test covers big part of mobile development environment on Android, the requirements were documented on a readable way so it makes easier for the actual implementation.

***NOTE:*** As a personal concern, the combination of view pager swipe to change the page and delete rows on swipe gesture could make the user to delete post unintentionally.
In my opinion, it would be better to change swipe delete for a different gesture like long press to show deleting functionality.


#### Some useful commands to execute the app:

```bash
#Execute Unit Test
$ ./gradlew testDebugUnitTest
```
```bash
#Execute Instrumented test on connected devices
$ ./gradlew connectedAndroidTest
```
```bash
#Buid apk
$ ./gradlew assembleDebug
```
```bash
#Install apk
$ ./gradlew installDebug
```

# Tech

  - Kotlin
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) and [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
  - [Navigation Component](https://developer.android.com/guide/navigation)
  - [Room](https://developer.android.com/topic/libraries/architecture/room)
  - [RXJava](https://github.com/ReactiveX/RxJava)
  - [Retrofit](https://square.github.io/retrofit/)
  - [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)
  - [Material Design](https://material.io)
  - [Koin - Dependency Injection](https://insert-koin.io/)

# Architecture
For this implementation, I decided to use a MVVM pattern given this provides high isolation of the different app layers and also, have a clear definition of each component responsibilities.

Additionally, a repository pattern was introduced to deal with the different data sources (Network and Database) that the app needs to interact with.

![Diagram](https://betabeers.com/static/uploads/blog/20190307_imagen_2.png)

