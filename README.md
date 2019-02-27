# Pop Movies

The app that shows a list of movies using the tmdb API and the data about them on details. Here you will find a project that aims to show through REST service request a movie list on screen.
___________________________________________________________________________________________________________________________

Technologies:
   - Java 8
   - Android SDK
   - Retrofit 2 REST library
   - Data manipulation layer
   - Butterknife library
   - Lifecycle Architecture component
   - Glide
   - Android material
___________________________________________________________________________________________________________________________

Data request

The actual implementation uses REST request to fetch data to the app. The data comes in JSON format including the URL to the images showed on screen.
___________________________________________________________________________________________________________________________


Launching of the project:

The project was developed on Android Studio in a linux environment, so to launch the app, first the SDK path which is in my personal directory has to be changed to the local path of the person who will test it.

The project is properly signed already, so only do the test verification through the Gradle task to validate the app signed by clicking the Gradle option on the right side of the Android Studio IDE: _app -> other_ and click on the task _'validateSigningRelease'_ with the _release_ variant selected.

It was generated a keystore for signing the app, using the command:\
_'keytool -genkey -v -keystore movies.keystore -alias pop_movies -keyalg RSA -keysize 2048 -validity 10000'_<br/>
The keystore was generated in my personal directory path and I put it in the root directory of the project, so that the test can work fine.

To install the release apk with Android Studio, go to the Gradle option and click _app -> install_ and click in the _'installRelease'_ gradle task (with a phone connected) and it will be installed on the device.
 
It was done the SHA certificate fingerprint as well with the command:\
_'keytool -list -v -keystore movies.keystore -alias pop_movies -storepass [password] -keypass [password]'_ <br/>
to sign the app and upload it on GooglePlay.<br/> It can also be done with Android Studio through Gradle option by clicking _app -> android_ and click the _'signingReport'_ task and it will be generated the SHA certificate.

As for the API_KEY of the TMDB API I used my personal, so I saved on the gradle.properties, which I put in the .gitignore file so that this sensitive information doesn't go to the public, I kindly ask you to use what you have, just put it in gradle.properties like that: API_KEY="&lt;&lt;api_key&gt;&gt;" because on the app.gradle the groovy script gets the api from there to be used with the API requests.

To generate an APK with gradle command, inside the project root, run _'./gradlew assembleRelease'_ to generate the release signed APK.<br/>
To build the app in debug mode, run _'./gradlew assembleDebug'_ to generate the debug apk that will be installed on device for testing purposes.<br/>
To generate with Android Studio, just go to the Gradle option and click _app -> other_ and execute the _'assembleRelease/assembleDebug'_ task.

It will be generated an apk on _&lt;&lt;ProjectPath&gt;&gt;/&lt;&lt;ModuleName&gt;&gt;/build/outputs/apk_ and then run adb install -r _&lt;&lt;path_to_your_build_folder&gt;&gt;/&lt;&lt;yourAppName&gt;&gt;.apk_
