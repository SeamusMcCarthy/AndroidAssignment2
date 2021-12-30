# AndroidAssignment2

2nd part of the Android development assignment centred around an app to display dog-friendly parks and beaches with some useful details.

For this version of the app, I have reworked several components that were present in the 1st version as well as adding some new functionality.

- Login/Register using Firebase Auth has been re-worked and now includes a logout option
- Location determination using Google Maps has been re-worked
- Swipe gestures re-worked
- New map showing all markers that have been added
- Firebase Realtime DB used for storing user entries
- Firebase Storage used for storing images
- MVP design pattern applied
- Some general tidying of the app including applying a constraint layout to the Add Walk view

## GIT approach

Work was carried out on a development branch and merged to master once a suitable point was reached.

## UX / DX

UX

- Consistent use of colour in headings
- Slightly darker colour used in buttons
- Purposeful use of colour in actions (for example, red slider on swipe to delete, green slider on archive)
- Rounded corners on buttons for pleasing visual
- User allowed to swipe to delete but also to delete via button within entries

DX

- MVP pattern applied consistently in app
- Additional functionality has defined outside the scope of the views/presenters in a separate 'helpers' package

## Installation

Clone the repo using the following command

```
git clone https://github.com/SeamusMcCarthy/AndroidAssignment2
```

## Usage

Will run on any emulator running Android 31.

## UML package/class diagram (generated from Code Iris)

![UML](Graph4.png)

## Personal Statement

I certify that this assignment/report is my own work, based on my personal study.

I also certify that this assignment has not previously been submitted for assessment (other than the original version submitted for assignment 1 of this module) and that I have not copied in part or whole or otherwise plagiarised the work of other students.
