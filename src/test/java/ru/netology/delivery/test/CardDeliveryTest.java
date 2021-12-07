package ru.netology.delivery.test;
import io.qameta.allure.selenide.AllureSelenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;
import ru.netology.delivery.info.RegistrationInfo;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    RegistrationInfo info;

    @BeforeEach
    public void shouldOpenForm() {
        info = DataGenerator.Registration.generateInfo("ru");
    }

    @Test
    public void shouldSendRequestHappyPath() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue(info.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(info.getDateOne());
        $("[data-test-id='name'] input").setValue(info.getName());
        $("[data-test-id='phone'] input").setValue(info.getPhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='success-notification']").shouldBe(appear).shouldHave(text("Встреча успешно запланирована на " + info.getDateOne()));

        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(info.getDateTwo());
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='replan-notification']").shouldBe(appear).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id='success-notification']").shouldBe(appear).shouldHave(text("Встреча успешно запланирована на " + info.getDateTwo()));
    }

    @Test
    public void shouldSendFormWrongName() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue(info.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(info.getDateOne());
        $("[data-test-id=name] input").setValue("Ivanov Ivan");
        $("[data-test-id=phone] input").setValue(info.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=name] .input__sub").shouldBe(appear).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldSendFormWrongPhone() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue(info.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(info.getDateOne());
        $("[data-test-id=name] input").setValue(info.getName());
        $("[data-test-id=phone] input").setValue("8192");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=phone] .input__sub").shouldBe(appear).shouldHave(text("Телефон указан неверно."));
    }

}