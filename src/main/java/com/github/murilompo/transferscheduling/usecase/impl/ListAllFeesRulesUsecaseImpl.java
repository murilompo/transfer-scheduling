package com.github.murilompo.transferscheduling.usecase.impl;

import com.github.murilompo.transferscheduling.domain.FeesRulesEntity;
import com.github.murilompo.transferscheduling.dto.feesrules.FeesRuleResponse;
import com.github.murilompo.transferscheduling.mapper.FeesRulesMapper;
import com.github.murilompo.transferscheduling.repository.FeesRulesRepository;
import com.github.murilompo.transferscheduling.usecase.ListAllFeesRulesUsecase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListAllFeesRulesUsecaseImpl implements ListAllFeesRulesUsecase {

	private final FeesRulesMapper mapper;
	private final FeesRulesRepository feesRulesRepository;

	@Override
	public List<FeesRuleResponse> execute() {

		List<FeesRulesEntity> entities = feesRulesRepository
				.findAllByOrderByPriorityAscMaximumDaysAscMinimumValueDesc();

		return mapper.toDTO(entities);
	}

}
