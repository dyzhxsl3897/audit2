package com.dyzhxsl.audit.bl.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.dyzhxsl.audit.bl.beans.User;

@Path("/loan")
@Controller
public class LoanController {

	private static Logger logger = Logger.getLogger(LoanController.class);

	@Context
	private HttpServletRequest req;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getSummary")
	public Map<String, Object> getSummary(Map<String, Object> requestBody) throws Exception {
		Map<String, Object> status = new HashMap<String, Object>();
		String returnStatus = "success";

		logger.info("running... /rest/loan/getSummary");

		User currentUser = (User) req.getSession(true).getAttribute("currentUser");
		String fromUser = String.valueOf(requestBody.get("fromUser"));
		String toUser = String.valueOf(requestBody.get("toUser"));

		if (null != currentUser) {
			logger.info("From: " + fromUser + ", To: " + toUser + ". Current: " + currentUser.getFirstName() + " " + currentUser.getLastName());
		} else {
			logger.info("From: " + fromUser + ", To: " + toUser + ". Current: Null");
		}

		status.put("status", returnStatus);
		return status;
	}

}
