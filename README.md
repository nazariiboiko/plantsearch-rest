# Plant Search REST API
The Plant Search REST API is a Java-based web application that provides search functionality for plant-related data through a RESTful API.

## Features
- Search plants by various criteria such as name, species, color and more.
- Retrieve detailed information about individual plants.
- Add new plants to the database.
- User registration and login functionality.
- Ability for users to add plants to their favorites.
- View information about shops that sell specific plants.

## Technologies Used
- Java 17
- Spring Framework
- Jackson
- RESTful API principles
- Maven
- PostgreSql
- Mapstruct
- AWS SDK 

## Getting Started
1. **Clone the repository:**
    ```
    git clone https://github.com/nazariiboiko/plantsearch-rest.git
    ```
2. **Configure the database:**
- Install PostgreSQL and create a new database.
- Update the database configuration in the `application-dev.yaml` file located in the `src/main/resources` directory.

3. **Configure the environment variables:**

    The following environment variables need to be set before running the application:

- `SPRING_PROFILES_ACTIVE`: Set this variable to `dev` to activate the development profile.
- `AWS_SECRET_KEY`: Set this variable to your AWS secret key.
- `AWS_ACCESS_KEY`: Set this variable to your AWS access key.
- `EMAIL_USERNAME`: Set this variable to the username of the email account used for sending emails.
- `EMAIL_PASSWORD`: Set this variable to the password of the email account used for sending emails.
4. **Build and run the application:**
- Open the project in your preferred Java IDE.
- Build the project using Maven.
- Run the application.

5. **Test the API:**

- Use a tool like Postman or cURL to send HTTP requests to the API endpoints.
- Refer to the API documentation for the available endpoints and request/response formats.

## Contributing
Contributions to the Plant Search REST API are welcome! If you find any issues or would like to suggest enhancements, please submit a GitHub issue or create a pull request with your proposed changes.

## Contact
For any questions or inquiries, please contact the project maintainer:

- Name: [Nazarii Boiko](https://github.com/nazariiboiko)
- Email: [nazariiboiko1@gmail.com](mailto:nazariiboiko2@gmail.com)
