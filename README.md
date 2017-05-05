# citygml-injector

CityGML Injector is a Java software for editing a CityGML file.
The main scope of the application is to create a building's solid geometry using polygons from boundary surfaces.

Prerequisites:
* Java 8 runtime environment

Usage example:
* Use "Open..." button to open a CityGML file
* Select the "gml:polygon" item from the "CityGML Elements" list
* Select the "Generate" item from the "Actions" list
* Select the "gml:id" item from the "Attributes" list
* Use "Apply" button
* Select the "bldg:Building" item from the "CityGML Elements" list
* Select the "Generate" item from the "Actions" list
* Select the "xlink:href" item from the "Attributes" list
* Use "Apply" button
* Select the "bldg:BuildingPart" item from the "CityGML Elements" list
* Select the "Generate" item from the "Actions" list
* Select the "xlink:href" item from the "Attributes" list
* Use "Apply" button
* Use "Save" button

Result:
* The original CityGML file is overwritten
* Polygons without gml:id have a new unique gml:id
* Buildings in the city model expose solid geometry using xlinks
* BuildingParts in the city model expose solid geometry using xlinks
