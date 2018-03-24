package com.appham.gimmerecipes.presenter

import android.support.annotation.UiThread
import android.util.Log
import android.widget.Toast
import com.appham.gimmerecipes.model.recipes.RecipeResponse
import com.appham.gimmerecipes.model.recipes.RecipesList
import com.appham.gimmerecipes.model.recipes.RecipesSource
import com.appham.gimmerecipes.model.wit.WitResponse
import com.appham.gimmerecipes.model.wit.WitSource
import com.appham.gimmerecipes.view.RecipesListFragment


/**
 * @author thomas
 */
class RecipesPresenter(val mView: MvpContract.View) : MvpContract.Presenter {

    var mRecipesSource: MvpContract.Model = RecipesSource()
    var mWitSource: MvpContract.Model = WitSource()

    override fun callRecipes() {
        mView.showLoadingBar(true)
        mRecipesSource.subscribeRecipes(this)
    }

    override fun callRecipes(q:String) {
        mView.showLoadingBar(true)
        mRecipesSource.subscribeRecipes(q, this)
    }

    override fun callRecipe(id:String) {
        mView.showLoadingBar(true)
        mRecipesSource.subscribeRecipe(id, this)
    }

    override fun callWit(q:String) {
        mView.showLoadingBar(true)
        mWitSource.subscribeWit(q, this)
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

        Log.i(RecipesListFragment.TAG, "${recipesList.recipes?.size} recipes loaded")
    }

    /**
     * Call recipes API by query after Wit API call finished with success.
     * @param recipesList
     */
    override fun onNext(wit: WitResponse) {
        mView.showLoadingBar(false)

        // create comma separated String list
        val sb = StringBuilder()
        val intents = wit.entities?.intent
        intents?.let {
            for (i in it) {
                sb.append(i.value).append(",")
            }
        }
        val q = sb.replace(Regex(",$"), "") //remove last comma at end

        mView.showToast("'$q'", Toast.LENGTH_LONG)

        when (q) {
            "recipes_get" -> callRecipes("")
            else -> {
                callRecipes(q)
            }
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

}