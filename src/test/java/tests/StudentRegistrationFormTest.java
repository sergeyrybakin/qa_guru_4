package tests;

import org.junit.jupiter.api.Test;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class StudentRegistrationFormTest
{
    @Test
    void dataAppearsInModalPopUpWindow() {
        open("https://demoqa.com/automation-practice-form");
        $(".main-header").shouldHave(Condition.text("Practice Form")).should(Condition.appear);

    }
}
