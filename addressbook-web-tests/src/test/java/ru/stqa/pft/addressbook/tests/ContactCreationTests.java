package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.group().list().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData("test2", null, null));
        }
    }

    @Test(enabled = false)
    public void testContactCreation() throws Exception {
        app.contact().returnToHomePage();
        List<ContactData> before = app.contact().list();
        ContactData contact = new ContactData("John", "Doe", "88001234567", "johndoe@mail.com", "test1");
        app.contact().create(contact, true);
        app.contact().returnToHomePage();
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() + 1);

        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
