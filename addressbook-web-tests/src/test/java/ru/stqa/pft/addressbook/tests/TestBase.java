package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by marru on 03.06.2017.
 */
public class TestBase {

    org.slf4j.Logger logger = LoggerFactory.getLogger(TestBase.class);


    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite
    public void tearDown() {
        app.stop();
    }

    @BeforeMethod (alwaysRun = true)
    public void lotTestStart(Method m, Object [] p){
        logger.info("Start test" +m.getName() + "with parameters" + Arrays.asList(p));

    }

    @AfterMethod (alwaysRun = true)
    public void logTestStop(Method m){
        logger.info("Stop test" +m.getName());

    }

    void verifyGroupListInUi(){
        if (Boolean.getBoolean("verivyUI")){
            Groups dbGroups= app.db().groups();
            Groups uiGroups= app.group().all();
            assertThat(uiGroups, equalTo(dbGroups.stream()
                    .map((g)-> new GroupData().withId(g.getId()).withName(g.getName()))
                    .collect(Collectors.toSet())));
        }

    }

    void verifyContactListInUi(){
        if (Boolean.getBoolean("verivyUI")){
            Contacts dbContacts= app.db().contacts();
            Contacts uiContacts= app.contact().all();
            assertThat(uiContacts, equalTo(dbContacts.stream()
                    .map((g)-> new ContactData().withId(g.getId()).withName(g.getName()))
                    .collect(Collectors.toSet())));
        }

    }

}
