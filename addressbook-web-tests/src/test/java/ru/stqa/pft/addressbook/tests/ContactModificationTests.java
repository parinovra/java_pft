package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test 1"));
            app.contact().returnToHomePage();
        }
        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData().withFirstName("John").withLastName("Doe").withMobile("89001234567")
                    .withEmail("johndoe@test.com"), true);
        }
    }

    @Test(enabled = true)
    public void testContactModification() {
        Groups groups = app.db().groups();
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("Иван")
                .withLastName("Иванов").withAddress("Жмеринка").withHome("99912345678").withMobile("88007654321")
                .withWork("8887654321").withEmail("иваниванов@тест.рф").withEmail2("mod@mmail.com")
                .withEmail3("email3-mod@fedmail.com").inGroup(groups.iterator().next());
        app.contact().modify(contact);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
        verifyContactListInUI();
    }
}
