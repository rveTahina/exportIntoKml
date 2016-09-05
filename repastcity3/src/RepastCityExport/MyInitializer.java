/** Tahina Ralitera 
 * 
 * Listen to any run-related events such as started, stopped, paused ...
 * 
 * */
package RepastCityExport;

import java.io.FileNotFoundException;
import repast.simphony.engine.environment.RunEnvironmentBuilder;
import repast.simphony.engine.environment.RunListener;
import repast.simphony.scenario.ModelInitializer;
import repast.simphony.scenario.Scenario;

public class MyInitializer implements ModelInitializer {
	MyAgentListener listen;

	
    @Override
    public void initialize(Scenario scenario, RunEnvironmentBuilder builder) {
        System.err.println("INITIALIZING!");
        builder.getScheduleRunner().addRunListener(new RunListener() {
            
            @Override
            public void stopped() {
            	try {
					new mainExport(listen);
					System.out.println("EXPORT DONE");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                System.err.println("STOPPED!");
            }
            
            @Override
            public void started() {
            	listen=new MyAgentListener();
                System.err.println("STARTED!");
        
            }
            
            @Override
            public void restarted() {
                System.err.println("RESTARTED!");
            }
            
            @Override
            public void paused() {
                System.err.println("PAUSED!");
            }
        });
    }
    
   
   
}
