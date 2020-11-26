package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditionGroup() {
        if (app.group().all().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @BeforeMethod
    public void ensurePreconditionContact() {
        if (app.contact().all().size() == 0) {
            app.contact().returnToHomePage();
            ContactData contact = new ContactData().withFirstName("111").withLastName("222").withHomePhone("111")
                    .withMobilePhone("222").withWorkPhone("333").withEmail("111.222@").withGroup("test1");
            app.contact().create(contact, true);
            app.contact().returnToHomePage();
        }
    }

    @Test
    public void testContactPhones() {
        app.goTo().gotoHomePage();
        //app.returnToHomePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getHomePhone(), equalTo(contactInfoFromEditForm.getHomePhone()));
        assertThat(contact.getMobilePhone(), equalTo(contactInfoFromEditForm.getMobilePhone()));
        assertThat(contact.getWorkPhone(), equalTo(contactInfoFromEditForm.getWorkPhone()));
    }
}
