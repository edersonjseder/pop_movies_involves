# Pop Movies

The app that shows a list of movies using the tmdb API and the data about them on details. Here you will find a project that aims to show through REST service request a movie list on screen.
___________________________________________________________________________________________________________________________

## Technologies:
   - Android SDK with Java 8
   - Retrofit 2 REST library
   - Data manipulation layer
   - Butterknife library
   - Lifecycle Architecture component
   - Glide
   - Android material
___________________________________________________________________________________________________________________________

## Data request

The actual implementation uses REST request to fetch data to the app. The data comes in JSON format including the URL to the images showed on screen.
___________________________________________________________________________________________________________________________


## Launching the project:

The project was developed on Android Studio in a linux environment, so to launch the app, first the SDK path which is in my personal directory has to be changed to the local path of the person who will test it. Android Studio will show a dialog with the current path and will ask if you wish to change, and then it will do that easily when the project is first opened on it when clicking 'OK'.

The project is properly signed already, so on Android Studio just do the test verification with a gradle task to validate the app signed by clicking the Gradle option on the right side of the IDE: _app -> other_ and click on the task _'validateSigningRelease'_ with the _release_ variant selected. It can be done with the Debug variant as well by clicking in the _'validateSigningDebug'_ task.

As for the API_KEY of the TMDB API I used my personal, so I saved on the gradle.properties, which I put in the .gitignore file so that this sensitive information doesn't go to the public, I kindly ask you to use what you have, just put it in gradle.properties like that: API_KEY="&lt;&lt;api_key&gt;&gt;" because on the app.gradle the groovy script gets the api from there to be used with the API requests.

To generate an APK with gradle command, inside the project root, run _'./gradlew assembleRelease'_ to generate the release signed APK.<br/>

To build the app in debug mode, run _'./gradlew assembleDebug'_ to generate the debug apk that will be installed on device for testing purposes.<br/>

To generate with Android Studio, just go to the Gradle option and click _app -> other_ and execute the _'assembleRelease/assembleDebug'_ task.

It will be generated an apk on _&lt;&lt;ProjectPath&gt;&gt;/&lt;&lt;ModuleName&gt;&gt;/build/outputs/apk_ and then run adb install -r _&lt;&lt;path_to_your_build_folder&gt;&gt;/&lt;&lt;yourAppName&gt;&gt;.apk_

To install the release apk with Android Studio, go to the Gradle option and click _app -> install_ and then in the _'installRelease'_ gradle task (with a phone connected) and it will be installed on the device.

I generated a keystore for signing the app, using the command:\
_'keytool -genkey -v -keystore movies.keystore -alias pop_movies -keyalg RSA -keysize 2048 -validity 10000'_<br/>
The keystore was generated in my personal directory path and I put it in the root directory of the project, so that the test can work fine.
 
A SHA certificate fingerprint can be easily generated as well with the command:\
_'keytool -list -v -keystore movies.keystore -alias pop_movies -storepass [password] -keypass [password]'_ <br/>
so that the app can be uploaded on GooglePlay with this signature.<br/> It can also be done with Android Studio with gradle option by clicking _app -> android_ and then the _'signingReport'_ task and it will be generated.
