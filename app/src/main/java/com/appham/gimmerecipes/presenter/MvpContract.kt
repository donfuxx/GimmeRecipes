package com.appham.gimmerecipes.presenter

import com.appham.gimmerecipes.model.recipes.Recipe
import com.appham.gimmerecipes.model.recipes.RecipeResponse
import com.appham.gimmerecipes.model.recipes.RecipesList
import com.appham.gimmerecipes.model.wit.WitResponse

/**
 * The interface that defines the Model View Presenter pattern
 * @author thomas
 */
interface MvpContract {

    interface Model { //TODO: Maybe split into RecipesModel, WitModel etc. at some point
        fun subscribeRecipes(presenter: Presenter)
        fun subscribeRecipes(q: String, presenter: Presenter)
        fun subscribeRecipe(id: String, presenter: Presenter)
        fun subscribeWit(q: String, presenter: Presenter)
    }

    interface View { //TODO: Maybe split into ListView, DetailsView etc. at some point
        fun refreshRecipes(recipesList: RecipesList)
        fun showToast(msg:String, length:Int)
        fun showLoadingBar(show: Boolean)
        fun activePresenter(): Presenter
        fun updateRecipeDetails(recipe: Recipe)
    }

    interface Presenter {
        fun callRecipes()
        fun callRecipes(q:String)
        fun callRecipe(id: String)
        fun callWit(q:String)
        fun onNext(recipesList: RecipesList)
        fun onNext(recipe: RecipeResponse)
        fun onError(throwable: Throwable)
        fun onNext(wit: WitResponse)
    }

}