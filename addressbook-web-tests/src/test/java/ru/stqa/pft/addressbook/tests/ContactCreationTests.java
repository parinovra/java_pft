package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
        app.contact().returnToHomePage();
    }

    @Test(enabled = true)
    public void testContactCreation() throws Exception {
        app.contact().returnToHomePage();
        Contacts before = app.contact().all();
        ContactData contact = new ContactData().withFirstName("John").withLastName("Doe").withMobile("89001234567")
                .withEmail("johndoe@test.com").withGroup("test1");
        app.contact().create(contact, true);
        app.contact().returnToHomePage();
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }

    @Test(enabled = true)
    public void testBadContactCreation() throws Exception {
        app.contact().returnToHomePage();
        Contacts before = app.contact().all();
        ContactData contact = new ContactData().withFirstName("John'").withLastName("Doe'").withMobile("88001234567")
                .withEmail("johndoe@test.com").withGroup("test1");
        app.contact().create(contact, true);
        app.contact().returnToHomePage();
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before));
    }
}
