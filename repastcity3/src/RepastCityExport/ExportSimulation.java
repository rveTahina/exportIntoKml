/** Tahina Ralitera 
 * 
 * 
 * */

package RepastCityExport;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import repastcity3.agent.IAgent;
import repastcity3.main.ContextManager;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;

public class ExportSimulation extends exportAgent{
	private HashMap<String, ArrayList<com.vividsolutions.jts.geom.Coordinate>> allPath = new HashMap<String, ArrayList<com.vividsolutions.jts.geom.Coordinate>>();
	private HashMap<String, ArrayList<Instant>> allInstant = new HashMap<String, ArrayList<Instant>>();

	public ExportSimulation(String nam, MyAgentListener listen) throws FileNotFoundException {
		super(nam);
		// TODO Auto-generated constructor stub

		allPath=listen.getAgentPath();
		allInstant=listen.getAgentTimeStamp();
		Instant dt = Instant.now();
		agentList=ContextManager.getAgentContext().getObjects(IAgent.class);

		for(IAgent ag:agentList){
			String couleurDefaultAgent;//Color of the agent, for the display
			// pick up the agent color
			couleurDefaultAgent=generateColor();
			current=allPath.get(ag.toString());
			currentListTmstp=allInstant.get(ag.toString());
			for(int j = 0; j < current.size(); j=j+step){
				export("http://maps.google.com/mapfiles/kml/shapes/man.png",current, currentListTmstp, document, dt, j,couleurDefaultAgent, ag);
			}
		}

		kml.marshal(new File("./FileOutput/"+name));
		System.out.println("\n Data stored in FileOutput/"+name);

	}

	//Create placemark
	Placemark createPlacemark(Geometry geom, String name, String description, Instant dt){
		Placemark placemark = KmlFactory.createPlacemark(); //Create a placemark
		placemark.setGeometry(geom);
		placemark.setDescription(description);
		placemark.createAndSetTimeStamp().setWhen(dt.toString()); //Create <TimeStamp><When></When></TimeStamp> tags and put value inside
		return placemark;
	}

	public void export(String link,List<com.vividsolutions.jts.geom.Coordinate> current,List<Instant> currentListTmstp, Document document, Instant dt, int j, String color, IAgent ag){

		Point point=createPoint(new Coordinate(current.get(j).x,current.get(j).y));
		dt=currentListTmstp.get(j);
		Placemark placemark=createPlacemark(point, ag.toString(), ag.toString(), dt); //create the placemark
		createIcon(placemark, color,link); // create the placemark icon
		document.addToFeature(placemark);

	}

}
