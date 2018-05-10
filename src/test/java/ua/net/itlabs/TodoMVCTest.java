package ua.net.itlabs;


import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Configuration.holdBrowserOpen;
import static com.codeborne.selenide.Selenide.*;



public class TodoMVCTest {

    @Before
    public void openSite() {
        ChromeDriverManager.getInstance().setup();
        browser = "chrome";
        open("http://todomvc.com/examples/emberjs/");
    }


    @Test
    public void providesCommonTasksManagement () {

        openSite();

        newTodo.shouldBe(visible);
        newTodo.shouldBe(empty);

        add("New task", "New task 1", "New task 2");
        read(exactTexts("New Task", "New Task 1", "New task 2"));

        edit("New task");
        cancelEdit("New changed task");

        complete("New Task 2");
        assertCompletedTasks("New Task 2");
        clearCompleted();

        assertActiveTasks("New Task", "New changed task");
        todoList.filterBy(cssClass("active")).shouldBe(CollectionCondition.empty);

        delete("New Task 1");
        read(exactTexts("New changed task"));

        holdBrowserOpen = true;
    }

//Variables
    SelenideElement newTodo = $("#new-todo");
    ElementsCollection todoList = $$("#todo-list>li");

//Methods
    private void add(String... taskTexts) {
        for (String text: taskTexts) {
            newTodo.setValue(text).pressEnter();
        }
    }

    private void read(CollectionCondition existed_task) {
        $$("#todo-list>li").shouldHave(existed_task);
    }

    private void edit(String text) {
        todoList.findBy(exactText(text)).doubleClick();
        todoList.findBy(cssClass("editing")).find(".edit").setValue("New changed task").pressEnter();
    }

    private void complete(String text) {
        todoList.findBy(exactText(text)).find(".toggle").click();
    }

    private void delete(String text) {
        todoList.findBy(exactText(text)).hover().find(".destroy").click();
    }

    private void assertActiveTasks(String... texts) {
        todoList.filterBy(cssClass("a.ember-view")).shouldHave(exactTexts(texts));
    }

    private void assertCompletedTasks(String... texts) {
        todoList.filterBy(cssClass("completed")).shouldHave(exactTexts(texts));
    }

    private void cancelEdit(String text) {
        todoList.findBy(exactText(text)).doubleClick();
        todoList.findBy(cssClass("editing")).find(".edit").setValue("Canceled edition").pressEscape();
    }

    private void clearCompleted() {
        $("#clear-completed").click();
    }
}







