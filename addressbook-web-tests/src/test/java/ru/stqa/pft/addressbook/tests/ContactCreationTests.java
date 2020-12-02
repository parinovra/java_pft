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
        File photo = new File("src/test/resources/stru.png");
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[] {new ContactData().withFirstName("Сидор1").withLastName("Сидоров1").withAddress("Питер-1")
                .withHome("84950123456").withMobile("89009999999").withWork("84996543210").withEmail("email1@test1.com")
                .withEmail2("email2@test2.com").withEmail3("email3@test2.com").withGroup("test1").withPhoto(photo)});
        list.add(new Object[] {new ContactData().withFirstName("Сидор2").withLastName("Сидоров2").withAddress("Питер-2")
                .withHome("84950123456").withMobile("89009999999").withWork("84996543210").withEmail("email1@test1.com")
                .withEmail2("email2@test2.com").withEmail3("email3@test3.com").withGroup("test1").withPhoto(photo)});
        list.add(new Object[] {new ContactData().withFirstName("Сидор3").withLastName("Сидоров3").withAddress("Питер-3")
                .withHome("84950123456").withMobile("89009999999").withWork("84996543210").withEmail("email1@test1.com")
                .withEmail2("email2@test2.com").withEmail3("email3@test3.com").withGroup("test1").withPhoto(photo)});
        return list.iterator();
    }

    //    @Test(enabled = true)
    @Test(dataProvider = "validContacts")
    public void testContactCreation(ContactData contact) throws Exception {
//        ContactData contact = new ContactData().withFirstName(firstName).withLastName(lastName).withAddress(address).withHome(home)
//                .withMobile(mobile).withWork(work).withEmail(email).withEmail2(email2).withEmail3(email3)
//                .withGroup("test1").withPhoto(photo);
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
