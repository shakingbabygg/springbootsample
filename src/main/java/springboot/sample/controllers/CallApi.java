package springboot.sample.controllers;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import springboot.sample.models.api.ConvertCoinDeskResponse;
import springboot.sample.services.CallApiService;

@RestController
public class CallApi {

	@Autowired
	private CallApiService callApiService;

	@GetMapping("/coinDesk/send")
	public String sendCoinDesk() {
		return callApiService.sendToCoinDeskApi();
	}

	@GetMapping("/coinDesk/convertData")
	public ConvertCoinDeskResponse convertCoinDeskResponse()
			throws JsonMappingException, JsonProcessingException, ParseException {
		return callApiService.convertCoinDeskApiResponse();
	}
}
