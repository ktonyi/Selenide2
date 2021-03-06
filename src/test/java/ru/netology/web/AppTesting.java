package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class AppTesting {
    @BeforeEach
    void setup () {
        open("http://localhost:9999");
    }
    @Test
    void shouldCardDeliveryWithAllData() {
        String dateToEnter = LocalDate.now().plusDays(10).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[data-test-id=date] input.input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input.input__control").setValue(dateToEnter);
        $("[name='name']").setValue("Сергей Миронов");
        $("[name='phone']").setValue("+71237654321");
        $("[class=checkbox__box]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(" [ data-test-id=notification]").shouldBe(visible, Duration.ofMillis(15000))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + dateToEnter));

    }

    @Test
    void shouldCardDeliveryWithoutCity(){

        $("[name='name']").setValue("Сергей Миронов");
        $("[name='phone']").setValue("+71237654321");
        $("[class=checkbox__box]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city] span.input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldCardDeliveryWithoutName(){

        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[name='phone']").setValue("+71237654321");
        $("[class=checkbox__box]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name] span.input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldCardDeliveryWithEngName(){

        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[name='name']").setValue("Sergey Mironov");
        $("[name='phone']").setValue("+71237654321");
        $("[class=checkbox__box]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name] span.input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldCardDeliveryWithoutPhone(){

        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[name='name']").setValue("Сергей Миронов");
        $("[class=checkbox__box]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone] span.input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldCardDeliveryWithoutCheckbox(){

        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[name='name']").setValue("Сергей Миронов");
        $("[name='phone']").setValue("+71237654321");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=agreement]").shouldHave(Condition.cssClass("input_invalid"));
    }
}

