/**
 * 
 */
package org.universAAL.training.universAAL.demo.srvc;

import java.util.Hashtable;

import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.ResourceFactory;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.device.LightActuator;
import org.universAAL.ontology.device.LightController;
import org.universAAL.ontology.phThing.DeviceService;

/**
 * Provided Serivce, lists the profiles to be registered. this class extends the
 * Ontological service class, To define specific restrictions for the provided
 * services.
 * 
 * In this case we extend {@link DeviceService}, to control a
 * {@link LightActuator}.
 * 
 * @author amedrano
 *
 */
public class ProvidedService extends DeviceService {

	/**
	 * Just a constructor.
	 * 
	 * @param instanceURI
	 */
	public ProvidedService(String instanceURI) {
		super(instanceURI);
	}

	static final String NAMESPACE = "http://my.domain/KnowledgeBase.owl#";
	
	public static final String MY_URI = NAMESPACE + "mySuperLampService";

	// Profiles and restrictions will be placed here
	static final ServiceProfile[] profs = new ServiceProfile[2];

	static final String PROF_TURN_ON = NAMESPACE + "turn_On";
	static final String PROF_TURN_OFF = NAMESPACE + "turn_Off";
	static final String INPUT_LAMP = NAMESPACE + "the_lamp";
	
	private static Hashtable myRestrictions = new Hashtable();
	static {
	      // Extend DeviceService without an Ontology class. Use uAAL context from Activator
	      OntologyManagement.getInstance().register(ProjectActivator.context,
	    		  // For these cases there is the simpleOntology implementation which allows to
	    		  // quickly define an ontology for a single class extension.
	         new SimpleOntology(MY_URI, DeviceService.MY_URI, 
	        		 new ResourceFactory() {
						
						public Resource createInstance(String classURI, String instanceURI, int factoryIndex) {
							return new ProvidedService(instanceURI);
						}
					}));
	      // Custom restriction to say it only controls light actuator
	      addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
	    		  DeviceService.PROP_CONTROLS, LightActuator.MY_URI, 1, 1), 
	    		  new String [] {DeviceService.PROP_CONTROLS}, myRestrictions);
	      
	      /*
	       *  Then define here the Service Profiles:
	       */
	      // This one is for turning on a lamp (set brightness 100%)
	      ProvidedService prof1 = new ProvidedService(PROF_TURN_ON);
	      prof1.addFilteringInput(INPUT_LAMP, LightActuator.MY_URI, 1, 1, new String[] { DeviceService.PROP_CONTROLS });
	      prof1.myProfile.addChangeEffect(new String[]{DeviceService.PROP_CONTROLS, LightActuator.PROP_HAS_VALUE }, new Integer(100));
	      profs[0] = prof1.myProfile;
	      
	      // This one is for turning on a lamp (set brightness 100%)
	      ProvidedService prof2 = new ProvidedService(PROF_TURN_OFF);
	      prof2.addFilteringInput(INPUT_LAMP, LightActuator.MY_URI, 1, 1, new String[] { DeviceService.PROP_CONTROLS });
	      prof2.myProfile.addChangeEffect(new String[]{DeviceService.PROP_CONTROLS, LightActuator.PROP_HAS_VALUE }, new Integer(0));
	      profs[1] = prof2.myProfile;
	      
	}
}
