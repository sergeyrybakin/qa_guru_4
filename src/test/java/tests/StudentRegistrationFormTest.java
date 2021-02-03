package tests;

import org.junit.jupiter.api.Test;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selenide.*;

public class StudentRegistrationFormTest
{
    @Test
    void dataAppearsInModalPopUpWindow() {
        String firstName = "Michail";
        String lastName = "Lomonosov";
        String email = "m.lomonosov@mail.ru";
        String gender = "Male";
        String phone = "1234567890";

        open("https://demoqa.com/automation-practice-form");
        $(".main-header").shouldHave(text("Practice Form")).should(Condition.appear);

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $(byValue(gender)).sendKeys("/t ");

        sleep(1000);
//        $("#currentAddress").setValue("currentAddress here");
//        $("#permanentAddress").setValue("permanentAddress here");
//        $("#submit").click();
//
//        $("#output").shouldHave(text(name), text("aa@aa.aa"));
    }
}
