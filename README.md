# Interface Graphique
This project is a Gradle-based Java project that includes automated tests.

**Setup**
1. Clone the repository to your local machine
2. Install Gradle if it's not already installed. You can download it [here]([URL](https://gradle.org/install/))
3. Open the project in your preferred IDE or text editor

**Running the Tests**
1. In the terminal, navigate to the project root directory
2. Run the command ./gradlew test
3. The tests will run automatically and the results will be output to the console
If any tests fail, more detailed information can be found in the build/reports/tests/test directory.

## Continuous Integration

This project uses GitHub Actions for continuous integration. The CI pipeline is triggered automatically on every push to the main branch, and it performs the following steps:

- Builds the Gradle project
- Runs unit tests and generates a test report
- Uploads the test report as an artifact { Not implemented yet }
- Deploys the application to a test environment { Not implemented yet }

To view the test report, click on the "Actions" tab above and select the latest workflow run. The test report artifact can be found under the "Artifacts" section. { Not implemented yet }

To set up CI for your own fork of this repository, you can create a new GitHub Actions workflow file in the .github/workflows directory, and customize it according to your needs.

**Contributing**
1. Fork the repository
2. Create a new branch
3. Make your changes and commit them
4. Push your changes to your forked repository
5. Create a pull request to merge your changes into the main repository

