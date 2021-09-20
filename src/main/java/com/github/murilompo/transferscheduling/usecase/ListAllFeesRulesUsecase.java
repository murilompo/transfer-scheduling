package com.github.murilompo.transferscheduling.usecase;

import com.github.murilompo.transferscheduling.dto.feesrules.FeesRuleResponse;
import java.util.List;

public interface ListAllFeesRulesUsecase {

	List<FeesRuleResponse> execute();

}
