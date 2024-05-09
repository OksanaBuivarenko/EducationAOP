package t.education.aop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import t.education.aop.annotation.TrackTime;
import t.education.aop.dto.responce.TouristRs;
import t.education.aop.entity.Tourist;
import t.education.aop.exception.ObjectNotFoundException;
import t.education.aop.mapper.TouristMapper;
import t.education.aop.repository.TouristRepository;


@RequiredArgsConstructor
@Service
public class TouristService {
    private final TouristRepository touristRepository;

    @TrackTime
    public Tourist addTourist(Tourist tourist) {
        return touristRepository.save(tourist);
    }

    public TouristRs getTouristById(Long id) {
        return TouristMapper.INSTANCE.toDto(touristRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id)));
    }

}
