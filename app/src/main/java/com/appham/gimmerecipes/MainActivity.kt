package com.appham.gimmerecipes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.appham.gimmerecipes.model.Recipe
import com.appham.gimmerecipes.model.RecipesList

class MainActivity : BaseActivity(), MvpContract.View, Talkable {
    val SPEECH_REQUEST_CODE = 1

    private lateinit var presenter: MvpContract.Presenter
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
//                    presenter.callRecipes(it[0])
                    presenter.callWit(it[0])
                }
            }
        }
    }

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
