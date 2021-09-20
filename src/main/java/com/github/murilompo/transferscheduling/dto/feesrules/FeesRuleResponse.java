package com.github.murilompo.transferscheduling.dto.feesrules;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class FeesRuleResponse {

	private Long id;
	private Long priority;
	private Integer maximumDays;
	private BigDecimal minimumValue;
	private BigDecimal percentageFee;
	private BigDecimal singleFixedFee;
	private BigDecimal recurringFixedFee;

}
