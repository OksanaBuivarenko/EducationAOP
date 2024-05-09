package t.education.aop.service;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import t.education.aop.dto.responce.TouristRs;
import t.education.aop.entity.Tourist;
import t.education.aop.repository.TouristRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TouristServiceTest {

    private final TouristRepository touristRepository = Mockito.mock(TouristRepository.class);

    private final TouristService touristService = new TouristService(touristRepository);

    private Tourist tourist;

    @BeforeEach
    void setUp() {
        tourist = new Tourist();
        tourist.setId(1L);
        tourist.setPhone("345345");
        tourist.setFirstName("Name");
        tourist.setLastName("Surname");
        tourist.setEmail("email@email.ru");
    }

    @AfterEach
    void tearDown() {
        tourist = null;
    }

    @Test
    @DisplayName("Add tourist")
    void addTourist() {
        touristService.addTourist(tourist);
        verify(touristRepository, times(1)).save(any(Tourist.class));
    }

    @Test
    @DisplayName("Get tourist by id")
    void getTouristById() {
        when(touristRepository.findById(1L)).thenReturn(Optional.of(tourist));
        TouristRs touristRs = touristService.getTouristById(1L);
        assertEquals("Name", touristRs.getFirstName());
        assertEquals("345345", touristRs.getPhone());
        verify(touristRepository, times(1)).findById(1L);
    }
}