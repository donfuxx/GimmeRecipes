package com.appham.gimmerecipes

import com.appham.gimmerecipes.model.recipes.RecipesSource
import com.appham.gimmerecipes.model.wit.Entities
import com.appham.gimmerecipes.model.wit.Intent
import com.appham.gimmerecipes.model.wit.WitResponse
import com.appham.gimmerecipes.presenter.MvpContract
import com.appham.gimmerecipes.presenter.RecipesPresenter
import com.nhaarman.mockito_kotlin.*
import org.junit.Test

/**
 * Local RecipesPresenter unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RecipesPresenterUnitTest {

    val mView: MvpContract.View = mock()
    val mPresenter: RecipesPresenter = spy(RecipesPresenter(mView))
    val mRecipesSource: RecipesSource = mock()

    @Test
    fun testOnNextWitWithEmptyResponseDoesNotCallRecipes() {

        mPresenter.onNext(WitResponse())

        verify(mPresenter, never()).callRecipes(anyOrNull())

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

    //TODO: add more tests, maybe implement dependency injection framework like dagger2
}
