package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TaskScreenTest {

    private lateinit var taskScreenRobot: TaskScreenRobot

    @Before
    fun setup() {
        taskScreenRobot = TaskScreenRobot()
        taskScreenRobot.setup()
    }

    @After
    fun cleanup() {
        taskScreenRobot.cleanup()
    }

    @Test
    fun whenUserHasSingleTask_thenSingleTaskIsDisplayed() = with(taskScreenRobot) {
        createSingleTaskTestData()
        launchActivity()
        checkTasksScreenUiElements()
        checkSingleTaskIsDisplayed()
    }

    @Test
    fun whenUserHasMultipleTasks_thenMultipleTasksAreDisplayed() = with (taskScreenRobot){
        createMultipleTasksTestData()
        launchActivity()
        checkTasksScreenUiElements()
        checkMultipleTasksAreDisplayed()
    }

    @Test
    fun whenUserCreatesANewTask_thenNewTaskIsDisplayedOnTasksScreen() = with (taskScreenRobot) {
        launchActivity()
        checkEmptyTasksScreen()
        tapOnAddTaskFabButton()
        addNewTask()
        checkTasksScreenUiElements()
        checkSingleTaskIsDisplayed()
    }

    @Test
    fun whenUserUpdatesAnExistingTask_thenTaskDisplaysUpdatedData() = with (taskScreenRobot) {
        createSingleTaskTestData()
        launchActivity()
        checkTasksScreenUiElements()
        tapOnTask()
        tapOnEditFabButton()
        editTaskData()
        checkTaskTitleHasBeenUpdated()
    }

    @Test
    fun whenUserDeletesTask_thenUserIsReturnedToMainScreenAndTaskIsDeleted() = with (taskScreenRobot)  {
        createSingleTaskTestData()
        launchActivity()
        checkTasksScreenUiElements()
        tapOnTask()
        tapOnDeleteTaskButton()
        checkEmptyTasksScreen()
    }
}
