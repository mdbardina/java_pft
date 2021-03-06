package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by bardina_md on 16.08.17.
 */
public class ContactAddressTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().homePage();
        if (app.contact().all().size() ==0){
            app.contact().createContact(new ContactData().withName("mashatest").withLastname("mashatest1").withMobilePhone("9999999").withAddress("Address3"));
        }
    }

    @Test

    public void testContactAddress(){
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData infoFormContactForm = app.contact().infoFormContactForm(contact);

        assertThat(contact.getAllAddress(), equalTo(mergeAddress(infoFormContactForm)));
    }

    private String mergeAddress(ContactData contact) {
        return Stream.of(contact.getAddress()).filter((s) -> ! s.equals(""))
                .collect(Collectors.joining("\n"));
    }

}
