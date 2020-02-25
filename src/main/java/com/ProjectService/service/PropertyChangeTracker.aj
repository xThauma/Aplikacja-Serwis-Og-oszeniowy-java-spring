package com.ProjectService.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public aspect PropertyChangeTracker {

	private static final Logger logger = LogManager.getLogger(PropertyChangeTracker.class);

	pointcut p1() : execution(public * com.ProjectService.service.*.*(..));

	before() : p1() {
		logger.info("Before: " + thisJoinPoint.getSignature().toString() + ". Parameters(" + thisJoinPoint.getArgs().toString() + ")", "wykonane");
	}

	after() : p1() {
		logger.info("After: " + thisJoinPoint.getSignature().toString() + ". Parameters(" + thisJoinPoint.getArgs().toString() + ")", "wykonane");
	}
}
