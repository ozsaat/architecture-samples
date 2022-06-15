package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.annotation.IdRes
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.utils.EspressoTestMatchers.withDrawable
import com.example.android.architecture.blueprints.todoapp.utils.EspressoTestMatchers.withRecyclerView
import com.example.android.architecture.blueprints.todoapp.utils.FakeAndroidTestRepository
import com.example.android.architecture.blueprints.todoapp.utils.RecyclerViewItemCountAssertion
import com.example.android.architecture.blueprints.todoapp.utils.WaitForViewUtils.waitForItemAndTap
import com.example.android.architecture.blueprints.todoapp.utils.WaitForViewUtils.waitForItemToBeDisplayed
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.AllOf.allOf

class TaskScreenRobot {

    private lateinit var taskActivityScenario: ActivityScenario<TasksActivity>
    private lateinit var repository: TasksRepository

    fun setup() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    fun cleanup() = runBlocking{
        ServiceLocator.resetRepository()
    }

    fun launchActivity() {
        taskActivityScenario = ActivityScenario.launch(TasksActivity::class.java)
    }

    fun createSingleTaskTestData() = runBlocking {
        repository.saveTask(Task("title 123", "description 456", false))
    }

    fun createMultipleTasksTestData() {
        createTestData(
            mapOf(
                "TITLE1" to "DESCRIPTION",
                "TITLE2" to "DESCRIPTION",
                "TITLE3" to "DESCRIPTION",
                "TITLE4" to "DESCRIPTION",
                "TITLE5" to "DESCRIPTION",
                "TITLE6" to "DESCRIPTION",
                "TITLE7" to "DESCRIPTION",
            )
        )
    }

    fun tapOnAddTaskFabButton() {
        onView(withId(R.id.add_task_fab)).perform(click())
    }

    fun tapOnTask() {
        onView(withText("title 123"))
            .perform(click())
    }

    fun tapOnEditFabButton() = waitForItemAndTap(withId(R.id.edit_task_fab))

    fun tapOnDeleteTaskButton() = waitForItemAndTap(withId(R.id.menu_delete))

    fun addNewTask() {
        waitForItemToBeDisplayed(withText("New Task"))
        typeTextIntoEditText(R.id.add_task_title_edit_text, "title 123")
        typeTextIntoEditText(R.id.add_task_description_edit_text, "description 456")
        onView(withId(R.id.save_task_fab)).perform(click())
    }

    fun editTaskData() {
        waitForItemToBeDisplayed(withText("Edit Task"))
        replaceTextInEditText(R.id.add_task_title_edit_text, "edit title")
        replaceTextInEditText(R.id.add_task_description_edit_text, "edit description")
        onView(withId(R.id.save_task_fab)).perform(click())
    }

    //this check also provides the delay needed for the screen to load
    fun checkTasksScreenUiElements() {
        waitForItemToBeDisplayed(withText("All Tasks"))
        onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText("Todo"))))
        onView(withId(R.id.filtering_text))
            .check(matches(allOf(withText("All Tasks"), isDisplayed())))
    }

    fun checkSingleTaskIsDisplayed() {
        checkRecyclerViewItem(0, "title 123")
        checkExpectedNumberOfTasks(1)
    }

    fun checkTaskTitleHasBeenUpdated() {
        checkRecyclerViewItem(0, "edit title")
        checkExpectedNumberOfTasks(1)
    }

    //For better stability on smaller screen sizes a scroll action can be added
    fun checkMultipleTasksAreDisplayed() {
        //check first task
        checkRecyclerViewItem(0, "TITLE1")
        //check last task
        checkRecyclerViewItem(6, "TITLE7")
        checkExpectedNumberOfTasks(7)
    }

    fun checkEmptyTasksScreen() {
        waitForItemToBeDisplayed(withText(R.string.no_tasks_all))
        onView(withId(R.id.no_tasks_icon)).check(matches(withDrawable(R.drawable.logo_no_fill)))
    }

    private fun checkRecyclerViewItem(position: Int, itemName: String) {
        onView(withRecyclerView(R.id.tasks_list)
        !!.atPositionOnView(position, R.id.title_text))
            .check(matches(allOf(withText(itemName), isCompletelyDisplayed())))
    }

    private fun checkExpectedNumberOfTasks(expectedNumberOfTasks: Int) {
        onView(withId(R.id.tasks_list))
            .check(RecyclerViewItemCountAssertion(expectedNumberOfTasks))
    }

    private fun createTestData(tasks: Map<String, String>) = runBlocking {
        tasks.forEach {
            repository.saveTask(Task(it.key, it.value, false))
        }
    }

    private fun typeTextIntoEditText(@IdRes viewId: Int, text: String) {
        onView(withId(viewId))
            .perform(typeText(text))
    }

    private fun replaceTextInEditText(@IdRes viewId: Int, text: String) {
        onView(withId(viewId))
            .perform(replaceText(text))
    }
}
