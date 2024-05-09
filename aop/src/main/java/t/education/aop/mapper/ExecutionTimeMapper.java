package t.education.aop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import t.education.aop.entity.ExecutionTime;
import t.education.aop.dto.responce.ExecutionTimeRs;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExecutionTimeMapper {
    ExecutionTimeMapper INSTANCE = getMapper(ExecutionTimeMapper.class);

    @Mapping(target = "durationInMs", source = "duration")
    ExecutionTimeRs toDto(ExecutionTime executionTime);
}
