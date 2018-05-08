package ua.net.itlabs;


import com.codeborne.selenide.CollectionCondition;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Configuration.holdBrowserOpen;
import static com.codeborne.selenide.Selenide.*;



public class TodoMVCTest {


    @Test

    public void providesCommonTasksManagement () {

//Open site
        ChromeDriverManager.getInstance().setup();
        browser = "chrome";
        open("http://todomvc.com/examples/emberjs/");

//Add
        $("#new-todo").shouldBe(visible);
        $("#new-todo").shouldBe(empty);
        add("New task");
        add("New task 1");
        add("New task 2");

//Edit
        $$("#todo-list li").shouldHave(exactTexts("New Task", "New Task 1", "New task 2"));
        $$("#todo-list li").findBy(exactText("New Task 1")).doubleClick();
        $$("#todo-list>li").findBy(cssClass("editing")).find(".edit").setValue("New changed task").pressEnter();

//Complete task
        $$("#todo-list>li").findBy(exactText("New task 2")).find(".toggle").click();
        $$("#todo-list li").findBy(exactText("New Task 2")).shouldHave(cssClass("completed"));
        $$("#todo-list>li").filterBy(cssClass("completed")).shouldHave(exactTexts("New Task 2"));//Complete task

//Cancel Edit
        $$("#todo-list li").findBy(exactText("New changed task")).doubleClick();
        $$("#todo-list>li").findBy(cssClass("editing")).find(".edit").setValue("Canceled edition").pressEscape();

//Delete all completed tasks
        $("#clear-completed").click();
        $$("#todo-list>li").filterBy(cssClass("active")).shouldBe(CollectionCondition.empty);

//Delete one task
        $$("#todo-list>li").findBy(exactText("New Task")).hover().find(".destroy").click();
        $$("#todo-list li").shouldHave(exactTexts("New changed task"));

        holdBrowserOpen = true;
    }

    private void add(String text) {

        $("#new-todo").setValue(text).pressEnter();
    }

}







//        $(byXpath("//*[@id='new-todo']")).shouldBe(enabled).setValue("New Task 0").pressEnter();
//        $(byXpath("//*[@id='new-todo']")).setValue("New Task 1").pressEnter();
//        $(byXpath("//*[@id='new-todo']")).setValue("New Task 2").pressEnter();
//
//        $$(byXpath("//*[@id='todo-list']//li")).shouldHave(exactTexts("New Task 0", "New Task 1", "New Task 2"));
//        $(byXpath(".//*[@id='todo-list']//li//*//label[contains(text(), 'New Task 2')]//..//*[@class='toggle']")).click();
//
//        $$(byXpath(".//*[@id='todo-list']//li//*//label[contains(text(), 'New Task 2')]//..//*[@class='completed ember-view']"));
//        $$(byXpath(".//*[@id='todo-list']//li//*//label[contains(text(), 'New Task 0', 'New Task 1)]//..//*[@class='ember-view']"));
