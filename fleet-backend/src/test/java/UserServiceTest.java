import com.fleet.domain.po.User;
import com.fleet.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fleet.repository.UserRepository;
import com.fleet.service.UserService;
import com.fleet.domain.dto.RegisterFormDTO;
import com.fleet.domain.po.University;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private UserMapper userMapper;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUser_id(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword_hash("hashedpassword");
        testUser.setAverage_rating(4.5);
        testUser.setCreated_at(LocalDateTime.now());
        testUser.setUpdated_at(LocalDateTime.now());
    }

    @Test
    void saveUser_ShouldReturnSavedUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User savedUser = userService.saveUser(testUser);

        assertNotNull(savedUser);
        assertEquals(testUser.getName(), savedUser.getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void findUserById_WhenUserExists_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<User> found = userService.findUserById(1L);

        assertTrue(found.isPresent());
        assertEquals(testUser.getName(), found.get().getName());
        verify(userRepository).findById(1L);
    }

    @Test
    void findUserById_WhenUserDoesNotExist_ShouldReturnEmpty() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<User> found = userService.findUserById(999L);

        assertFalse(found.isPresent());
        verify(userRepository).findById(999L);
    }

    @Test
    void findAllUsers_ShouldReturnList() {
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> foundUsers = userService.findAllUsers();

        assertFalse(foundUsers.isEmpty());
        assertEquals(1, foundUsers.size());
        verify(userRepository).findAll();
    }

    @Test
    void findUserByEmail_WhenUserExists_ShouldReturnUser() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(testUser);

        User found = userService.findUserByEmail(testUser.getEmail());

        assertNotNull(found);
        assertEquals(testUser.getEmail(), found.getEmail());
        verify(userRepository).findByEmail(testUser.getEmail());
    }

    @Test
    void updateUser_WhenUserExists_ShouldReturnUpdatedUser() {
        User updatedUser = new User();
        updatedUser.setName("Updated Name");

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(userRepository).findById(1L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_WhenUserDoesNotExist_ShouldThrowException() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                userService.updateUser(999L, new User())
        );
        verify(userRepository).findById(999L);
    }

    @Test
    void deleteUser_WhenUserExists_ShouldDeleteSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).delete(any(User.class));

        userService.deleteUser(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).delete(any(User.class));
    }

    @Test
    void deleteUser_WhenUserDoesNotExist_ShouldThrowException() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                userService.deleteUser(999L)
        );
        verify(userRepository).findById(999L);
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void registerUser_ShouldReturnSavedUser() {
        // Arrange
        RegisterFormDTO registerDTO = new RegisterFormDTO();
        registerDTO.setUsername("testUser");
        registerDTO.setEmail("test@example.com");
        registerDTO.setPassword("password123");
        registerDTO.setUniversity_id(1L);

        User mappedUser = new User();
        mappedUser.setName("testUser");
        mappedUser.setEmail("test@example.com");
        
        University university = new University();
        university.setUniversity_id(1L);
        mappedUser.setUniversity(university);

        String hashedPassword = "hashedPassword123";

        // Mock behaviors
        when(userMapper.toEntity(registerDTO)).thenReturn(mappedUser);
        when(passwordEncoder.encode("password123")).thenReturn(hashedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setUser_id(1L);
            return savedUser;
        });

        // Act
        User result = userService.registerUser(registerDTO);

        // Assert
        assertNotNull(result);
        assertEquals("testUser", result.getName());
        assertEquals("test@example.com", result.getEmail());
        assertEquals(hashedPassword, result.getPassword_hash());
        assertEquals(1L, result.getUniversity().getUniversity_id());

        // Verify interactions
        verify(userMapper).toEntity(registerDTO);
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_WithExistingEmail_ShouldThrowException() {
        // Arrange
        RegisterFormDTO registerDTO = new RegisterFormDTO();
        registerDTO.setEmail("existing@example.com");
        
        User mappedUser = new User();
        mappedUser.setEmail("existing@example.com");

        when(userMapper.toEntity(registerDTO)).thenReturn(mappedUser);
        when(userRepository.findByEmail("existing@example.com")).thenReturn(mappedUser);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(registerDTO);
        });

        verify(userRepository).findByEmail("existing@example.com");
        verify(userRepository, never()).save(any(User.class));
    }
}