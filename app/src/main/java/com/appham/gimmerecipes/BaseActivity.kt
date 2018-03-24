package com.appham.gimmerecipes

import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.appham.gimmerecipes.model.Recipe
import com.appham.gimmerecipes.model.RecipesList

abstract class BaseActivity : AppCompatActivity(), MvpContract.View {

    override fun refreshRecipes(recipesList: RecipesList) {
        TODO("not implemented")
    }

    override fun showLoadingBar(show: Boolean) {
        TODO("not implemented")
    }

    override fun activePresenter(): MvpContract.Presenter {
        return RecipesPresenter(this)
    }

    override fun updateRecipeDetails(recipe: Recipe) {
        TODO("not implemented")
    }

    override fun showToast(msg: String, length: Int) {
        Toast.makeText(this, msg, length).show()
    }

}
