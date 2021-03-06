package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.testng.Assert.assertEquals;

/**
 * Created by bardina_md on 30.08.17.
 */
public class AddContactToGroup extends TestBase {

    @BeforeMethod
    public void ensureContactPreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().homePage();
            app.contact().createContact(new ContactData().withName("mashatest").withLastname("mashatest1").withMobilePhone("9999999"));
        }
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("mashatest"));
        }
    }


    @Test

    public void addContactToGroup() {
        app.goTo().homePage();
        Contacts before = app.db().contacts();
        ContactData addContact =  before.iterator().next();
        app.contact().contactToGroup(addContact);
        Contacts after = app.db().contacts();
        ContactData next = after.iterator().next();
        assertEquals(next.getGroups().size(), 1);
    }
}
