package com.highload.exception;

public class ValidationExceptionTest {
    @Test
    public void testHandleValidationException() {
        ValidationExceptionTest ex = new ValidationExceptionTest("Invalid input");
        ResponseEntity<String> response = globalExceptionHandler.handleValidationException(ex, null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid input", response.getBody());
    }
}
