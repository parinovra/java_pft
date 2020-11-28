package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactPhoneTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
        app.contact().returnToHomePage();

        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstName("111").withLastName("222").withHome("111")
                    .withMobile("222").withWork("333").withEmail("111.222@").withGroup("test1"), true);
        }
    }

    @Test
    public void testContactPhones() {
//        app.goTo().gotoHomepage(); //в лекции, а у меня в ApplicationManager такого перехода нет, потому заюзаю строчку ниже
        app.contact().returnToHomePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    }
}
