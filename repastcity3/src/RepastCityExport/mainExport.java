/** Tahina Ralitera 
 * 
 * Main class
 * 
 * */

package RepastCityExport;

import java.io.FileNotFoundException;

public class mainExport {
	public mainExport(MyAgentListener listen) throws FileNotFoundException{
		new ExportSimulation("simulation.kml",listen); 
	}
}