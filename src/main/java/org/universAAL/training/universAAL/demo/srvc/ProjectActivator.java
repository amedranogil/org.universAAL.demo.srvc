package org.universAAL.training.universAAL.demo.srvc;

import org.universAAL.middleware.container.ModuleActivator;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.device.LightActuator;
import org.universAAL.ontology.phThing.DeviceService;

public class ProjectActivator implements ModuleActivator {

	static ModuleContext context;
	
	ServiceCallee myCallee;
	
	ServiceCaller myCaller;

	public void start(ModuleContext ctxt) throws Exception {
		context = ctxt;
		LogUtils.logDebug(context, getClass(), "start", "Starting.");
		/*
		 * uAAL stuff
		 */
		// Register the Service Callee with the profiles to which it will be matched
		myCallee = new MyCallee(context, ProvidedService.profs);
		

		LogUtils.logDebug(context, getClass(), "start", "Started.");
	}


	public void stop(ModuleContext ctxt) throws Exception {
		LogUtils.logDebug(context, getClass(), "stop", "Stopping.");
		/*
		 * close uAAL stuff
		 */
		// unregister the callee
		myCallee.close();
		myCallee = null;
		
		LogUtils.logDebug(context, getClass(), "stop", "Stopped.");

	}

}
