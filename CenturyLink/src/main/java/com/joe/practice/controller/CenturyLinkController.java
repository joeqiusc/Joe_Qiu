package com.joe.practice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CenturyLinkController {
	
	@RequestMapping(value="/")
	public int stringLength(String s) {
		s= "Hello CenturyLink";
		return s.length();
	}

}
