package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactFromGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test 11"));
            app.contact().returnToHomePage();
        }
        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData().withFirstName("Леша 11").withLastName("Иванов 11"), true);
        }
        //здесь будет третья проверка, есть ли группа, куда контакт не добавлен
//        if (app.db().contactInGroups().size() != 0) {
//            app.goTo().groupPage();
//            app.group().create(new GroupData().withName("test 12"));
//            app.contact().returnToHomePage();

        //это другой вариант, когда создается новый контакт, а не группа
//            app.contact().create(new ContactData().withFirstName("Леша 12").withLastName("Иванов 12"), true);
//        }
    }

    @Test(enabled = true)
    public void testRemoveContactFromTheGroup() {
        Contacts beforeContacts = app.db().contacts();
        ContactData selectContact = beforeContacts.iterator().next();
        Groups beforeGroups = app.db().groups();
        GroupData selectGroup = beforeGroups.iterator().next();
        app.contact().returnToHomePage();
        if (selectContact.getGroups().isEmpty() || !selectContact.getGroups().contains(selectGroup)) {
            app.contact().selectGroupByName("[all]");
            app.contact().add(selectContact, selectGroup);
            assertThat(selectContact.getGroups().withAdded(selectGroup), equalTo(app.db().contacts().stream()
                    .filter((c) -> c.getId() == selectContact.getId()).collect(Collectors.toList()).get(0).getGroups()));
            app.contact().returnToHomePage();
        }
        app.contact().remove(selectContact, selectGroup);
        app.contact().returnToHomePage();
        app.contact().selectGroupByName("[all]");
        assertThat(selectContact.getGroups().without(selectGroup), equalTo(app.db().contacts().stream().
                filter((c) -> c.getId() == selectContact.getId()).collect(Collectors.toList()).get(0).getGroups()));
    }
}
