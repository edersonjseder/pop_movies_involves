# Pop Movies

This project is an Android application that shows a list of movies using REST request to TMDB API and the details about them such as release date, ratings, overviews, trailers and users reviews if available.
___________________________________________________________________________________________________________________________

**Technologies:**
   - Android SDK with Java 8
   - Retrofit 2 REST library
   - Data manipulation layer
   - Butterknife library
   - Lifecycle Architecture component
   - Glide
   - Android material
___________________________________________________________________________________________________________________________

**Data request:**

The actual implementation uses REST request to fetch data to the app. The data comes in JSON format including the URL to the images showed on screen.
___________________________________________________________________________________________________________________________


**Launching the project:**

The project was developed on Android Studio in a linux environment, so to launch the app, first the SDK path which is in my personal directory has to be changed to the local path of the person who will test it. It can be done either by changing the path at *sdk.dir* in *local.properties* file or with Android Studio which will show a dialog with the current path informing you the need to change, so when clicking 'OK' it will do that easily when the project is first opened.

The project is properly signed already, so on Android Studio just do the test verification with a gradle task to validate the app signed by clicking the Gradle option on the right side of the IDE: _app -> other_ and click on the task _'validateSigningRelease'_ with the _release_ variant selected. It can be done with the Debug variant as well by clicking in the _'validateSigningDebug'_ task or by running *./gradlew validateSigningDebug/validateSigningRelease* task in the project root directory.

As for the API_KEY of the TMDB API I used my personal, so I saved on the gradle.properties, which I put in the .gitignore file so that this sensitive information doesn't go to the public, I kindly ask you to use what you have, just put it in gradle.properties like that: API_KEY="&lt;&lt;api_key&gt;&gt;" because on the app.gradle the groovy script gets the api from there to be used with the API requests.

To build the app by using Gradle only with no Android Studio, go to the root of the project _'(&lt;&lt;your_directory&gt;&gt;/pop_movies)'_ and run _'./gradlew build'_ command. The Gradle will build the project and show something like this:<br/>

<font color="green">*Task :app:lint<br/>
Ran lint on variant release: 73 issues found<br/>
Ran lint on variant debug: 73 issues found<br/>
Wrote HTML report to file:///home/ederson/Documents/Popular_movies_PS/Pop_movies/app/build/reports/lint-results.html<br/>
Wrote XML report to file:///home/ederson/Documents/Popular_movies_PS/Pop_movies/app/build/reports/lint-results.xml<br/>
<br/>
BUILD SUCCESSFUL in 8s<br/>
56 actionable tasks: 13 executed, 43 up-to-date*</font><br/>

Then go to the _'&lt;&lt;your_directory&gt;&gt;/pop_movies/app/build/outputs/apk'_ and there will be tow folders: _debug_ and _release_, when you go inside release there will be the Pop_movies-release-1.0.apk generated or if you go inside debug there will be Pop_movies-debug-1.0.apk, then if you have adb set in your environment variables, just execute adb install Pop_movies-{variant}-1.0.apk and if you don't go to your android sdk directory _(~/Android/Sdk/platform-tools)_ and execute _./adb install ~/&lt;&lt;your_path&gt;&gt;/pop_movies/app/build/outputs/apk/release/Pop_movies-{variant}-1.0.apk_ so that it can be installed on device.<br/>
It can also be done as well inside the project root as follows: run _'./gradlew assembleRelease'_ to generate the release signed APK or run _'./gradlew assembleDebug'_ to generate the debug apk that will be installed on device for testing purposes.<br/>

To generate with Android Studio, just go to the Gradle option and click _app -> other_ and execute the _'assembleRelease/assembleDebug'_ task.

It will be generated an apk on _&lt;&lt;ProjectPath&gt;&gt;/&lt;&lt;ModuleName&gt;&gt;/build/outputs/apk_ and then run adb install -r _&lt;&lt;path_to_your_build_folder&gt;&gt;/&lt;&lt;yourAppName&gt;&gt;.apk_

To install the release apk with Android Studio, go to the Gradle option and click _app -> install_ and then in the _'installRelease'_ gradle task (with a phone connected) and it will be installed on the device.

I generated a keystore for signing the app, using the command:\
_'keytool -genkey -v -keystore movies.keystore -alias pop_movies -keyalg RSA -keysize 2048 -validity 10000'_<br/>
The keystore was generated in my personal directory path and I put it in the root directory of the project, so that the test can work fine.
 
A SHA certificate fingerprint can be easily generated as well with the command:\
_'keytool -list -v -keystore movies.keystore -alias pop_movies -storepass [password] -keypass [password]'_ <br/>
so that the app can be uploaded on GooglePlay with this signature.<br/> It can also be done with Android Studio with gradle option by clicking _app -> android_ and then the _'signingReport'_ task and it will be generated.
