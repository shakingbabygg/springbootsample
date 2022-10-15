package springboot.sample.models.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoinDeskApiResponse {

	private Time time;
	
	private String disclaimer;
	
	private String chartName;
	
	private Bpi bpi;
}
