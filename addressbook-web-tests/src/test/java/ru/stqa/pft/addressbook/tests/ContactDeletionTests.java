package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() throws Exception {
        if (! app.getContactHelper().isThereAContact()) {
          app.getContactHelper().createContact(new ContactData("John", "Doe", "88001234567", "johndoe@mail.com", "test1"), true);
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContacts();
    }
}
