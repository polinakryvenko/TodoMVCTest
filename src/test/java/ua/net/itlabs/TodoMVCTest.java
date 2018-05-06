package ua.net.itlabs;


import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Configuration.holdBrowserOpen;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;


public class TodoMVCTest {

    @Test
    public void testCreateTask () {

        ChromeDriverManager.getInstance().setup();
        browser = "chrome";

        open("http://todomvc.com/examples/emberjs/");

        $(byXpath("//*[@id='new-todo']")).shouldBe(enabled).setValue("New Task 0").pressEnter();
        $(byXpath("//*[@id='new-todo']")).setValue("New Task 1").pressEnter();
        $(byXpath("//*[@id='new-todo']")).setValue("New Task 2").pressEnter();

        $$(byXpath("//*[@id='todo-list']//li")).shouldHave(exactTexts("New Task 0", "New Task 1", "New Task 2"));
        $(byXpath(".//*[@id='todo-list']//li//*//label[contains(text(), 'New Task 2')]//..//*[@class='toggle']")).click();

        $$(byXpath(".//*[@id='todo-list']//li//*//label[contains(text(), 'New Task 2')]//..//*[@class='completed ember-view']"));
        $$(byXpath(".//*[@id='todo-list']//li//*//label[contains(text(), 'New Task 0', 'New Task 1)]//..//*[@class='ember-view']"));

        holdBrowserOpen = true;

    }

}



