package com.appham.gimmerecipes.presenter

import android.support.annotation.UiThread
import android.widget.Toast
import com.appham.gimmerecipes.model.recipes.RecipeResponse
import com.appham.gimmerecipes.model.recipes.RecipesList
import com.appham.gimmerecipes.model.recipes.RecipesSource
import com.appham.gimmerecipes.model.wit.WitResponse
import com.appham.gimmerecipes.model.wit.WitSource
import io.reactivex.disposables.Disposable


/**
 * @author thomas
 */
class RecipesPresenter(val mView: MvpContract.View) : MvpContract.Presenter {

    val RECIPES_GET = "recipes_get"
    var mRecipesSource: MvpContract.Model = RecipesSource()
    private var mWitSource: MvpContract.Model = WitSource()
    private var mRecipesDisposable: Disposable? = null
    private var mWitDisposable: Disposable? = null

    override fun callRecipes() {
        mView.showLoadingBar(true)
        mRecipesDisposable = mRecipesSource.subscribeRecipes(this)
    }

    override fun callRecipes(q:String) {
        mView.showLoadingBar(true)
        mRecipesDisposable = mRecipesSource.subscribeRecipes(q, this)
    }

    override fun callRecipe(id:String) {
        mView.showLoadingBar(true)
        mRecipesDisposable = mRecipesSource.subscribeRecipe(id, this)
    }

    override fun callWit(q:String) {
        mView.showLoadingBar(true)
        mWitDisposable = mWitSource.subscribeWit(q, this)
    }

    /**
     * Update the view with provided recipe when API call finished with success.
     * @param recipe
     */
    @UiThread
    override fun onNext(recipe: RecipeResponse) {
        mView.showLoadingBar(false)
        recipe.recipe?.let {
            mView.updateRecipeDetails(it)
        }
    }

    /**
     * Update the adapter with provided recipes list when API call finished with success.
     * @param recipesList
     */
    @UiThread
    override fun onNext(recipesList: RecipesList) {
        mView.showLoadingBar(false)

        mView.refreshRecipes(recipesList)
    }

    /**
     * Call recipes API by query after Wit API call finished with success.
     * @param wit
     */
    @UiThread
    override fun onNext(wit: WitResponse) {
        mView.showLoadingBar(false)

        // create comma separated String list
        val sb = StringBuilder()
        val intents = wit.entities?.intent
        intents?.let {
            for (i in it) {
                // recipes_get intent only matters if there are no specific keywords
                if (it.size > 1 && i.value == RECIPES_GET) {
                    continue
                }
                sb.append(i.value).append(",")
            }
        }
        val q = sb.replace(Regex(",$"), "") //remove last comma at end

        if (q.isNotEmpty()) {
            mView.showToast("'$q'", Toast.LENGTH_LONG)
        }

        // show all recipes if 'recipes_get' detected or q is blank
        if (q == RECIPES_GET || q.isBlank()) {
            callRecipes()
        } else { // otherwise call recipes with keywords as query params
            callRecipes(q)
        }
    }

    /**
     * Show a toast msg indicating the problem when API call failed.
     * @param throwable
     */
    @UiThread
    override fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        mView.showToast(throwable.localizedMessage, Toast.LENGTH_LONG)
        mView.showLoadingBar(false)
        //TODO: show more user-friendly messages than just plain exception msg instead
    }

    override fun cancelRequest() {
        mRecipesDisposable?.dispose()
        mWitDisposable?.dispose()
    }

}