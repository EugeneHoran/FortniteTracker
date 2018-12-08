# FortniteTracker
Example project using modern tech. The goal of app is find what I like and dont like to create a base module. 


[Download Link via Hockeyapp](https://rink.hockeyapp.net/apps/a7579e6f046a4786a91c985ff6c45350/app_versions/5)
If you don't have an account to search: select PC and use ninja for epic username


## TODO: 
- description
- update and clean dependencies 
- ...


### Image Previews

<p float="top">
<img src="https://github.com/EugeneHoran/FortniteTracker/blob/master/images/0.jpg" width="220" />
<img src="https://github.com/EugeneHoran/FortniteTracker/blob/master/images/1.jpg" width="220" />
<img src="https://github.com/EugeneHoran/FortniteTracker/blob/master/images/2.jpg" width="220" />
<img src="https://github.com/EugeneHoran/FortniteTracker/blob/master/images/3.jpg" width="220" />
<img src="https://github.com/EugeneHoran/FortniteTracker/blob/master/images/4.jpg" width="220" />
<img src="https://github.com/EugeneHoran/FortniteTracker/blob/master/images/5.jpg" width="220" />
</p>


### Libraries

```
dependencies {
    def dagger_version = "2.16"
    def retrofit_version = "2.4.0"
    def lifecycle_version = "2.0.0"
    def room_version = "2.1.0-alpha02"
    
    implementation fileTree(include: ['*.jar'], dir: 'libs')
    
    implementation 'androidx.core:core-ktx:1.0.1'
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"

    // Android X
    implementation 'androidx.appcompat:appcompat:1.0.1'
    implementation 'androidx.vectordrawable:vectordrawable:1.0.1'
    implementation 'com.google.android.material:material:1.0.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.0-alpha2'

    // Dagger
    implementation "com.google.dagger:dagger-android:$dagger_version"
    kapt "com.google.dagger:dagger-android-processor:$dagger_version"
    kapt "com.google.dagger:dagger-compiler:$dagger_version"

    // Retro
    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-scalars:$retrofit_version"
    implementation 'com.squareup.okhttp3:logging-interceptor:3.10.0'

    // LiveData Retrofit Adapter
    implementation 'com.github.leonardoxh:retrofit2-livedata-adapter:1.1.2'

    // Room
    implementation "androidx.room:room-runtime:$room_version"
    kapt "androidx.room:room-compiler:$room_version"

    // Architecture Components
    implementation "androidx.lifecycle:lifecycle-extensions:$lifecycle_version"
    kapt "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"

    // Glide
    implementation 'com.github.bumptech.glide:glide:4.8.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.8.0'

    // Rx
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'
    implementation 'io.reactivex.rxjava2:rxjava:2.2.4'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'

    /** Libs **/
    implementation project(':togglebuttonlayout')
    implementation 'com.polyak:icon-switch:1.0.0'
    implementation 'com.github.lecho:hellocharts-android:v1.5.8'
    implementation 'com.github.RaviKoradiya:Toolbar-Center-Title:1.0.3'
    
    // Testing
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test:runner:1.1.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.1.0'
}
```
