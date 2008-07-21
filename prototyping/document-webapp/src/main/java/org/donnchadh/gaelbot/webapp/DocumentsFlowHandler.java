package org.donnchadh.gaelbot.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.webflow.core.FlowException;
import org.springframework.webflow.execution.FlowExecutionOutcome;
import org.springframework.webflow.execution.repository.NoSuchFlowExecutionException;
import org.springframework.webflow.mvc.servlet.AbstractFlowHandler;

public class DocumentsFlowHandler extends AbstractFlowHandler {

    @Override
    public String handleExecutionOutcome(FlowExecutionOutcome outcome, HttpServletRequest request,
	    HttpServletResponse response) {
        return "/documents/index";
    }

    @Override
    public String handleException(FlowException e, HttpServletRequest request, HttpServletResponse response) {
    	if (e instanceof NoSuchFlowExecutionException) {
    	    return "/documents/index";
    	} else {
    	    throw e;
    	}
    }

}
