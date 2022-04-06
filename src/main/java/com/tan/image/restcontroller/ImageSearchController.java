package com.tan.image.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tan.image.service.ImageSearchService;

@RestController
public class ImageSearchController {

	@Autowired
	ImageSearchService imageSearchService;

	@GetMapping(value = "imgsrch/v1/find/{searchVal}/{qty}")
	public ResponseEntity<String> downloadReq(@PathVariable("searchVal") String searchVal,
			@PathVariable("qty") String qty) {

		ResponseEntity<String> resp = null;
		String searchValup = searchVal.trim().replace(" ", "%20");
		String qtyup = qty.trim();
		System.out.println(searchValup + " - " + qtyup);
		List<String> urls = imageSearchService.parser(searchValup, qtyup);
		String zipFile = imageSearchService.imageCollector(urls);

		return new ResponseEntity<String>(zipFile, HttpStatus.OK);

	}
}
