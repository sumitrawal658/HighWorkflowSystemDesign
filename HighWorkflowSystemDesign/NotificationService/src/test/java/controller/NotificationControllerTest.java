package controller;
@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @BeforeEach
    void setup() {
        reset(notificationService);
    }

    // Valid case: Send a notification
    @Test
    void testSendNotification_Valid() throws Exception {
        Notification notification = new Notification(1L, 1L, "Hello", "PENDING");
        when(notificationService.sendNotification(any(Notification.class))).thenReturn(notification);

        mockMvc.perform(post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"message\": \"Hello\", \"status\": \"PENDING\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.message").value("Hello"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    // Invalid case: Send a notification with null message
    @Test
    void testSendNotification_NullMessage() throws Exception {
        when(notificationService.sendNotification(any(Notification.class)))
                .thenThrow(new ValidationException("Notification message cannot be null or empty"));

        mockMvc.perform(post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"message\": null, \"status\": \"PENDING\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Notification message cannot be null or empty"));
    }
}