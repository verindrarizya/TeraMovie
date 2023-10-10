# TeraMovie
Teravin Android Coding Challenge

## About
Test Coding dari PT Teravin, membuat aplikasi android native (kotlin) sederhana)

## Requirement
- The minimum SDK supported is API 21.
- The app should be written in Java or Kotlin programming language.
- There are only 10 items need to show for the list.
  - The app should implement a local database to support offline mode and only use the data in the local to show it on UI.
  - The app should implement service which runs in the background to update the movies that have been stored in local storage. The updates are performed every minute by making requests to the API.
  - The app should implement Local Broadcast Manager, once the updates are completed by the Service, the Activity should display a notification stating that the old data has been deleted and new data is available.
  - The app should implement notification when the new data is available. The notification will only appear temporarily and will automatically disappear.

- The app should implement splash screen.
  - Show notification if the device not connected to network and the data on local database is empty.

- Use version control system (Git). We would like to see the Git history.
