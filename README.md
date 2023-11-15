# MyPokedex


MyPokedex is an Android application that allows users to visualize pokemon's and add to favorites section. The application is developed in Kotlin and follows the MVVM (Model-View-ViewModel) architecture.

## Features

- **Visualize pokemo's from API (PokeApi)**: Users can create visualize pokemon's provided by the API.
- **Input validation**: The application performs real-time validations to ensure that the register functions do not allow duplicate users
- **Like Pokemon's**: Once users lice the pokemon information, they can save it, and a listener will notify when it has been successfully saved.
- **Intuitive user interface**: The user interface is designed using XML for view development, following the principles of Material Design to provide a modern and consistent user experience.
- **MVVM architecture**: The application follows the MVVM architecture, allowing a clear separation of responsibilities and facilitating code maintenance and scalability.
- **Firebase Firestore**: Firebase Firestore is used as a cloud database to store and synchronize user pokemon's.
- **Firebase Authentication**: Firebase Authentication is used to allow users to authenticate in the application using their email and password.
- **Session checking**: The application performs session checks to ensure that only authenticated users can access and visualize pokemon's.
- **Shared Preferences**: Shared Preferences are used to store information locally, such as the user's session state.

## Technologies Used

- Kotlin: The main programming language used to develop the application.
- Android Jetpack: Android Jetpack's ViewModel component is used to implement the MVVM architecture and manage the lifecycle of activities and fragments.
- Data Binding: Data Binding is used to declaratively bind the view model data to the user interface.
- XML: XML is used for view development and defining the application's user interface.
- Firebase Firestore: Firebase Firestore is used as a cloud database to store and synchronize user posts.
- Firebase Authentication: Firebase Authentication is used to allow users to authenticate in the application using their email and password.
- Shared Preferences: Shared Preferences are used to store information locally, such as the user's session state.
- Material Design: The application follows the principles of Material Design to provide a modern and consistent user experience.

## Installation Requirements

- Android Studio: Android Studio is required to compile and run the application on an emulator or Android device.
- Android Device or Emulator: An Android device or emulator is required to run the application.

## Build Instructions

1. Clone the MyPokedex repository on your local machine.
2. Open Android Studio and select "Open an existing Android Studio project."
3. Navigate to the location where you cloned the repository and select the project folder.
4. Wait for Android Studio to import and build the project.
5. Connect an Android device or start an emulator.
6. Click the "Run" button in Android Studio to compile and run the application on the selected device or emulator.

## Contribution

If you would like to contribute to MyPokedex, follow these steps:

1. Fork the repository.
2. Create a branch with a clear description of the feature or fix you want to make.
3. Make the changes in your branch and commit them with descriptive messages.
4. Push your changes to your forked repository.
5. Open a pull request on the main repository, describing the changes made.

## Contact

If you have any questions or suggestions regarding MyPokedex, feel free to contact us at pmpedrotorres@gmail.com. We would be delighted to assist you.
