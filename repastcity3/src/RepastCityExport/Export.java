/** Tahina Ralitera 
 * 
 * 
 * */
package RepastCityExport;

import java.awt.Color;

import repastcity3.environment.Building;
import repastcity3.main.ContextManager;
import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Boundary;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.IconStyle;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.LineStyle;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;
import de.micromata.opengis.kml.v_2_2_0.PolyStyle;
import de.micromata.opengis.kml.v_2_2_0.Polygon;
import de.micromata.opengis.kml.v_2_2_0.Units;
import de.micromata.opengis.kml.v_2_2_0.Vec2;

public abstract class Export {

	String name; //name of the output file
	de.micromata.opengis.kml.v_2_2_0.Kml kml = de.micromata.opengis.kml.v_2_2_0.KmlFactory.createKml(); //Create a kml object
	de.micromata.opengis.kml.v_2_2_0.Document document = kml.createAndSetDocument().withName("MyMarkers");	//Create the document tag

	public Export(){

	}

	//Create Polygon
	Polygon createPolygon(Building bu, Placemark placemark, int hauteur){
		Polygon poly= KmlFactory.createPolygon();
		LineStyle ls= placemark.createAndAddStyle().createAndSetLineStyle();
		ls.setWidth(1.5);
		PolyStyle pls=placemark.createAndAddStyle().createAndSetPolyStyle();
		pls.setColor(generateColor());
		poly.setExtrude(true);
		poly.setAltitudeMode(AltitudeMode.RELATIVE_TO_GROUND);
		Boundary bound=new Boundary();
		LinearRing lnr=new LinearRing();

		com.vividsolutions.jts.geom.Coordinate[] coo = ContextManager.buildingProjection.getGeometry(bu).getCoordinates();
		for(com.vividsolutions.jts.geom.Coordinate coord:coo){
			Coordinate c=new Coordinate(coord.x,coord.y,hauteur);
			lnr.addToCoordinates(c.toString());
		}

		bound.setLinearRing(lnr);
		poly.setOuterBoundaryIs(bound);

		return poly;
	}


	//Create point
	Point createPoint(Coordinate coords){
		de.micromata.opengis.kml.v_2_2_0.Point point = KmlFactory.createPoint();//Create point
		point.getCoordinates().add(coords);
		return point;
	}


	//Create placemark
	Placemark createPlacemark(Geometry geom, String name, String description){
		Placemark placemark = KmlFactory.createPlacemark(); //Create a placemark
		placemark.setGeometry(geom);
		placemark.setName(name);
		placemark.setDescription(description);
		return placemark;
	}

	//Create icon
	Icon createIcon(Placemark placemark, String color, String link){
		// IconStyle
		IconStyle ic= placemark.createAndAddStyle().createAndSetIconStyle();
		ic.setHeading(1);
		ic.setScale(0.5);
		ic.setColor(color);
		Vec2 vc=new Vec2();
		vc.setX(0);
		vc.setY(0.5);
		vc.setXunits(Units.FRACTION);
		vc.setYunits(Units.FRACTION);
		ic.setHotSpot(vc);

		//Icon
		Icon icone=ic.createAndSetIcon();
		icone.setRefreshInterval(0.5);
		icone.setViewRefreshTime(0.5);
		icone.setHref(link);

		return icone;
	}


	//Generate a rondom RGB color
	public static String generateColor(){
		int red = (int) (( Math.random()*255)+1);
		int green = (int) (( Math.random()*255)+1);
		int blue = (int) (( Math.random()*255)+1);
		Color RandomC = new Color(red,green,blue);
		int RandomRGB = (RandomC.getRGB());
		String RandomRGB2Hex = Integer.toHexString(RandomRGB);
		return RandomRGB2Hex;
	}

	//Create curve : To be done

}
