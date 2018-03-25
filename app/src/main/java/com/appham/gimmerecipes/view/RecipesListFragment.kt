package com.appham.gimmerecipes.view


import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.appham.gimmerecipes.R
import com.appham.gimmerecipes.model.recipes.Recipe
import com.appham.gimmerecipes.model.recipes.RecipesList
import com.appham.gimmerecipes.presenter.MvpContract
import com.appham.gimmerecipes.presenter.RecipesPresenter

/**
 * @author thomas
 */

class RecipesListFragment : Fragment(), MvpContract.View, Queryable {
    private val recipesAdapter = RecipesAdapter()

    private lateinit var progressBar: ProgressBar

    val presenter: MvpContract.Presenter = RecipesPresenter(this)

    companion object {
        const val TAG = "recipes-list-fragment"
        const val RECIPES_LIST = "recipes-list"
    }

    //region lifecycle methods
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        addQueryFragment()

        // get recycler-list of recipes results with fixed size
        val recipesListView = view.findViewById<RecyclerView>(R.id.listRecipes)
        recipesListView.setHasFixedSize(true)

        // use a linear layout manager
        val recipesLayoutManager = LinearLayoutManager(activity)
        recipesListView.layoutManager = recipesLayoutManager

        recipesListView.adapter = recipesAdapter

        // delete recipes item from list by swipe right
        val simpleItemTouchCallback = createSwipeCallback()
        ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(recipesListView)

        progressBar = view.findViewById(R.id.progressBarList)

        // restore recipes list if there is a saved instance state
        savedInstanceState?.let {
            refreshRecipes(it.getParcelable<Parcelable>(RECIPES_LIST) as RecipesList)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.cancelRequest()
        outState.putParcelable(RECIPES_LIST, recipesAdapter.recipesList)
        super.onSaveInstanceState(outState)
    }
    //endregion

    //region mvp view interface methods
    override fun refreshRecipes(recipesList: RecipesList) {
        recipesAdapter.recipesList = recipesList
        recipesAdapter.notifyDataSetChanged()
    }

    override fun showToast(msg: String, length: Int) {
        activity?.let { //Kotlin-style null-safe call
            (it as MvpContract.View).showToast(msg, length)
        }
    }

    override fun showLoadingBar(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun activePresenter(): MvpContract.Presenter {
        return presenter
    }

    override fun updateRecipeDetails(recipe: Recipe) {
        TODO("not implemented")
    }
    //endregion

    override fun query(q: String) {
        presenter.callRecipes(q)
    }

    private fun addQueryFragment() {
        var queryFragment: Fragment? = childFragmentManager.findFragmentByTag(QueryFragment.TAG)

        if (queryFragment == null) {
            queryFragment = QueryFragment()
            childFragmentManager.beginTransaction()
                    .add(R.id.frameQuery, queryFragment, QueryFragment.TAG)
                    .commit()
        }
    }

    /**
     * Create a callback to delete list items on swipe
     */
    private fun createSwipeCallback(): ItemTouchHelper.SimpleCallback {
        return object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                //TODO: also delete from backend db
                showToast(getString(R.string.delete_not_yet), Toast.LENGTH_LONG)

                val pos = viewHolder.adapterPosition
                recipesAdapter.recipesList.recipes?.removeAt(pos)
                recipesAdapter.notifyItemRemoved(pos)
            }
        }
    }

}
