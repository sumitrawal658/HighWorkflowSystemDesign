package controller;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @BeforeEach
    void setup() {
        reset(paymentService);
    }

    @Test
    void testProcessPayment_InvalidPayment() throws Exception {
        when(paymentService.processPayment(any(Payment.class)))
                .thenThrow(new ValidationException("Payment amount must be greater than zero"));

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"amount\": -100}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Payment amount must be greater than zero"));
    }

    @Test
    void testGetPaymentByTransactionId_NotFound() throws Exception {
        when(paymentService.getPaymentByTransactionId("invalid-id"))
                .thenThrow(new ResourceNotFoundException("Payment not found for transaction ID: invalid-id"));

        mockMvc.perform(get("/api/payments/invalid-id"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Payment not found for transaction ID: invalid-id"));
    }
}