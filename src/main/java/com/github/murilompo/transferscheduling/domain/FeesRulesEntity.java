package com.github.murilompo.transferscheduling.domain;

import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "FEES_RULES", uniqueConstraints
		= @UniqueConstraint(columnNames = {"PRIORITY", "MAXIMUM_DAYS", "MINIMUM_VALUE"}))
public class FeesRulesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	@Basic(optional = false)
	@Column(name = "PRIORITY", nullable = false)
	private Long priority;
	@Basic(optional = false)
	@Column(name = "MAXIMUM_DAYS", nullable = false)
	private Integer maximumDays;
	@Basic(optional = false)
	@Column(name = "MINIMUM_VALUE", nullable = false, scale = 2, precision = 20)
	private BigDecimal minimumValue;
	@Column(name = "PERCENTAGE_FEE", scale = 8, precision = 8)
	private BigDecimal percentageFee;
	@Column(name = "SINGLE_FIXED_FEE", scale = 2, precision = 20)
	private BigDecimal singleFixedFee;
	@Column(name = "RECURRING_FIXED_FEE", scale = 2, precision = 20)
	private BigDecimal recurringFixedFee;

}
