package tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

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
        String photoFileName = "1518521058110646316.jpg";
        String[] stringArray = new String[] { "Reading", "Music" };
        String address = "Forrest str, 5, 534533";
        String state = "Haryana";
        String city = "Panipat";

        open("https://demoqa.com/automation-practice-form");
        $(".main-header").shouldHave(text("Practice Form")).should(Condition.appear);

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $(byValue(gender)).sendKeys("/t ");
        $("#userNumber").setValue(phone);
        //Date of Birth
        typeDateOfBirth(dateOfBirth);
        //Hobby
        for (String s : stringArray)
        {
            $("#hobbiesWrapper").findElement(byText(s)).click();
        }

        $("#uploadPicture").uploadFromClasspath(photoFileName);

        //Address
        $("#currentAddress").scrollTo();
        $("#currentAddress").setValue(address);
        selectInDropDownList("state", state);
        selectInDropDownList("city", city);

        //Submit form
        $("#submit").scrollTo().click();

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
        $(".modal-body").shouldHave(text(state+" "+city));

        $("#closeLargeModal").scrollTo().click();
    }

    private void typeDateOfBirth(String dateOfBirth)
    {
        $("#dateOfBirthInput").click();
        $("#dateOfBirthInput").sendKeys(dateOfBirth);
        for(int i=0; i<11; i++)
            $("#dateOfBirthInput").sendKeys(Keys.LEFT);
        for(int i=0; i<11; i++)
            $("#dateOfBirthInput").sendKeys(Keys.BACK_SPACE);
        $("#dateOfBirthInput").pressEnter();
    }

    private void selectInDropDownList(String type, String name)
    {
        SelenideElement selectState = $("#" + type + " div[class $= '-placeholder']");
        selectState.scrollTo().click();
        SelenideElement dropDownStateList = $("div[class $= '-menu']").should(appear);
        dropDownStateList.findElement(byText(name)).click();
    }

    private String getHindiDate(String dateOfBirth)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate dateTime = LocalDate.parse(dateOfBirth, formatter);
        DateTimeFormatter formatterOutput = DateTimeFormatter.ofPattern("dd MMMM,yyyy", Locale.ENGLISH);
        return dateTime.format(formatterOutput);
    }
}
