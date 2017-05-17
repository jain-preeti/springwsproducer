package com.concretepage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Home {
	
	@RequestMapping("/")
	public String hh()
	{
		return "hello";
	}

}
