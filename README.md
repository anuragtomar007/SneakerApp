# SneakerApp

SneakerApp (SneakerShip) is an Android app that allows users to explore a collection of top sneakers, view their details, and add them to a cart for checkout. The app follows the MVVM architecture and provides a user-friendly interface to interact with sneaker data.

## Features

### Sneaker List Screen

The app displays a grid of the top 100 sneakers. Each grid item includes an image, price, and name of the sneaker. Users can search for specific sneakers by name. Tapping on a sneaker item takes the user to the sneaker details page.

### Sneaker Details Screen

The sneaker details page provides detailed information about a selected sneaker. The following details are shown:

- Title
- Name
- Image
- Brand
- Year of release
- Price

In addition to these details, users have the option to customize their selection by choosing from available colorway and size options. This allows users to tailor their sneaker choice to their preferences before adding it to their cart.

Users can then click the "Add to Cart" button to finalize their selection.

### Checkout Screen

The checkout page displays a list of sneakers that have been added to the cart. Each cart item includes the sneaker image and price. The total price of all items in the cart is also displayed at the bottom of the screen. Users can remove items from the cart.

## Decisions and Assumptions

- **Architecture:** The app is built using the MVVM architecture.

- **Data Source:** Dummy data is used for demonstration purposes.

- **UI Design:** The app follows a clean and intuitive Material Design-based UI.

- **Image Loading:** Glide is used to efficiently load and display sneaker images.

- **Search Functionality:** The app allows users to find specific sneakers by name.

- **Currency Formatting:** The app assumes a single currency format for displaying prices.

- **Error Handling:** Error handling for network requests and data parsing should be implemented in a production version.

## Getting Started

To run the app locally, follow these steps:

1. Clone the repository: `git clone https://github.com/anuragtomar007/SneakerApp.git`
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

## Future Enhancements

Future enhancements could include:

- User Authentication
- Real-time Data Integration
- Payment Integration
- Localization
- Caching
- Testing

## Credits

The SneakerShip app was developed by Anurag Tomar as a sample project.
