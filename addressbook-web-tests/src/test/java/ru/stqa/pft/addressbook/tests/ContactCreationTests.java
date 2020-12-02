package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @BeforeMethod(enabled = false)
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
        app.contact().returnToHomePage();
    }

    @DataProvider
    public Iterator<Object[]> validContacts() {
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[]{"Сидор1", "Сидоров2", "Питер-1", "84950123456", "89009999999", "84996543210",
                "email1@test1.com", "email2@test2.com", "email3@test2.com"});
        list.add(new Object[]{"Сидор2", "Сидоров2", "Питер-2", "84950123456", "89009999999", "84996543210",
                "email1@test1.com", "email2@test2.com", "email3@test2.com"});
        list.add(new Object[]{"Сидор3", "Сидоров2", "Питер-3", "84950123456", "89009999999", "84996543210",
                "email1@test1.com", "email2@test2.com", "email3@test2.com"});
        return list.iterator();
    }

    //    @Test(enabled = true)
    @Test(dataProvider = "validContacts")
    public void testContactCreation(String firstName, String lastName, String address, String home, String mobile,
                                    String work, String email, String email2, String email3) throws Exception {
        File photo = new File("src/test/resources/stru.png");
        ContactData contact = new ContactData().withFirstName(firstName).withLastName(lastName).withAddress(address).withHome(home)
                .withMobile(mobile).withWork(work).withEmail(email).withEmail2(email2).withEmail3(email3)
                .withGroup("test1").withPhoto(photo);
/*
            ContactData contact = new ContactData().withFirstName("John").withLastName("Doe").withMobile("89001234567")
                    .withEmail("johndoe@test.com").withGroup("test1").withPhoto(photo); //здесь подбросить переменную name
 */
        app.contact().returnToHomePage();
        Contacts before = app.contact().all();
        app.contact().create(contact, true);
        app.contact().returnToHomePage();
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }

    @Test(enabled = false)
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

//    @Test
//    public void testCurrentDir() {
//        File currentDir = new File("."); //точка - текущая директория
//        System.out.println(currentDir.getAbsolutePath());
//        File photo = new File("src/test/resources/stru.png");
//        System.out.println(photo.getAbsolutePath());
//        System.out.println(photo.exists());
//    }
}
