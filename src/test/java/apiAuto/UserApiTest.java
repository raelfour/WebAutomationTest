package apiAuto;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserApiTest {

    private static RequestSpecification requestSpec;

    // MODEL USER CLASS (Inner Class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        private String id;
        private String title;
        private String firstName;
        private String lastName;
        private String email;
        private String picture;

        // Constructors
        public User() {}

        public User(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }

        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPicture() { return picture; }
        public void setPicture(String picture) { this.picture = picture; }

        @Override
        public String toString() {
            return "User{" +
                    "id='" + id + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

    // BASE TEST SETUP
    @BeforeAll
    public static void setupBaseUrl() {
        RestAssured.baseURI = "https://dummyapi.io";
        RestAssured.basePath = "/data/v1";

        requestSpec = new RequestSpecBuilder()
                .addHeader("app-id", "63a804408eb0cb069b57e43a") // Ganti dengan app-id yang valid dari dummyapi.io
                .addHeader("Content-Type", "application/json")
                .build();

        System.out.println("=== API Testing Setup Complete ===");
        System.out.println("Base URL: " + RestAssured.baseURI + RestAssured.basePath);
    }

    // POSITIVE TEST CASES

    @Test
    @Order(1)
    @DisplayName("Positive Test 1: Get All Users - Should return user list")
    public void testGetAllUsers() {
        System.out.println("\n=== Testing GET All Users ===");

        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/user")
                .then()
                .statusCode(200)
                .body("data", notNullValue())
                .body("data.size()", greaterThan(0))
                .body("data[0].id", notNullValue())
                .body("data[0].firstName", notNullValue())
                .body("data[0].lastName", notNullValue())
                .extract().response();

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Total Users: " + response.jsonPath().getList("data").size());
        System.out.println("First User: " + response.jsonPath().getString("data[0].firstName") +
                " " + response.jsonPath().getString("data[0].lastName"));

        // Additional assertions
        assertNotNull(response.jsonPath().getList("data"));
        assertTrue(response.jsonPath().getList("data").size() > 0);

        System.out.println("✅ Get All Users Test PASSED");
    }

    @Test
    @Order(2)
    @DisplayName("Positive Test 2: Get User by ID - Should return specific user")
    public void testGetUserById() {
        System.out.println("\n=== Testing GET User by ID ===");

        String userId = "60d0fe4f5311236168a109d8"; // Sample user ID

        Response response = given()
                .spec(requestSpec)
                .pathParam("id", userId)
                .when()
                .get("/user/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(userId))
                .body("title", notNullValue())
                .body("firstName", notNullValue())
                .body("lastName", notNullValue())
                .extract().response();

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("User ID: " + response.jsonPath().getString("id"));
        System.out.println("User Name: " + response.jsonPath().getString("firstName") +
                " " + response.jsonPath().getString("lastName"));
        System.out.println("Email: " + response.jsonPath().getString("email"));

        // Convert to User object
        User user = response.as(User.class);
        assertNotNull(user.getId());
        assertNotNull(user.getFirstName());
        assertNotNull(user.getLastName());
        assertEquals(userId, user.getId());

        System.out.println("✅ Get User by ID Test PASSED");
    }

    @Test
    @Order(3)
    @DisplayName("Positive Test 3: Get Users with Limit - Should return limited results")
    public void testGetUsersWithLimit() {
        System.out.println("\n=== Testing GET Users with Limit ===");

        int limit = 5;

        Response response = given()
                .spec(requestSpec)
                .queryParam("limit", limit)
                .when()
                .get("/user")
                .then()
                .statusCode(200)
                .body("data.size()", lessThanOrEqualTo(limit))
                .body("limit", equalTo(limit))
                .extract().response();

        int actualSize = response.jsonPath().getList("data").size();
        int responseLimit = response.jsonPath().getInt("limit");

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Requested Limit: " + limit);
        System.out.println("Response Limit: " + responseLimit);
        System.out.println("Actual Users Returned: " + actualSize);

        assertTrue(actualSize <= limit, "Returned users should not exceed limit");
        assertEquals(limit, responseLimit, "Response limit should match request");

        System.out.println("✅ Get Users with Limit Test PASSED");
    }

    @Test
    @Order(4)
    @DisplayName("Positive Test 4: Create New User - Should create user successfully")
    public void testCreateUser() {
        System.out.println("\n=== Testing CREATE User ===");

        User newUser = new User("John", "Doe", "john.doe.test" + System.currentTimeMillis() + "@example.com");
        newUser.setTitle("mr");

        Response response = given()
                .spec(requestSpec)
                .body(newUser)
                .when()
                .post("/user/create")
                .then()
                .statusCode(200)
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Doe"))
                .body("title", equalTo("mr"))
                .body("id", notNullValue())
                .extract().response();

        User createdUser = response.as(User.class);

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Created User ID: " + createdUser.getId());
        System.out.println("Created User Name: " + createdUser.getFirstName() + " " + createdUser.getLastName());
        System.out.println("Created User Email: " + createdUser.getEmail());
        System.out.println("Created User Title: " + createdUser.getTitle());

        assertNotNull(createdUser.getId());
        assertEquals("John", createdUser.getFirstName());
        assertEquals("Doe", createdUser.getLastName());
        assertEquals("mr", createdUser.getTitle());
        assertNotNull(createdUser.getEmail());

        System.out.println("✅ Create User Test PASSED");
    }

    @Test
    @Order(5)
    @DisplayName("Positive Test 5: Get User Posts - Should return user's posts")
    public void testGetUserPosts() {
        System.out.println("\n=== Testing GET User Posts ===");

        String userId = "60d0fe4f5311236168a109d4"; // Sample user ID

        Response response = given()
                .spec(requestSpec)
                .pathParam("id", userId)
                .when()
                .get("/user/{id}/post")
                .then()
                .statusCode(200)
                .body("data", notNullValue())
                .extract().response();

        int postsCount = response.jsonPath().getList("data").size();

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("User ID: " + userId);
        System.out.println("Number of Posts: " + postsCount);

        if (postsCount > 0) {
            System.out.println("First Post ID: " + response.jsonPath().getString("data[0].id"));
            System.out.println("First Post Text Preview: " +
                    response.jsonPath().getString("data[0].text").substring(0,
                            Math.min(50, response.jsonPath().getString("data[0].text").length())) + "...");
        }

        // Verify posts structure
        assertNotNull(response.jsonPath().getList("data"));
        assertTrue(response.getStatusCode() == 200);

        System.out.println("✅ Get Posts Test PASSED");
    }

    // NEGATIVE TEST CASE

    @Test
    @Order(6)
    @DisplayName("Negative Test 1: Get User with Invalid ID - Should return error")
    public void testGetUserWithInvalidId() {
        System.out.println("\n=== Testing GET User with Invalid ID ===");

        String invalidUserId = "invalidUserId123";

        Response response = given()
                .spec(requestSpec)
                .pathParam("id", invalidUserId)
                .when()
                .get("/user/{id}")
                .then()
                .statusCode(anyOf(equalTo(400), equalTo(404))) // Accept either 400 or 404
                .extract().response();

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Invalid User ID Used: " + invalidUserId);
        System.out.println("Error Response: " + response.getBody().asString());

        // Verify error response
        assertTrue(response.getStatusCode() >= 400, "Should return client error status code (4xx)");
        assertTrue(response.getStatusCode() < 500, "Should be client error, not server error");

        // Check if error message exists
        String responseBody = response.getBody().asString();
        assertFalse(responseBody.isEmpty(), "Error response should not be empty");

        // Verify error response contains error field
        assertTrue(responseBody.contains("error"), "Response should contain error field");

        // Verify error type is PARAMS_NOT_VALID
        String errorType = response.jsonPath().getString("error");
        assertEquals("PARAMS_NOT_VALID", errorType, "Error type should be PARAMS_NOT_VALID");

        // Verify response does NOT contain user fields
        assertNull(response.jsonPath().getString("id"), "Response should not contain user id");
        assertNull(response.jsonPath().getString("firstName"), "Response should not contain firstName");
        assertNull(response.jsonPath().getString("lastName"), "Response should not contain lastName");
        assertNull(response.jsonPath().getString("email"), "Response should not contain email");

        System.out.println("✅ Invalid User ID Test PASSED - Error handled correctly");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("\n=== Test Suite Completed ===");
        System.out.println("All tests have been executed.");
        System.out.println("Check individual test results above for details.");
    }
}
