Satellite Android App
=====================

**Satellite Android** is a fully functional Android app built entirely with Kotlin and Jetpack Compose.

# Features

Satellite Android, where satellites are displayed on a list page and filtering can be done; It is also an android application where detailed information can be viewed on a separate page by clicking on the satellites in the list.

## Screenshots

<table><tr>
<td> 
  <p align="center" style="padding: 10px">
    <img alt="Forwarding" src="https://github.com/SaidAtmaca/SatelliteAndroid/blob/development/app/src/main/res/drawable/s1.png" width="400">
    <br>
    <em style="color: grey">Home Screen</em>
  </p> 
</td>
<td> 
  <p align="center">
    <img alt="Routing" src="https://github.com/SaidAtmaca/SatelliteAndroid/blob/development/app/src/main/res/drawable/s2.png" width="400">
    <br>
    <em style="color: grey">Detail Screen</em>
  </p> 
</td>
</tr></table>

# Development Environment

**Satellite Android** uses the Gradle build system and can be imported directly into Android Studio (make sure you are using the latest stable version available [here](https://developer.android.com/studio)).

Change the run configuration to `app`.

![image](https://user-images.githubusercontent.com/873212/210559920-ef4a40c5-c8e0-478b-bb00-4879a8cf184a.png)

# Modularization

The **Satellite Android** app has been fully modularized and you can find the detailed guidance and
description of the modularization strategy used in
[modularization guidance](https://developer.android.com/topic/modularization).

## Tech stack & Open-source libraries
- Minimum SDK level 21.
- [Kotlin](https://kotlinlang.org/) based, utilizing [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous operations.
- Convention plugin in Android is a Gradle plugin that defines common configurations and rules across the project, ensuring a consistent structure across all modules.
- Jetpack Libraries:
    - Jetpack Compose: Android’s modern toolkit for declarative UI development.
    - Lifecycle: Observes Android lifecycles and manages UI states upon lifecycle changes.
    - ViewModel: Manages UI-related data and is lifecycle-aware, ensuring data survival through configuration changes.
    - Navigation: Facilitates screen navigation, complemented by [Hilt Navigation Compose](https://developer.android.com/jetpack/compose/libraries#hilt) for dependency injection.
    - Room: Constructs a database with an SQLite abstraction layer for seamless database access.
    - [Hilt](https://dagger.dev/hilt/): Facilitates dependency injection.
- Architecture:
    - MVVM Architecture (View - ViewModel - Model): Facilitates separation of concerns and promotes maintainability.
    - Repository Pattern: Acts as a mediator between different data sources and the application's business logic.


