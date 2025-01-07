@SpringBootTest
@AutoConfigureMockMvc
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private InventoryService inventoryService;

    @Test
    void testCreateOrder_StockAvailable() {
        when(inventoryService.checkStockAvailability(1L, 10)).thenReturn(true);

        Order order = new Order();
        order.setUserId(1L);
        order.setProductId(1L);
        order.setQuantity(10);

        Order savedOrder = orderService.createOrder(order);

        assertNotNull(savedOrder);
        verify(inventoryService, times(1)).updateStock(1L, 10);
    }
}