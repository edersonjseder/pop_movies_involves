# Pop Movies

The app that shows a list of movies using the tmdb API and the data about them on details. Here you will find a project that aims to show through REST service request a movies list on screen.
___________________________________________________________________________________________________________________________

Technologies:
    Java 8
    Android SDK
    Retrofit 2 REST library
    Data manipulation layer
    Butterknife library
    Lifecycle Architecture component
    Glide
    Android material
___________________________________________________________________________________________________________________________

Data request

The actual implementation uses REST request to fetch data to the app. The data comes in JSON format including the URL to the images showed on screen.
___________________________________________________________________________________________________________________________


Launching of the project:

The project was developed on Android Studio in a linux environment, so to launch the app, first the SDK path which is in my personal has to be changed to the local path of the tester.
It was generated a keystore for signing the app, using the command:
'keytool -genkey -v -keystore movies.keystore -alias pop_movies -keyalg RSA -keysize 2048 -validity 10000'
The keystore is in the root directory of the project, so to test if the signing is fine, click the Gradle option on the right side of the IDE and open app -> other and click on the task 'validateSigningRelease'
 
It was done the SHA certificate fingerprint as well with the command:
'keytool -list -v -keystore movies.keystore -alias pop_movies -storepass [password] -keypass [password]'
to be signing on GooglePlay if the app is to be uploaded there. It can also be done with Android Studio through Gradle option and app -> android and clicking the signingReport task and it will be generated the SHA certificate.

The project is properly signed already, so only the test verification through the Gradle task is ok to validate the app signed. To create a release apk, just go to the Gradle option and click app -> install and click in the installRelease gradle task (with a phone connected) and it will be generated an apk on <<ProjectPath>>\<<ModuleName>>\build\outputs\apk

As for the API_KEY of the TMDB API I used my personal, so I saved on the gradle.properties, which I put in the .gitignore file so that this sensitive information doesn't go to the public, I kindly ask you to use what you have, just put it in gradle.properties like that: API_KEY="<<api_key>>" because on the app.gradle the groovy script gets the api from there to be used with the API requests.

To generate an APK with gradle command, inside the project root, run ./gradlew assembleRelease to generate the release signed APK.
To build the app in debug mode, run ./gradlew assembleDebug to generate the debug apk that will be installed on device for testing purposes.
To generate with Android Studio, just go to the Gradle option and click app -> other and execute the assembleRelease/assembleDebug task.

And the adb install -r <<path-to-your-build-folder>>/<<yourAppName>>.apk
