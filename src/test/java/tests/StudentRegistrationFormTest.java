package tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
    void dataAppearsInModalPopUpWindow()
    {
        String firstName = "Michail";
        String lastName = "theBear";
        String email = "m.thebear@mail.ru";
        String gender = "Male";
        String phone = "1234567890";
        String dateOfBirth = "19 Dec 1961";
        String address = "Forrest str, 5";
        String photoFileName = "1518521058110646316.jpg";
        String[] stringArray = new String[] { "Reading", "Music" };

        open("https://demoqa.com/automation-practice-form");
        $(".main-header").shouldHave(text("Practice Form")).should(Condition.appear);

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $(byValue(gender)).sendKeys("/t ");
        $("#userNumber").setValue(phone);
        //Date of Birth
        $("#dateOfBirthInput").click();
        $("#dateOfBirthInput").sendKeys(dateOfBirth);
        for(int i=0; i<11; i++)
            $("#dateOfBirthInput").sendKeys(Keys.LEFT);
        for(int i=0; i<11; i++)
            $("#dateOfBirthInput").sendKeys(Keys.BACK_SPACE);
        $("#dateOfBirthInput").pressEnter();
        //Hobby
        for (String s : stringArray)
        {
            $("#hobbiesWrapper").findElement(byText(s)).click();
        }

        $("#uploadPicture").uploadFromClasspath(photoFileName);

        $("#currentAddress").scrollTo();
        $("#currentAddress").setValue(address);

        $("#submit").scrollTo();
        $("#submit").click();

        sleep(1000);

        //Verifications
        $(".modal-content").should(appear);
        $(".modal-body").shouldHave(text(firstName));
        $(".modal-body").shouldHave(text(lastName));
        $(".modal-body").shouldHave(text(email));
        $(".modal-body").shouldHave(text(gender));
        $(".modal-body").shouldHave(text(getHindiDate(dateOfBirth)));
        $(".modal-body").shouldHave(text(phone));
        for (String s : stringArray)
        {
            $(".modal-body").shouldHave(text(s));
        }
        $(".modal-body").shouldHave(text(photoFileName));
        $(".modal-body").shouldHave(text(address));

    }

    private String getHindiDate(String dateOfBirth)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate dateTime = LocalDate.parse(dateOfBirth, formatter);
        DateTimeFormatter formatterOutput = DateTimeFormatter.ofPattern("dd MMMM,yyyy", Locale.ENGLISH);
        String hindiDate = dateTime.format(formatterOutput);
        return hindiDate;
    }
}
