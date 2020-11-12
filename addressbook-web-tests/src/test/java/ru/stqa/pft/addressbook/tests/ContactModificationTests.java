package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        if (! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("John", "Doe", "88001234567", "johndoe@mail.com", "test1"), true);
        }
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Иван", "Иванов", "89520123456", "иваниванов@почта.рф", null), false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();
    }
}
