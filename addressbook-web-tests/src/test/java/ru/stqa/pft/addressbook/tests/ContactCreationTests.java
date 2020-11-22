package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

public class ContactCreationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.group().all().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test(enabled = true)
    public void testContactCreation() throws Exception {
        app.contact().returnToHomePage();
        Set<ContactData> before = app.contact().all();
        ContactData contact = new ContactData().withFirstName("John").withLastName("Doe").withMobile("88001234567")
                .withEmail("johndoe@test.com").withGroup("test1");
        app.contact().create(contact, true);
        app.contact().returnToHomePage();
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size() + 1);

        contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()); //max из всех идентификаторов
        before.add(contact);
        Assert.assertEquals(before, after);
    }
}
