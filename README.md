# Conference_Planner_Application
Conference Planner Application with Login, Scheduling, Sign-Up and Messaging Systems

## Setup
### JavaFX setup
Get JavaFX 11 SDK for your respective OS from [JavaFX Download](https://gluonhq.com/products/javafx/) and unzip it to a desired location. Make sure that the project itself is running a Java SDK greater than or equal to version 11. Then go to `File -> Project Structure -> Libraries` and add the JavaFX 11 SDK as a library to the project, specifically the presentation module. Make sure it points to the `lib` folder. Then go to `Run -> Edit Configurations` and add the following line `--module-path "/path/to/javafx-sdk-11.0.2/lib" --add-modules javafx.controls,javafx.fxml` to the VM options located under the `Application -> Main -> Configuration` tab. **Note that "/path/to/javafx-sdk-11.0.2/lib" needs to be replaced with where ever you unzipped your JavaFX SDK lib folder**. Click `Apply` and close the dialog. Mark the res folder and the src folder as a resource root and source root respectively inside the presentation module. Now the module will recognise all the javafx.* related code and can be run properly.

For more detailed instructions, go to the [JavaFX Setup](https://openjfx.io/openjfx-docs/#install-javafx "JavaFX Getting Started") and clicking the `JavaFX and IntelliJ -> Non-modular from IDE` link.

### JSON setup
Get JSON simple (1.1.1.jar) from [json-simple Download](https://code.google.com/archive/p/json-simple/downloads) and save it to a desired location. Then go to `File -> Project Structure -> Libraries` and add the library to **both the core and presentation modules**. Make sure it points to the .jar file itself. Click `Apply` and close the window. Now when importing `org.json.simple.*`, there should not be any import errors.
