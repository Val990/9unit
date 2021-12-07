package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
import ru.netology.delivery.info.RegistrationInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@UtilityClass
public class DataGenerator {

    @UtilityClass
    public static class Registration {
        public static RegistrationInfo generateInfo(String locate) {
            Faker faker = new Faker(new Locale(locate));
            DateTimeFormatter date = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            return new RegistrationInfo(
                    faker.address().city(),
                    date.format(LocalDate.now().plusDays(3)),
                    date.format(LocalDate.now().plusDays(5)),
                    faker.name().fullName(),
                    faker.phoneNumber().phoneNumber());
        }
    }
}