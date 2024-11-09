package com.placementserver.placementserver.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.placementserver.placementserver.models.Job;

@RestController
@RequestMapping("/jobpost")
public class JobPostController {
	
	@PostMapping("/post")
	public String posting(@RequestBody Job job) {
		String[] arr = job.getJob().split("\\r?\\n");
		for(String temp:arr) {
			System.out.println(temp);
		}
//		Pattern p = Pattern.compile("https");
//		Matcher m = p.matcher(job.getJob());
//		if(m.find()) {
//			System.out.println(m.group());
//			System.out.println(m.start());
//			System.out.println(m.end());
//		}
		return job.getJob();
	}
}
