package t.education.aop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import t.education.aop.dto.responce.TourOrderRs;
import t.education.aop.entity.TourOrder;
import t.education.aop.dto.request.OrderRq;


import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    OrderMapper INSTANCE = getMapper(OrderMapper.class);

    TourOrder toEntity(OrderRq orderRq);

    TourOrderRs toDto(TourOrder tourOrder);
}
