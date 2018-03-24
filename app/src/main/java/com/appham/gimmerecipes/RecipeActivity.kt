package com.appham.gimmerecipes

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.appham.gimmerecipes.model.Recipe
import com.squareup.picasso.Picasso
import android.content.Intent
import android.net.Uri


class RecipeActivity : BaseActivity(), MvpContract.View {

    private lateinit var mPresenter: RecipesPresenter

    private lateinit var mProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        mPresenter = RecipesPresenter(this)

        val bundle = intent.extras

        // set recipes data from the bundle first
        if (bundle != null) {
            val recipe = bundle.get(RecipesAdapter.RECIPE) as Recipe
            initViews(recipe)

            // call api to get the ingredients
            recipe.recipeId?.let {
                mPresenter.callRecipe(it)
            }
        }

    }

    override fun showLoadingBar(show: Boolean) {
        mProgressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun activePresenter(): MvpContract.Presenter {
        return mPresenter
    }

    override fun updateRecipeDetails(recipe: Recipe) {
        val txtDetails: TextView? = findViewById(R.id.txtRecipeDetails)
        recipe.ingredients?.let {

            // show each ingredient in a new line
            val sb = StringBuilder()
            for (s in it) {
                sb.append(s).append("\n")
            }

            txtDetails?.text = sb.toString()
            //TODO: Maybe use SpannableStringBuilder here for nicer formatting


        }
    }

    /**
     * Set view text and image from Recipe object
     * @param recipe
     */
    private fun initViews(recipe: Recipe) {

        updateRecipeDetails(recipe)

        mProgressBar = findViewById(R.id.progressBarDetails)

        val txtTitle: TextView? = findViewById(R.id.txtRecipeTitle)
        txtTitle?.text = recipe.title

        val imgView: ImageView? = findViewById(R.id.imgRecipeDetails)
        imgView?.let {
            Picasso.with(this).load(recipe.imageUrl)
                    .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                    .error(R.mipmap.ic_launcher_round)
                    .into(it)
        }

        val btnSource: Button? = findViewById(R.id.btnRecipeSource)
        btnSource?.let {
            it.setOnClickListener({ //open browser with publisher url on click
                recipe.f2fUrl?.let {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                    //TODO: Maybe show a webview instead
                }
            })
        }
    }
}
