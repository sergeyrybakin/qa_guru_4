package tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selenide.*;

public class StudentRegistrationFormTest
{
    @Test
    void dataAppearsInModalPopUpWindow() {
        String firstName = "Michail";
        String lastName = "theBear";
        String email = "m.thebear@mail.ru";
        String gender = "Male";
        String phone = "1234567890";
        String dateOfBirth = "19 Dec 1961";

        open("https://demoqa.com/automation-practice-form");
        $(".main-header").shouldHave(text("Practice Form")).should(Condition.appear);

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $(byValue(gender)).sendKeys("/t ");
        $("#userNumber").setValue(phone);

        $("#dateOfBirthInput").click();
        $("#dateOfBirthInput").sendKeys(dateOfBirth);
        for(int i=0; i<11; i++)
            $("#dateOfBirthInput").sendKeys(Keys.LEFT);
        for(int i=0; i<11; i++)
            $("#dateOfBirthInput").sendKeys(Keys.BACK_SPACE);
        $("#dateOfBirthInput").pressEnter();

        $("#hobbiesWrapper").findElement(byText("Reading")).click();
        $("#hobbiesWrapper").findElement(byText("Music")).click();

        $("#uploadPicture").uploadFromClasspath("1518521058110646316.jpg");

        $("#submit").scrollTo();
        $("#submit").click();

        sleep(1000);

        $(".modal-content").should(appear);
//
//        $("#output").shouldHave(text(name), text("aa@aa.aa"));
    }
}
