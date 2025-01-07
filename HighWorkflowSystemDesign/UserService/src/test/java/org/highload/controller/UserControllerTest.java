package org.highload.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setup() {
        reset(userService);
    }

    // Valid case: Get user by email
    @Test
    void testGetUserByEmail_Valid() throws Exception {
        User user = new User(1L, "John Doe", "john@example.com");
        when(userService.getUserByEmail("john@example.com")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    // Invalid case: User not found
    @Test
    void testGetUserByEmail_UserNotFound() throws Exception {
        when(userService.getUserByEmail("unknown@example.com"))
                .thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(get("/api/users/unknown@example.com"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

    // Invalid case: Email is null
    @Test
    void testGetUserByEmail_NullEmail() throws Exception {
        when(userService.getUserByEmail(null))
                .thenThrow(new ValidationException("Email cannot be null or empty"));

        mockMvc.perform(get("/api/users/"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email cannot be null or empty"));
    }

    // Valid case: Search users
    @Test
    void testSearchUsers_Valid() throws Exception {
        List<User> users = List.of(
                new User(1L, "Alice", "alice@example.com"),
                new User(2L, "Bob", "bob@example.com")
        );
        when(userService.searchUsers("example")).thenReturn(users);

        mockMvc.perform(get("/api/users/search?keyword=example"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].email").value("alice@example.com"))
                .andExpect(jsonPath("$[1].email").value("bob@example.com"));
    }

    // Invalid case: Search with empty keyword
    @Test
    void testSearchUsers_EmptyKeyword() throws Exception {
        when(userService.searchUsers(""))
                .thenThrow(new ValidationException("Search keyword cannot be null or empty"));

        mockMvc.perform(get("/api/users/search?keyword="))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Search keyword cannot be null or empty"));
    }
}