package com.appham.gimmerecipes.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import com.appham.gimmerecipes.R
import com.appham.gimmerecipes.model.recipes.Recipe
import com.appham.gimmerecipes.model.recipes.RecipesList
import com.appham.gimmerecipes.presenter.MvpContract
import com.appham.gimmerecipes.presenter.RecipesPresenter
import kotlinx.android.synthetic.main.fragment_query.*

class MainActivity : BaseActivity(), MvpContract.View, Talkable {
    val SPEECH_REQUEST_CODE = 1

    private lateinit var presenter: MvpContract.Presenter

    //region lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // attach recipes list fragment if there isn't one already
        var recipesListFragment: Fragment? = supportFragmentManager.findFragmentByTag(RecipesListFragment.TAG)
        if (recipesListFragment == null) {
            recipesListFragment = RecipesListFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.frameRecipes, recipesListFragment, RecipesListFragment.TAG)
                    .commit()
        }

        presenter = RecipesPresenter(recipesListFragment as MvpContract.View)
    }

    override fun onActivityResult(requestCode: Int, resultcode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultcode, intent)
        if (resultcode == Activity.RESULT_OK) {
            if (requestCode == SPEECH_REQUEST_CODE) {
                val speech = intent?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                speech?.let {
                    presenter.cancelRequest()
                    presenter.callWit(it[0])
                    editQuery?.setText(it[0])
                }
            }
        }
    }
    //endregion

    //region MVP view methods
    override fun showLoadingBar(show: Boolean) {
        val recipesListFragment = fragmentManager.findFragmentByTag(RecipesListFragment.TAG) as RecipesListFragment?
        recipesListFragment?.showLoadingBar(show)
    }

    override fun refreshRecipes(recipesList: RecipesList) {
        val recipesListFragment = fragmentManager.findFragmentByTag(RecipesListFragment.TAG) as RecipesListFragment?
        recipesListFragment?.refreshRecipes(recipesList)
    }

    override fun updateRecipeDetails(recipe: Recipe) {
    }

    override fun activePresenter(): MvpContract.Presenter {
        return presenter
    }
    //endregion

    override fun recordVoice() {
        val DIALOG_TEXT = getString(R.string.talk_to_me)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, DIALOG_TEXT)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, SPEECH_REQUEST_CODE)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-UK")

        ActivityCompat.startActivityForResult(this, intent, SPEECH_REQUEST_CODE, null)
    }
}
