package com.github.murilompo.transferscheduling.dto.schedule;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.Digits;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateTransferScheduleRequest {

	@NotNull(message = "Source account is required")
	@Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = "Source account must be 6 alphanumeric characters")
	private String sourceAccount;
	@NotNull(message = "Target account is required")
	@Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = "Target account must be 6 alphanumeric characters")
	private String targetAccount;
	@NotNull(message = "Amount is required")
	@Positive(message = "Amount must be greater than zero")
	@Digits(integer = 18, fraction = 2, message = "Amount can contain up to 18 integer digits and 2 fractional digits")
	private BigDecimal amount;
	@FutureOrPresent(message = "Processing date must be greater than or equal to date of today")
	@NotNull(message = "Processing date is required")
	private LocalDate processingDate;

}
