package springboot.sample.models.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BpiDesc {

	private String code;

	private String symbol;

	private String rate;

	private String description;

	private float rate_float;
}
