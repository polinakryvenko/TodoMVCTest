package ua.net.itlabs;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    SelenideElement newTodo = $("#new-todo");
    ElementsCollection todoList = $$("#todo-list>li");


    public void add(String... taskTexts) {
        for (String text: taskTexts) {
            newTodo.setValue(text).pressEnter();
        }
    }

    public void read(CollectionCondition existed_task) {
        $$("#todo-list>li").shouldHave(existed_task);
    }

    public void edit(String text) {
        todoList.findBy(exactText(text)).doubleClick();
        todoList.findBy(cssClass("editing")).find(".edit").setValue("New changed task").pressEnter();
    }

    public void complete(String text) {
        todoList.findBy(exactText(text)).find(".toggle").click();
    }

    public void delete(String text) {
        todoList.findBy(exactText(text)).hover().find(".destroy").click();
    }

//    private void assertActiveTasks(String... texts) {
//       // todoList.filterBy(cssClass("ember-view")).shouldHave(exactTexts(texts));
//        //todoList.find(cssClass("a.ember-view")).shouldBe(CollectionCondition.exactTexts("New changed task"));
//        todoList.filterBy(cssClass("a.ember-view")).shouldBe(CollectionCondition.exactTexts("New changed task"));
//    }

    public void assertCompletedTasks(String... texts) {
        todoList.filterBy(cssClass("completed")).shouldHave(exactTexts(texts));
    }

    public void cancelEdit(String text) {
        todoList.findBy(exactText(text)).doubleClick();
        todoList.findBy(cssClass("editing")).find(".edit").setValue("Canceled edition").pressEscape();
    }

    public void clearCompleted() {
        $("#clear-completed").click();
    }
}
