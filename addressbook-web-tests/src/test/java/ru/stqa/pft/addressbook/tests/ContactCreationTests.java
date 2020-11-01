package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.initContactCreation();
    app.fillContactForm(new ContactData("John", "Doe", "88001234567", "johndoe@mail.com"));
    app.submitContactCreation();
    app.returnToHomePage();
  }
}