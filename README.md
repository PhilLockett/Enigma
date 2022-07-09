# Enigma

'Enigma' is a JavaFX application that simulates the Enigma machine.

## Overview
This project has been set up as a Maven project that uses JavaFX, FXML and 
CSS to render the GUI. Maven can be run from the command line as shown below.
Maven resolves dependencies and builds the application independently of an IDE.

This application simulates the [Enigma](https://en.wikipedia.org/wiki/Enigma_machine)
machine. There are no restrictions placed on the Rotor selection, as such some 
Rotor combinations can be made that may not be available on the real machine.

Note: for the Commercial, Rocket and SwissK Rotors, the current turnover points 
are guesses and may be incorrect.

## Dependencies
'Enigma' is dependent on the following:

  * Java 15.0.1
  * Apache Maven 3.6.3

The source code is structured as a standard Maven project which requires Maven 
and a JDK to be installed. A quick web search will help, alternatively
[Oracle](https://www.java.com/en/download/) and 
[Apache](https://maven.apache.org/install.html) should guide you through the
install.

Also [OpenJFX](https://openjfx.io/openjfx-docs/) can help set up your 
favourite IDE to be JavaFX compatible, however, Maven does not require this.

## Cloning and Running
The following commands clone and execute the code:

	git clone https://github.com/PhilLockett/Enigma.git
	cd Enigma/
	mvn clean javafx:run

## Points of interest
This code has the following points of interest:

  * Enigma is a Maven project that uses Maven, JavaFX, FXML and CSS.
  * Enigma is derived from the [BaseFXML](https://github.com/PhilLockett/BaseFXML) 'framework'.
  * Enigma simulates the behaviour of the [Enigma](https://en.wikipedia.org/wiki/Enigma_machine) machine.
  * Rotor combinations can be selected that may not be available on the real machine.
  * Data persistence is maintained from one session to the next.
