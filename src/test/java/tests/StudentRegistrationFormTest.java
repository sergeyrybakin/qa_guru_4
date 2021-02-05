package tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
        String dateOfBirth = "9 Dec 1961";
        Map<String, String> subject = new HashMap<>();
        subject.put("a", "Maths");
        subject.put("c", "Computer Science");
        subject.put("e", "English");

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
        String formattedDateOfBirth = formatDateOfBirth(dateOfBirth);
        typeDateOfBirth(formattedDateOfBirth);
        //Subject
        selectSubjects(subject);
        //Hobby
        selectHobby(stringArray);

        $("#uploadPicture").uploadFromClasspath(photoFileName);

        //Address
        $("#currentAddress").scrollTo().setValue(address);
        selectInDropDownList("state", state);
        selectInDropDownList("city", city);

        //Submit form
        $("#submit").scrollTo().click();

        //Verifications
        $(".modal-content").should(appear);
        SelenideElement modalBody = $(".modal-body");
        modalBody.shouldHave(text(firstName));
        modalBody.shouldHave(text(lastName));
        modalBody.shouldHave(text(email));
        modalBody.shouldHave(text(gender));
        modalBody.shouldHave(text(getHindiDate(formattedDateOfBirth)));
        modalBody.shouldHave(text(phone));

        subject.forEach((k,v)->
            modalBody.shouldHave(text(v))
        );

        for (String s : stringArray)
        {
            modalBody.shouldHave(text(s));
        }
        modalBody.shouldHave(text(photoFileName));
        modalBody.shouldHave(text(address));
        modalBody.shouldHave(text(state+" "+city));

        $("#closeLargeModal").scrollTo().click();
    }

    private void selectHobby(String[] stringArray)
    {
        for (String s : stringArray)
        {
            $("#hobbiesWrapper").findElement(byText(s)).click();
        }
    }

    private void selectSubjects(Map<String, String> subject)
    {
        subject.forEach((k,v)->{
            $("#subjectsInput").setValue(k);
            $(".subjects-auto-complete__menu-list").should(appear);
            $(".subjects-auto-complete__menu-list").findElement(byText(v)).click();
                });
    }

    private String formatDateOfBirth(String dateOfBirth)
    {
        String s;
        if(dateOfBirth.indexOf(' ') == 1)
            s = "0" + dateOfBirth;
        else
            s = dateOfBirth;
        return  s;
    }

    private void typeDateOfBirth(String dateOfBirth)
    {
        SelenideElement dateOfBirthInput = $("#dateOfBirthInput");
        dateOfBirthInput.click();
        int amountOfChars = dateOfBirthInput.getValue().length();
        dateOfBirthInput.sendKeys(dateOfBirth);
        for(int i=0; i<dateOfBirth.length()-1; i++)
            dateOfBirthInput.sendKeys(Keys.LEFT);
        for(int i=0; i<amountOfChars; i++)
            dateOfBirthInput.sendKeys(Keys.BACK_SPACE);
        dateOfBirthInput.pressEnter();
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
