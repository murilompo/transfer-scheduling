package com.github.murilompo.transferscheduling.dto.feesrules;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CreateFeesRuleRequest {

	@NotNull(message = "Priority is required")
	@PositiveOrZero(message = "Priority must be greater than or equal to zero")
	private Long priority;
	@NotNull(message = "Maximum days is required")
	@PositiveOrZero(message = "Maximum days must be greater than or equal to zero")
	private Integer maximumDays;
	@NotNull(message = "Minimum value days is required")
	@PositiveOrZero(message = "Minimum value must be greater than or equal to zero")
	@Digits(integer = 18, fraction = 2, message = "Minimum value can contain up to 18 integer digits and 2 fractional digits")
	private BigDecimal minimumValue;
	@PositiveOrZero(message = "Percentage fee must be greater than or equal to zero")
	@DecimalMax(value = "1", inclusive = false, message = "Percentage fee must be less than one")
	@Digits(integer = 1, fraction = 8, message = "Percentage fee must be greater than or equal to zero, less than one and have up to 8 fractional digits")
	private BigDecimal percentageFee;
	@PositiveOrZero(message = "Single fixed fee must be greater than or equal to zero")
	@Digits(integer = 18, fraction = 2, message = "Single fixed fee can contain up to 18 integer digits and 2 fractional digits")
	private BigDecimal singleFixedFee;
	@PositiveOrZero(message = "Recurring fixed fee must be greater than or equal to zero")
	@Digits(integer = 18, fraction = 2, message = "Recurring fixed fee can contain up to 18 integer digits and 2 fractional digits")
	private BigDecimal recurringFixedFee;

}
