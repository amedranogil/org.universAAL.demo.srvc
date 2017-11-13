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
		
		// Register the caller, use the default caller for simplicity.
		myCaller = new DefaultServiceCaller(context);
		
		// wait, for suspense
		Thread.sleep(1000);
		
		// Do a service request
		ServiceRequest sreq = new ServiceRequest(new DeviceService(), new Resource("Actual_User"));
		
		// This device is for reference (just because the service profile requires a LightActuator as input)
		Resource device = new LightActuator(ProvidedService.NAMESPACE+"myLamp");
		
		// Request is composed of the device as input
		sreq.addValueFilter(new String[] {DeviceService.PROP_CONTROLS}, device);
		// and as a change effect of the value of the device
		sreq.addChangeEffect(new String[] {DeviceService.PROP_CONTROLS,LightActuator.PROP_HAS_VALUE},
				Integer.valueOf(100));
		// to turn off change value to 0.
		
		// perform the request
		ServiceResponse resp = myCaller.call(sreq);
		if (resp.getCallStatus().equals(CallStatus.succeeded)) {
			System.out.println("The Request was correctly processed");
		} else {
			System.err.println("Oh no!");
		}
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
		
		// un register the caller
		myCaller.close();
		myCaller = null;
		
		LogUtils.logDebug(context, getClass(), "stop", "Stopped.");

	}

}
