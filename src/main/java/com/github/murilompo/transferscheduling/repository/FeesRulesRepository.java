package com.github.murilompo.transferscheduling.repository;

import com.github.murilompo.transferscheduling.domain.FeesRulesEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface FeesRulesRepository extends CrudRepository<FeesRulesEntity, Long> {

	List<FeesRulesEntity> findAllByOrderByPriorityAscMaximumDaysAscMinimumValueDesc();

	Optional<FeesRulesEntity>
			findTop1ByMaximumDaysGreaterThanEqualAndMinimumValueLessThanEqualOrderByPriorityAscMaximumDaysAscMinimumValueDesc(
					Integer maximumDays, BigDecimal minimumValue);

}
