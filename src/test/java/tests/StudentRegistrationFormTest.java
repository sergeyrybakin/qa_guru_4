package tests;

import org.junit.jupiter.api.Test;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class StudentRegistrationFormTest {

    @Test
    void dataAppearsInModalPopUpWindow() {
        String firstName = "Michail";
        String lastName = "theBear";
        String email = "m.thebear@mail.ru";
        String gender = "Male";
        String phone = "1234567890";
        String dayOfBirth = "1";
        String monthOfBirth = "December";
        String yearOfBirth = "1961";
        String subject = "Computer Science";
        String photoFileName = "1518521058110646316.jpg";
        String hobby = "Reading";
        String address = "Forrest str, 5, 534533";
        String state = "Haryana";
        String city = "Panipat";

        open("https://demoqa.com/automation-practice-form");
        $(".main-header").shouldHave(text("Practice Form")).should(Condition.appear);

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $$("#genterWrapper label").findBy(text(gender)).click();
        $("#userNumber").setValue(phone);
        //Date of Birth
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").click();
        $$(".react-datepicker__month-select option").findBy(matchText(monthOfBirth)).click();
        $(".react-datepicker__year-select").click();
        $$(".react-datepicker__year-select option").findBy(text(yearOfBirth)).click();
        String prefilledDay = (dayOfBirth.length()>1 ? dayOfBirth : "0" + dayOfBirth);
        $(".react-datepicker__day--0" + prefilledDay + ":not(.react-datepicker__day--outside-month)").click();
        //Subject
        $("#subjectsInput").val(subject.substring(0, 1));
        $$(".subjects-auto-complete__menu-list div").findBy(text(subject)).click();
        //Hobby
        $$("#hobbiesWrapper .custom-control label").findBy(text(hobby)).click();
        //Picture
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
        verifyPresence("Date of Birth",  prefilledDay + " " + monthOfBirth + "," + yearOfBirth);
        verifyPresence("Subject", subject);
        verifyPresence("Hobbies", hobby);
        verifyPresence("Picture", photoFileName);
        verifyPresence("Address", address);
        verifyPresence("State and City", state + " " + city);

        $("#closeLargeModal").scrollTo().click();
    }

    private void verifyPresence(String where, String whatExpected) {
        $$(".modal-body tr").filterBy(text(where)).last().shouldHave(text(whatExpected));
    }

    private void selectInDropDownList(String type, String name) {
        $("#" + type + " div[class $= '-placeholder']").scrollTo().click();
        $("div[class $= '-menu']").should(appear).findElement(byText(name)).click();
    }
}