package springboot.sample.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import springboot.sample.models.api.Bpi;
import springboot.sample.models.api.BpiInfo;
import springboot.sample.models.api.CoinDeskApiResponse;
import springboot.sample.models.api.ConvertCoinDeskResponse;
import springboot.sample.models.api.Time;

@Service
public class CallApiService {

	private final RestTemplate restTemplate;

	private ObjectMapper objectMapper;

	public final static DateFormat fullDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public CallApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}

	public String sendToCoinDeskApi() {
		return restTemplate.getForObject("https://api.coindesk.com/v1/bpi/currentprice.json", String.class);
	}

	public ConvertCoinDeskResponse convertCoinDeskApiResponse()
			throws JsonMappingException, JsonProcessingException, ParseException {
		String response = sendToCoinDeskApi();
		CoinDeskApiResponse coinDeskApiResponse = objectMapper.readValue(response, CoinDeskApiResponse.class);

		Time convertDataTime = convertTime(coinDeskApiResponse);
		BpiInfo convertDataBpi = convertBpi(coinDeskApiResponse);

		return ConvertCoinDeskResponse.builder().time(convertDataTime).bpiInfo(convertDataBpi).build();
	}

	public Time convertTime(CoinDeskApiResponse coinDeskApiResponse) throws ParseException {
		Time time = coinDeskApiResponse.getTime();
		Date convertUpdated = fullDateFormat.parse(time.getUpdated());
		Date convertUpdatedISO = fullDateFormat.parse(time.getUpdatedISO());
		Date convertUpdateduk = fullDateFormat.parse(time.getUpdateduk());

		Time convertDataTime = new Time();
		convertDataTime.setUpdated(convertUpdated.toString());
		convertDataTime.setUpdatedISO(convertUpdatedISO.toString());
		convertDataTime.setUpdateduk(convertUpdateduk.toString());
		return convertDataTime;
	}

	public BpiInfo convertBpi(CoinDeskApiResponse coinDeskApiResponse) {
		BpiInfo bpiInfo = new BpiInfo();
		Bpi bpi = coinDeskApiResponse.getBpi();
		BeanUtils.copyProperties(bpiInfo.getUsd(), bpi.getUsd());
		BeanUtils.copyProperties(bpiInfo.getGbp(), bpi.getGbp());
		BeanUtils.copyProperties(bpiInfo.getEur(), bpi.getEur());

		// TODO 中文說明可能要從DB取得?
		bpiInfo.getUsd().setDescription("美");
		bpiInfo.getGbp().setDescription("英");
		bpiInfo.getEur().setDescription("歐");
		return bpiInfo;
	}
}
