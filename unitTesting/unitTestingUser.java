package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.User;

class unitTestingUser {

    private User user;
    private static class TestUser extends User {

        public TestUser(String name, String surname, LocalDate dob,
                        int phone, String address, String username, String password) {
            super(name, surname, dob, phone, address, username, password);
        }

        @Override
        public boolean logIn(String username, String password) {
            return getUsername().equals(username) && getPassword().equals(password);
        }
    }

    @BeforeEach
    void setUp() {
        user = new TestUser(
                "John",
                "Doe",
                LocalDate.of(2000, 1, 1),
                123456,
                "Address",
                "john",
                "pass"
        );
    }

    @Test
    void testGetters() {
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals(123456, user.getPhoneNr());
        assertEquals("Address", user.getAddress());
        assertEquals("john", user.getUsername());
        assertEquals("pass", user.getPassword());
    }

    @Test
    void testSetters() {
        user.setName("Jane");
        user.changeSurname("Smith");
        user.setphoneNr(999);
        user.setAddress("New Address");

        assertEquals("Jane", user.getName());
        assertEquals("Smith", user.getSurname());
        assertEquals(999, user.getPhoneNr());
        assertEquals("New Address", user.getAddress());
    }

    @Test
    void testChangePasswordSuccess() {
        boolean result = user.changePass("pass", "newPass", "newPass");
        assertTrue(result);
        assertEquals("newPass", user.getPassword());
    }

    @Test
    void testChangePasswordWrongOld() {
        boolean result = user.changePass("wrong", "new", "new");
        assertFalse(result);
        assertEquals("pass", user.getPassword());
    }

    @Test
    void testChangePasswordMismatch() {
        boolean result = user.changePass("pass", "new1", "new2");
        assertFalse(result);
        assertEquals("pass", user.getPassword());
    }

    @Test
    void testLoginValid() {
        assertTrue(user.logIn("john", "pass"));
    }

    @Test
    void testLoginInvalid() {
        assertFalse(user.logIn("john", "wrong"));
    }

    @Test
    void testToStringNotNull() {
        assertNotNull(user.toString());
    }
}

