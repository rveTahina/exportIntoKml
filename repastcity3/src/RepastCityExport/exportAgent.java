/** Tahina Ralitera 
 * 
 * 
 * */
package RepastCityExport;

import java.time.Instant;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.space.gis.Geography;
import repast.simphony.util.collections.IndexedIterable;
import repastcity3.agent.IAgent;

public class exportAgent extends Export{

	int agentNumber; // the number of agent
	List<com.vividsolutions.jts.geom.Coordinate> current; // A List that contains agent's coordinates; 
	List<Instant> currentListTmstp; // A list that contains the instant . ex: 2015-05-16T08:18:00.803Z
	int step=1; // define the interval at which we sample the agent trajectory . This is to reduce the .kml file size
	Context<IAgent>  agentContext;
	public static Geography<IAgent> 	agentGeography;
	IndexedIterable<IAgent> agentList;


	public exportAgent(String nam){
		name=nam;
	}

	public void export(){
	}





}
