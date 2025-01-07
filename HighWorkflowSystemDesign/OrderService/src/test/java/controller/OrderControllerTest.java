package controller;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    void setup() {
        reset(orderService);
    }

    // Valid case: Create an order
    @Test
    void testCreateOrder_Valid() throws Exception {
        Order order = new Order(1L, 1L, "Pending");
        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"status\": \"Pending\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("Pending"));
    }

    // Invalid case: Create an order with null userId
    @Test
    void testCreateOrder_NullUserId() throws Exception {
        when(orderService.createOrder(any(Order.class)))
                .thenThrow(new ValidationException("User ID must not be null or zero."));

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": null, \"status\": \"Pending\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User ID must not be null or zero."));
    }

    // Valid case: Get orders by status
    @Test
    void testGetOrdersByStatus_Valid() throws Exception {
        List<Order> orders = List.of(
                new Order(1L, 1L, "Completed"),
                new Order(2L, 2L, "Completed")
        );
        when(orderService.getOrdersByStatus(Order.OrderStatus.COMPLETED)).thenReturn(orders);

        mockMvc.perform(get("/api/orders/status/COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].status").value("Completed"))
                .andExpect(jsonPath("$[1].status").value("Completed"));
    }

    // Invalid case: Invalid status
    @Test
    void testGetOrdersByStatus_InvalidStatus() throws Exception {
        when(orderService.getOrdersByStatus(any()))
                .thenThrow(new IllegalArgumentException("Invalid order status"));

        mockMvc.perform(get("/api/orders/status/INVALID_STATUS"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid order status"));
    }
}