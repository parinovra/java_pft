package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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

        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
        assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
    }

    private String mergePhones(ContactData contact) {
//        это старая школа
//        String result = "";
//        if (contact.getHome() != null) {
//            result = result + contact.getHome();
//        }
//        return result;

//        а это новая с помощью элементов функционального программирования
        return Arrays.asList(contact.getHome(), contact.getMobile(), contact.getWork())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactPhoneTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", ""); //регулярные выражения внутри
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> !s.equals("")).collect(Collectors.joining("\n"));
    }
}
