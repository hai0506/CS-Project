# Project report

```
Hoang Hai – M
```
## Project overview

This application, named HAI (Hai’s Atmospheric Indicator) displays real-time environmental data,
consisting of weather, temperature, wind speed, humidity, pollutant index and UV index, in various
parts of Singapore. The programme aims to help users keep track of these data and carry out
appropriate preparations and activities outdoors to ensure health safety and convenience.

## Class design

There is a total of 6 classes.

Inheritance and polymorphism are used in class design. Location is an abstract
class which contains common attributes of the Station and Region classes such
as a String name and a Coordinate object, as well as related methods. Station
and Region classes both extend Location.

Forecast and Settings are separate classes with no relationships to others as
well as to each other.

All class attributes are either of protected or private access to encapsulate data.


## MVC implementation

The sample folder consists of 3 packages: Control, Model and View.

The View package contains all the .fxml files as well as all the
images used in the application.

Each .fxml file has a controller class located in the Control package.
The Splash and WeatherpaneController classes do not play any
parts in the programme. The SampleController class is the main
controller and controls the entire programme.

The Model package contains .json files stored in a folder named
“JSON”, .txt files and .properties files. All classes are also located at
the Model package.

## Features

Retrieving data from API

This application uses 7 real-time APIs from https://data.gov.sg/developer: temperature, wind
speed, humidity, weather forecast for the next 24 hours, weather forecast for the next 4 days,
pollutant index and UV index. Besides those, a geolocation API of https://freegeoip.app/ is also
used to access location of the user. HttpURLConnection is used to access the APIs, which passes in
the current date-time and receives data from the APIs in the .json format. This data is then written
into .json files.

To extract necessary data from the .json files, an external library called json-simple is used, which
can be found at [http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm.](http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm.) The data is
used to create Station and Region objects which are stored in ArrayLists.

Automatic updates

Multithreading is used to automatically update the data by retrieving them from the API. Weather
data is updated every minute, pollutant and UV index are updated every hour, and forecast is
updated every half hour. Only the last updated time of the weather is displayed on the app, as all
updates of pollutant, UV and forecast will coincide with the weather updates.


Functions

When the app starts, a splash screen appears, displaying the app icon and name, as well as progress
bar and status. After loading, the main programme is shown.

The program consists of 4 main panels: weather, PSI, UV and forecast.

Weather panel:

This panel displays the information about the stations,
including temperature, wind speed, humidity and weather.

The program loops through the list of station objects to be
displayed, and pass the data of each object into a pre-made
model pane. These panes are then added as children nodes
of a TilePane, and the TilePane is added to a ScrollPane, so
that a large number of station objects can be displayed
within a small area.

User can add or remove saved locations by double-clicking on the name of the station in a ListView.
The IDs of the saved stations are written into a file.

User can also see the stations near them by keying in a distance value and choose the unit (km /
mile). Any stations whose distance away from the user is smaller than the input will be displayed.
The haversine formular is used to calculate the distance between two locations using given
coordinates.

PSI panel:

User selects one region out of Central, North, South, West and East. The program then displays the
overall pollutant index in the region using the attributes of the selected Region object, and also
displaying the appropriate color to show the level of danger. It also shows the concentration of 6
pollutants in the atmosphere: O3, NO2, SO2, CO, PM2.5 and PM10. According to the pollutant
index, the app will give advice to the user on what to do to benefit their health, such as closing
windows or reducing outdoor activities. In addition, the user can click on a pollutant for more its
general information, short-term and long-term effects on physical health.

UV panel:

Current UV index is displayed, and the programme gives advice accordingly to ensure health
benefits. User can also click on “What is UV?” for more information about UV.

Past UV records at each hour of the day (from 7 a.m to the last updated time, until 7 p.m) are
displayed in a bar chart.


Forecast:

Information about the weather, temperature, wind speed and humidity of the 4 upcoming days are
displayed, using attributes of the Forecast objects.

Settings

The app allows user to change the unit of temperature, wind speed and language. Any changes
made to the settings alter the attributes of the Settings object of the controller class, and this object
is written to a file so that the settings are saved for the next usage. For internationalization, user
can choose between English, Spanish and Vietnamese. Translations are stored in .properties files.
Upon changing languages, the main .fxml file is reloaded with the necessary ResourceBundle, hence
the app closes and reopens.

## Testing

The application was run without Internet connection. This returned an exception, which was
handled. The app still automatically updates due to the running threads, but the data displayed no
longer renews, and the user is also informed that there is no Internet connection. However, when
the user reconnects, they must wait until the app updates itself again to receive new data.

Station and Region objects in ArrayLists are printed out and manually checked according to .json
files to ensure accurate data. Distance from the user’s location to the stations are also recalculated
using online websites.

Pollutant index and UV values are modified manually to ensure proper display of colours and health
advice for all ranges.

## Reflection

Obstacles faced

Creating the .jar file of this application was confusing. Due to the MVC organization, it was very
difficult to do file input and output properly. The required format of the directory of some files are
different from others depending on the type of file and their location in the project. Moreover, I
initially attempted to modify write into .txt files inside the .jar at runtime, however through the
Internet I learnt that these files should be modified externally.

SceneBuilder is also very susceptible to crashing mid-development. Sometimes it would
immediately freeze when dragging a controller/container into the pane, and the program must be
exited, losing unsaved work and preventing progress.

Learning points


Developing the app helped me to better understand the concepts taught in class and how to apply
them, such as multithreading and internationalization.

I also learnt to retrieve data from APIs and extracting data from .json files.

Future improvements

A map API could be used to visualize the location of the user and the stations on a map by passing
in the coordinates. However, Google Maps API requires billing, and I did not find other alternatives.

The application sometimes freezes slightly when it automatically calls the API to retrieve data. This
can be solved by making use of more multithreading.

The weather is not translated. This is because I did not have every possibilities of the weather to
translate in .properties files. This can be solved using an external API such as Google Translate.

