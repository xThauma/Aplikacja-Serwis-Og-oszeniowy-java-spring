package com.ProjectService.service;

import com.ProjectService.utils.StringUtils;

import javafx.scene.control.Alert.AlertType;

public aspect DeleteAspect {

	pointcut p1() : execution(public * com.ProjectService.service.*.remov*(..));

	before() : p1() {
		
	}

	after() : p1() {
		StringUtils.createAlertBox(AlertType.INFORMATION, "Artykuł", "", "Artykuł skasowany");
	}
}
