package com.appham.gimmerecipes

import com.appham.gimmerecipes.model.recipes.RecipesSource
import com.appham.gimmerecipes.model.wit.Entities
import com.appham.gimmerecipes.model.wit.Intent
import com.appham.gimmerecipes.model.wit.WitResponse
import com.appham.gimmerecipes.presenter.MvpContract
import com.appham.gimmerecipes.presenter.RecipesPresenter
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


/**
 * Local RecipesPresenter unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RecipesPresenterUnitTest {

    val mView: MvpContract.View = mock()
    val mPresenter: RecipesPresenter = spy(RecipesPresenter(mView))
    val mRecipesSource: RecipesSource = mock()

    var immediate: Scheduler = object : Scheduler() {
        override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
            // this prevents StackOverflowErrors when scheduling with a delay
            return super.scheduleDirect(run, 0, unit)
        }

        override fun createWorker(): Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
        }
    }

    /**
     * Set schedulers to the test scheduler
     */
    @Before
    fun setup() {
        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }

    @Test
    fun testOnNextWitWithEmptyResponseDoesNotCallRecipesWithQuery() {

        mPresenter.onNext(WitResponse())

        verify(mPresenter, never()).callRecipes(any())

    }

    @Test
    fun testOnNextWitWithValidResponseDoesCallRecipes() {

        // a valid "thai" wit response
        val witResponse = WitResponse()
        witResponse.entities = Entities()
        val testIntent = Intent()
        testIntent.value = "thai"
        witResponse.entities?.intent = mutableListOf(testIntent)

        mPresenter.mRecipesSource = mRecipesSource

        mPresenter.onNext(witResponse)

        verify(mPresenter).callRecipes("thai")

    }

    @Test
    fun testOnNextWitWithMultiResponseDoesCallRecipes() {

        // a valid "thai,curry" wit response
        val witResponse = WitResponse()
        witResponse.entities = Entities()
        val testIntent = Intent()
        testIntent.value = "thai"
        val testIntent2 = Intent()
        testIntent2.value = "curry"
        witResponse.entities?.intent = mutableListOf(testIntent, testIntent2)

        mPresenter.mRecipesSource = mRecipesSource

        mPresenter.onNext(witResponse)

        verify(mPresenter).callRecipes("thai,curry")

    }

    //TODO: add more tests, maybe implement dependency injection framework like dagger2
}
