package ua.net.itlabs;


import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.exactTexts;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.Test;


public class TodoMVCTest {

    @Test
    public void testCreateTask () {

        ChromeDriverManager.getInstance().setup(); //instal google drive
        Configuration.browser = "chrome";

        open("http://todomvc.com/examples/emberjs/"); //open tested site

        //$("#new-todo").shouldBe(visible).setValue("New task").pressEnter(); //set the timeout until all elements will be visible
        //Configuration.timeout = 6000; there is ability to change the default timeout


        //add();// method of the adding elements to the list - hardocoded parameters - bad idea
        add("New task");
        add("New task 1");
        add("New task 2");

        //$("#todo-list li").shouldHave(size(3)); //checking the amound of the elements on the page

        $$("#todo-list li").shouldHave(exactTexts("New Task", "New Task 1", "New Task 2")); //checking the name of the elements

        //$$("todo-list>li").filterBy(exactText("New Task 2")).find(".toogle").click(); //look for special element, check in the toogle
        $$("todo-list>li").filterBy(exactText("New Task 2")).find(cssClass(".toggle")).click();

        $$("todo-list>li").filterBy(cssClass("completed")).shouldHave(exactTexts("New Task 2")); //filter only completed ones and check their texts

        $$("todo-list>li").excludeWith(cssClass("completed")).shouldHave(exactTexts("New Task", "New Task 1")); //filter only not completed ones, check their texts
    }

    private void add(String text) {
        $("#new-todo").setValue(text).pressEnter();
    }


//    private void add() {
//        $("#new-todo").setValue("New task").pressEnter(); //look for element on the page
//        $("#new-todo").setValue("New task 1").pressEnter();
//        $("#new-todo").setValue("New task 2").pressEnter();
//    }

    //private final SelenideElement newTodo = $("#new-todo"); //test-method
    //SelenideElement newTodo = $("#new-todo"); // variable for correct name and using
}
