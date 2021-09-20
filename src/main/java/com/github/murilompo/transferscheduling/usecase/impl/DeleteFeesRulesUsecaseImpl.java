package com.github.murilompo.transferscheduling.usecase.impl;

import com.github.murilompo.transferscheduling.exception.IdFeesRulesNotFoundException;
import com.github.murilompo.transferscheduling.repository.FeesRulesRepository;
import com.github.murilompo.transferscheduling.usecase.DeleteFeesRulesUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteFeesRulesUsecaseImpl implements DeleteFeesRulesUsecase {

	private final FeesRulesRepository feesRulesRepository;

	@Override
	public void execute(Long feesRulesId) {

		try {
			feesRulesRepository.deleteById(feesRulesId);
		} catch (EmptyResultDataAccessException e) {
			throw new IdFeesRulesNotFoundException();
		}
	}

}
