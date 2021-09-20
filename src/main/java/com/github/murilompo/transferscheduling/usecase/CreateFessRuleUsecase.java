package com.github.murilompo.transferscheduling.usecase;

import com.github.murilompo.transferscheduling.dto.feesrules.CreateFeesRuleRequest;

public interface CreateFessRuleUsecase {

	Long execute(CreateFeesRuleRequest createRule);

}
