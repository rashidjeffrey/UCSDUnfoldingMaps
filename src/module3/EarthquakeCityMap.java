package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/**
 * EarthquakeCityMap An application with an interactive map displaying
 * earthquake data. Author: UC San Diego Intermediate Software Development MOOC
 * team
 * 
 * @author Rashid Jeffrey Date: 20151008
 */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this. It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;

	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	// Here is an example of how to use Processing's color method to
	// generate
	// an int that represents the color yellow.
	int yellow = color(255, 255, 0);
	int blue = color(0, 0, 255);
	int red = color(255, 0, 0);
	int white = color(255, 255, 255);
	int black = color(0, 0, 0);
	int silver = color(192,192,192);

	/**
	 * This is where to find the local tiles, for working without an Internet
	 * connection
	 */
	public static String mbTilesString = "blankLight-1-3.mbtiles";

	// The map
	private UnfoldingMap map;

	// feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
			earthquakesURL = "2.5_week.atom"; // Same feed, saved Aug 7, 2015,
												// for working offline
		} else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			// earthquakesURL = "2.5_week.atom";
		}

		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);

		// The List you will populate with new SimplePointMarkers
		List<Marker> markers = new ArrayList<Marker>();

		// Use provided parser to collect properties for each earthquake
		// PointFeatures have a getLocation method
		List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);

		// These print statements show you (1) all of the relevant properties
		// in the features, and (2) how to get one property and use it
		if (earthquakes.size() > 0) {
			PointFeature f = earthquakes.get(0);
			System.out.println(f.getProperties());
			Object magObj = f.getProperty("magnitude");
			float mag = Float.parseFloat(magObj.toString());
			// PointFeatures also have a getLocation method
		}

		// Create the markers and attach them to the map
		// We have a list of all the earthquakes (see earthquakes ArrayList),
		// so now we need to convert the earthquakes into markers.
		//
		// Do this using the helper method createMarker which takes a
		// PointFeature and converts it to a SimplePointMarker.
		// List<Marker> can store SimplePointMarker objects.
		// We should change the appearance of the markers based on the magnitude
		// (see mag float variable)
		// within the helper method
		for (PointFeature pointFeature : earthquakes) {
			SimplePointMarker simplePointMarker = createMarker(pointFeature);

			Object magObj = pointFeature.getProperty("magnitude");
			float mag = Float.parseFloat(magObj.toString());

			// Minor earthquakes (less than magnitude 4.0) will have blue
			// markers and be small.
			if (mag < 4.0) {
				simplePointMarker.setRadius(5);
				simplePointMarker.setColor(blue);
			}
			// Light earthquakes (between 4.0-4.9) will have yellow markers and
			// be medium.
			else if (mag >= 4.0 && mag <= 4.9) {
				simplePointMarker.setRadius(10);
				simplePointMarker.setColor(yellow);
			}
			// Moderate and higher earthquakes (5.0 and over) will have red
			// markers and be largest.
			else {
				simplePointMarker.setRadius(15);
				simplePointMarker.setColor(red);

			}

			markers.add(simplePointMarker);
		}

		map.addMarkers(markers);

	}

	// A suggested helper method that takes in an earthquake feature and
	// returns a SimplePointMarker for that earthquake
	private SimplePointMarker createMarker(PointFeature feature) {
		// finish implementing and use this method, if it helps.
		return new SimplePointMarker(feature.getLocation());
	}

	public void draw() {
		background(10);
		map.draw();
		addKey();
	}

	// helper method to draw key in GUI
	// Draw the legend on the left hand side using the Processing library
	// graphic methods
	private void addKey() {
		// Remember you can use Processing's graphics methods here
		// text(), rect(), ellipse(), and fill()

		fill(silver);
		rect(20, 60, 150, 300);	
		
		textSize(15);
		fill(white);
		text("Earthquake Key", 35, 90);
		
		fill(red);
		ellipse(35, 120, 15, 15);

		fill(yellow);
		ellipse(35, 150, 10, 10);
		
		fill(blue);
		ellipse(35, 180, 5, 5);
		
		textSize(15);
		fill(white);
		text("5.0+ mag", 50, 120);

		textSize(15);
		fill(white);
		text("4.0+ mag", 50, 150);

		textSize(15);
		fill(white);
		text("-4.0 mag", 50, 180);

	
	}
}
