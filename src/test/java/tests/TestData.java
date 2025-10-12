package tests;
import com.github.javafaker.Faker;

public class TestData {
    Faker faker = new Faker();

    protected String generateDataUserJson() {
        String userName = faker.name().username();
        String password = faker.phoneNumber().subscriberNumber(10);
        String email = faker.internet().emailAddress();
        return String.format("{\"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\"}", userName, email, password);
    }

        String email = faker.internet().emailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
}
