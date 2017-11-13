/**
 * 
 */
package org.universAAL.training.universAAL.demo.srvc;

import java.io.InputStream;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;

/**
 * Service Callee code with the method to receive calls
 * 
 * @author amedrano
 *
 */
public class MyCallee extends ServiceCallee {

	protected MyCallee(ModuleContext context, ServiceProfile[] realizedServices) {
		super(context, realizedServices);
	}

	/**
	 * This method is called when the Bus is disconnected.
	 */
	@Override
	public void communicationChannelBroken() {
		//
	}

	/**
	 * This method is called when the Service Request is matched. Do your suff here
	 * to comply with the request.
	 */
	@Override
	public ServiceResponse handleCall(ServiceCall call) {
		String operation = call.getProcessURI();
		if (operation.startsWith(ProvidedService.PROF_TURN_ON)) {
			// Profile arguments can be taken from the call
			Object input = call.getInputValue(ProvidedService.INPUT_LAMP);
			// Make the light turn on here...
			displayStatus("on.art");
			// then return success
			return new ServiceResponse(CallStatus.succeeded);
		}
		// For completeness:
		if (operation.startsWith(ProvidedService.PROF_TURN_OFF)) {
			displayStatus("off.art");
			return new ServiceResponse(CallStatus.succeeded);
		}
		return new ServiceResponse(CallStatus.noMatchingServiceFound);

	}

	private void displayStatus(String res) {
		// Do not look too deep into this
		// This might just be the business end of the service.
		try {
			InputStream is = MyCallee.class.getClassLoader().getResourceAsStream(res);
			String out = new java.util.Scanner(is).useDelimiter("\\A").next();
			System.out.println(out);
		} catch (Exception e) {
			System.out.println(" I	 am very sad :,(");
			e.printStackTrace();
		} 
	}
}
