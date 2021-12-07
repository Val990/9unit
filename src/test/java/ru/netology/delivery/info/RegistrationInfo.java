package ru.netology.delivery.info;

import lombok.*;

@Data
@RequiredArgsConstructor
public class RegistrationInfo {
    private final String city;
    private final String dateOne;
    private final String dateTwo;
    private final String name;
    private final String phone;

}