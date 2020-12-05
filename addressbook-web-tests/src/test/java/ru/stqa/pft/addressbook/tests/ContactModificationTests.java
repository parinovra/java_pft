package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

//    @BeforeMethod
//    public void ensurePreconditions() {
//        if (app.db().contacts().size() == 0) {
//
//        }
//        app.goTo().groupPage();
//        if (app.group().all().size() == 0) {
//            app.goTo().groupPage();
//            app.group().create(new GroupData().withName("test1"));
//        }
//        app.contact().returnToHomePage();
//
//        if (app.contact().all().size() == 0) {
//            app.contact().create(new ContactData().withFirstName("John").withLastName("Doe").withMobile("89001234567")
//                    .withEmail("johndoe@test.com").withGroup("test1"), true);
//        }
//    }

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
            app.contact().returnToHomePage();
        }
        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData().withFirstName("John").withLastName("Doe").withMobile("89001234567")
                    .withEmail("johndoe@test.com").withGroup("test1"), true);
        }
    }

    @Test(enabled = true)
    public void testContactModification() {
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("Иван")
                .withLastName("Иванов").withMobile("88007654321").withEmail("иваниванов@тест.рф");
        app.contact().modify(contact);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
