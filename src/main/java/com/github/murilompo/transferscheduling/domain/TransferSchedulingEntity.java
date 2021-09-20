package com.github.murilompo.transferscheduling.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TRANSFER_SCHEDULING")
public class TransferSchedulingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	@Basic(optional = false)
	@Column(name = "SOURCE_ACCOUNT", nullable = false, length = 6)
	private String sourceAccount;
	@Basic(optional = false)
	@Column(name = "TARGET_ACCOUNT", nullable = false, length = 6)
	private String targetAccount;
	@Basic(optional = false)
	@Column(name = "AMOUNT", nullable = false, scale = 2, precision = 20)
	private BigDecimal amount;
	@Basic(optional = false)
	@Column(name = "FEE", nullable = false, scale = 2, precision = 20)
	private BigDecimal fee;
	@Basic(optional = false)
	@Column(name = "SCHEDULING_DATE", nullable = false)
	private LocalDate schedulingDate;
	@Basic(optional = false)
	@Column(name = "PROCESSING_DATE", nullable = false)
	private LocalDate processingDate;

}
