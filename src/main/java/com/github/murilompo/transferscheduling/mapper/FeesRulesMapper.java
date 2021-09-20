package com.github.murilompo.transferscheduling.mapper;

import com.github.murilompo.transferscheduling.domain.FeesRulesEntity;
import com.github.murilompo.transferscheduling.dto.feesrules.CreateFeesRuleRequest;
import com.github.murilompo.transferscheduling.dto.feesrules.FeesRuleResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeesRulesMapper {

	FeesRulesEntity toEntity(CreateFeesRuleRequest feesRules);

	List<FeesRuleResponse> toDTO(List<FeesRulesEntity> feesRulesList);

}
