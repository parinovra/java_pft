package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
    app.getGroupHelper().selectContact();
    app.getGroupHelper().deleteSelectedContacts();
    app.getGroupHelper().returnToHomePage();
  }
}