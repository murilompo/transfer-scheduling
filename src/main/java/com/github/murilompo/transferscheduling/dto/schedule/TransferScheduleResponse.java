package com.github.murilompo.transferscheduling.dto.schedule;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class TransferScheduleResponse {

	private Long id;
	private String sourceAccount;
	private String targetAccount;
	private BigDecimal amount;
	private BigDecimal fee;
	private LocalDate schedulingDate;
	private LocalDate processingDate;

}
