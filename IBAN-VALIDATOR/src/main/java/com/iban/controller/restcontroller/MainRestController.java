package com.iban.controller.restcontroller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iban.service.MainService;
@RestController
public class MainRestController {
	private static final Logger LOGGER = LogManager.getLogger(MainRestController.class);
	@Autowired
	private MainService mainService;


	@PostMapping(value="/validate")
    public @ResponseBody ResponseEntity<String> validateIBANs(@RequestParam String codes) {
		LOGGER.info("validate called");
		return new ResponseEntity<String>(mainService.validateIBANList(codes).toString(), HttpStatus.OK);
    }


	@PostMapping(value = "/file/validate" )
	public ResponseEntity<String> validateIBANFile(@RequestParam("file") MultipartFile file) {
		LOGGER.info("File Validate called");
		return new ResponseEntity<String>(mainService.validateIBANFile(file).toString(), HttpStatus.OK);
	}

}