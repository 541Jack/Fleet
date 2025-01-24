import com.fleet.domain.po.University;
import com.fleet.repository.UniversityRepository;
import com.fleet.service.UniversityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversityServiceTest {

    @Mock
    private UniversityRepository universityRepository;

    @InjectMocks
    private UniversityService universityService;

    private University testUniversity;

    @BeforeEach
    void setUp() {
        testUniversity = new University();
        testUniversity.setUniversity_id(1L);
        testUniversity.setName("Test University");
        testUniversity.setLocation("Test Location");
        testUniversity.setCreated_at(LocalDateTime.now());
        testUniversity.setUpdated_at(LocalDateTime.now());
    }

    @Test
    void saveUniversity_ShouldReturnSavedUniversity() {
        when(universityRepository.save(any(University.class))).thenReturn(testUniversity);

        University savedUniversity = universityService.saveUniversity(testUniversity);

        assertNotNull(savedUniversity);
        assertEquals(testUniversity.getName(), savedUniversity.getName());
        verify(universityRepository).save(any(University.class));
    }

    @Test
    void findById_WhenUniversityExists_ShouldReturnUniversity() {
        when(universityRepository.findById(1L)).thenReturn(Optional.of(testUniversity));

        University found = universityService.findById(1L);

        assertNotNull(found);
        assertEquals(testUniversity.getName(), found.getName());
        verify(universityRepository).findById(1L);
    }

    @Test
    void findById_WhenUniversityDoesNotExist_ShouldThrowException() {
        when(universityRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> 
            universityService.findById(999L)
        );
    }
} 