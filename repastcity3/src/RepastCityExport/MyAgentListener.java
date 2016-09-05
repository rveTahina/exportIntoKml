/** Tahina Ralitera 
 * 
 * Pick up agent position and time
 * 
 * */

package RepastCityExport;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.space.gis.Geography;
import repast.simphony.util.collections.IndexedIterable;
import repastcity3.agent.IAgent;
import repastcity3.main.ContextManager;

import com.vividsolutions.jts.geom.Coordinate;

public class MyAgentListener {
	int agentNumber; // the number of agent
	List<com.vividsolutions.jts.geom.Coordinate> current; // A List that contains agent's coordinates; 
	List<Instant> currentListTmstp; // A list that contains the instant . ex: 2015-05-16T08:18:00.803Z
	int step=1; // define the interval at which we sample the agent trajectory . This is to reduce the .kml file size
	public static Geography<IAgent> 	agentGeography; // Projection
	IndexedIterable<IAgent> agentList; // List of agent
	private Coordinate currentCoords; // Current position of an agent
	private HashMap<String, ArrayList<Coordinate>> path = new HashMap<String, ArrayList<Coordinate>>();
	private HashMap<String, ArrayList<Instant>> inst = new HashMap<String, ArrayList<Instant>>();

	public MyAgentListener(){
		agentList=ContextManager.getAgentContext().getObjects(IAgent.class); // Pick up all agents in the RepastCity agentContext and store it in agentList

		ISchedule schedule = RunEnvironment.getInstance().getCurrentSchedule();
		schedule.schedule(ScheduleParameters.createRepeating(1, 10,
				ScheduleParameters.FIRST_PRIORITY), this, "setAgentPath"); // Call the setAgentPath method every 10 interval

	}

	
	public void setAgentPath(){
		for(IAgent a:agentList){
			currentCoords=ContextManager.getAgentGeography().getGeometry(a).getCoordinate(); //pick up the current position of the agent
			setAllAgentPath(a.toString(), currentCoords, Instant.now());
		}
	}

	void setAllAgentPath(String str, Coordinate curr, Instant instant){
		if(path.containsKey(str)){
			ArrayList<Coordinate> temp = path.get(str);
			ArrayList<Instant> in = inst.get(str);
			temp.add(curr);
			in.add(instant);
			path.replace(str, temp);
			inst.replace(str, in);
		}else{	
			ArrayList<Coordinate> temp = new ArrayList<Coordinate>();
			ArrayList<Instant> in = new ArrayList<Instant>();
			temp.add(curr);
			in.add(instant);
			path.put(str, temp);
			inst.put(str, in);
		}
	}

	public HashMap<String, ArrayList<Coordinate>> getAgentPath(){
		return path;
	}

	public HashMap<String, ArrayList<Instant>> getAgentTimeStamp(){
		return inst;
	}

}
