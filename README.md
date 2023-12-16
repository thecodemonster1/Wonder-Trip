# Wonder Trip App

## Overview

The Wonder Trip App is a mobile application that allows users to create and share their travel experiences. Users can document their journeys, add location-based details, and customize their journal themes. This project is developed as part of Assignment 02 for HCI Project.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)

## Features

1. **User Authentication and Profile Management:**
   - Users can create accounts to manage their travel journals.
   - Personalized user profiles allow customization.

2. **Data Input and Retrieval:**
   - Users can input travel experiences, creating a timeline of their journeys.
   - The app displays travel journals using RecyclerView.

3. **External API Integration:**
   - A location-based API enhances travel details and provides local recommendations.

4. **Settings and Preferences:**
   - Users can customize journal themes.
   - Notification preferences are configurable.

## Getting Started

To get started with the Travel Journal App, follow these steps:

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/thecodemonster1/Wonder-Trip.git
   cd Wonder-Trip
   ```

2. **Set Up Firebase:**
   - Create a new Firebase project and configure it for Android.
   - Add the `google-services.json` file to the `app` directory.

3. **Configure API Key:**
   - Obtain an API key for the chosen location-based API.
   - Add the API key to the appropriate configuration file.

4. **Build and Run:**
   - Open the project in Android Studio.
   - Build and run the app on an emulator or a physical device.

## Project Structure

The project follows the following structure:

```
travel-journal-app/
|-- app/
|   |-- src/
|       |-- main/
|           |-- java/
|               |-- com.example.traveljournal/
|                   |-- authentication/
|                   |-- data/
|                   |-- ui/
|           |-- res/
|           |-- ...
|-- google-services.json
|-- ...
```

- **`authentication/`:** Contains user authentication-related code.
- **`data/`:** Includes code for data input and retrieval, Firebase integration, and API calls.
- **`ui/`:** Manages the UI components and layout files.

## Fireabase DB Structure

The Database follows the following structure:

```
users
  |--- userId
        |--- journals
              |--- journalId1
                  |--- title: "My Amazing Trip"
                  |--- date: "2023-12-13"
                  |--- rate: 4.5
                  |--- text: "This is a wonderful place..."
              |--- journalId2
                  |--- ...
```

## Technologies Used

- **Android Studio:** The official integrated development environment for Android app development.
- **Firebase:** Used for user authentication and data storage.
- **[Location-Based API]:** Integrated to enhance travel details and recommendations.

## Contributing

If you would like to contribute to the project, follow these steps:

1. Fork the repository.
2. Create a new branch for your feature: `git checkout -b feature-name`.
3. Commit your changes: `git commit -m 'Add some feature'`.
4. Push to the branch: `git push origin feature-name`.
5. Submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).
