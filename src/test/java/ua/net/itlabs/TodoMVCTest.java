package ua.net.itlabs;


import com.codeborne.selenide.CollectionCondition;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Configuration.holdBrowserOpen;
import static com.codeborne.selenide.Selenide.open;


public class TodoMVCTest {

    MainPage mainPage = new MainPage();

    @Before
    public void openSite() {
        ChromeDriverManager.getInstance().setup();
        browser = "chrome";
        open("http://todomvc.com/examples/emberjs/");
    }

    @Test
    public void providesCommonTasksManagement () {

        openSite();

        mainPage.newTodo.shouldBe(visible);
        mainPage.newTodo.shouldBe(empty);

        mainPage.add("New task", "New task 1", "New task 2");
        mainPage.read(exactTexts("New Task", "New Task 1", "New task 2"));

        mainPage.edit("New task");
        mainPage.cancelEdit("New changed task");

        mainPage.complete("New Task 2");
        mainPage.assertCompletedTasks("New Task 2");
        mainPage.clearCompleted();

//        assertActiveTasks("New Task", "New changed task");
        mainPage.todoList.filterBy(cssClass("active")).shouldBe(CollectionCondition.empty);

        mainPage.delete("New Task 1");
        mainPage.read(exactTexts("New changed task"));

        holdBrowserOpen = true;
    }

    @Test
    public void filterTasks () {
        openSite();
        mainPage.add("New task", "New task 1", "New task 2");
        mainPage.read(exactTexts("New Task", "New Task 1", "New task 2"));
        mainPage.complete("New Task 2");
    }


}







