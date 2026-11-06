package feign.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vijaystack.feign.client.UserFeignClient;
import com.vijaystack.feign.dto.UserRequest;
import com.vijaystack.feign.dto.UserResponse;
import com.vijaystack.feign.service.UserConsumerService;

@ExtendWith(MockitoExtension.class)
class UserConsumerServiceTest {

    @Mock
    private UserFeignClient userFeignClient;

    @InjectMocks
    private UserConsumerService userConsumerService;

    @Test
    void shouldCreateUser() {

        UserRequest request = new UserRequest();
        request.setName("Vijay");
        request.setEmail("vijay@example.com");

        UserResponse expected = new UserResponse();
        expected.setId(1L);
        expected.setName("Vijay");
        expected.setEmail("vijay@example.com");

        when(userFeignClient.createUser(request)).thenReturn(expected);

        UserResponse actual = userConsumerService.createUser(request);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getEmail(), actual.getEmail());

        verify(userFeignClient).createUser(request);
    }

    @Test
    void shouldGetUserById() {

        UserResponse expected = new UserResponse();
        expected.setId(1L);
        expected.setName("Vijay");
        expected.setEmail("vijay@example.com");

        when(userFeignClient.getUserById(1L)).thenReturn(expected);

        UserResponse actual = userConsumerService.getUserById(1L);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getEmail(), actual.getEmail());

        verify(userFeignClient).getUserById(1L);
    }
}