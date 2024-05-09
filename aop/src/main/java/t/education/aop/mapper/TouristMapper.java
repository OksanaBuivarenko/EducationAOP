package t.education.aop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import t.education.aop.dto.responce.TouristRs;
import t.education.aop.entity.Tourist;
import t.education.aop.dto.request.OrderRq;


import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TouristMapper {
    TouristMapper INSTANCE = getMapper(TouristMapper.class);

    Tourist toEntity(OrderRq orderRq);

    TouristRs toDto(Tourist tourist);
}
