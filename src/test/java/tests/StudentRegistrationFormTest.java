package tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selenide.*;

public class StudentRegistrationFormTest {

    @Test
    void dataAppearsInModalPopUpWindow() {
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
        selectDateOfBirth(formattedDateOfBirth);
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
        verifyPresence("Student Name", firstName + " " + lastName);
        verifyPresence("Student Email", email);
        verifyPresence("Gender", gender);
        verifyPresence("Mobile", phone);
        verifyPresence("Date of Birth", getHindiDate(formattedDateOfBirth));
        subject.forEach((k, v) ->
                verifyPresence("Subjects", v)
        );
        for (String s : stringArray)
            verifyPresence("Hobbies", s);
        verifyPresence("Picture", photoFileName);
        verifyPresence("Address", address);
        verifyPresence("State and City", state + " " + city);

        $("#closeLargeModal").scrollTo().click();
    }

    private void verifyPresence(String where, String whatExpected) {
        $$(".modal-body tr").filterBy(text(where)).last().shouldHave(text(whatExpected));
    }

    private void selectHobby(String[] stringArray) {
        for (String s : stringArray) {
            $("#hobbiesWrapper").findElement(byText(s)).click();
        }
    }

    private void selectSubjects(Map<String, String> subject) {
        subject.forEach((k,v)-> {
            $("#subjectsInput").setValue(k);
            $(".subjects-auto-complete__menu-list").should(appear);
            $(".subjects-auto-complete__menu-list").findElement(byText(v)).click();
        });
    }

    private String formatDateOfBirth(String dateOfBirth) {
        String s;
        if(dateOfBirth.indexOf(' ') == 1)
            s = "0" + dateOfBirth;
        else
            s = dateOfBirth;
        return  s;
    }

    private void selectDateOfBirth(String dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate dateTime = LocalDate.parse(dateOfBirth, formatter);
        int month = dateTime.getMonth().getValue() - 1;
        $("#dateOfBirthInput").click();
        $(".react-datepicker__year-select").click();
        $(".react-datepicker__year-select").$$("option").findBy(text(Integer.toString(dateTime.getYear()))).click();
        $(".react-datepicker__month-select").$$("option").get(month).click();
        $(".react-datepicker__header").click();
        $(".react-datepicker__day--0" + dateOfBirth.substring(0, 2) + ":not(.react-datepicker__day--outside-month)")
                .click();
    }

    private void selectInDropDownList(String type, String name) {
        SelenideElement selectState = $("#" + type + " div[class $= '-placeholder']");
        selectState.scrollTo().click();
        SelenideElement dropDownStateList = $("div[class $= '-menu']").should(appear);
        dropDownStateList.findElement(byText(name)).click();
    }

    private String getHindiDate(String dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate dateTime = LocalDate.parse(dateOfBirth, formatter);
        DateTimeFormatter formatterOutput = DateTimeFormatter.ofPattern("dd MMMM,yyyy", Locale.ENGLISH);
        return dateTime.format(formatterOutput);
    }
}