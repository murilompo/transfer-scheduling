package com.github.murilompo.transferscheduling.usecase.impl;

import com.github.murilompo.transferscheduling.domain.FeesRulesEntity;
import com.github.murilompo.transferscheduling.dto.feesrules.CreateFeesRuleRequest;
import com.github.murilompo.transferscheduling.exception.FeesRulesCompositeKeyViolationException;
import com.github.murilompo.transferscheduling.mapper.FeesRulesMapper;
import com.github.murilompo.transferscheduling.repository.FeesRulesRepository;
import com.github.murilompo.transferscheduling.usecase.CreateFessRuleUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateFessRuleUsecaseImpl implements CreateFessRuleUsecase {

	private final FeesRulesMapper mapper;
	private final FeesRulesRepository feesRulesRepository;

	@Override
	public Long execute(CreateFeesRuleRequest createRule) {

		FeesRulesEntity entity = mapper.toEntity(createRule);
		try {
			feesRulesRepository.save(entity);
		} catch (DataIntegrityViolationException e) {
			throw new FeesRulesCompositeKeyViolationException();
		}
		return entity.getId();
	}

}
