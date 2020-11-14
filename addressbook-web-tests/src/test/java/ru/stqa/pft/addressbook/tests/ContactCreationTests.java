package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        if (! app.getGroupHelper().isThereAGroup()) {
            app.getNavigationHelper().gotoGroupPage();
            app.getGroupHelper().createGroup(new GroupData("test1", null, null));
        }
        app.getContactHelper().createContact(new ContactData("John", "Doe", "88001234567", "johndoe@mail.com", "test1"), true);
    }
}
