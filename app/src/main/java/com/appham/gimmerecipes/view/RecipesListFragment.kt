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
import com.appham.gimmerecipes.presenter.MvpContract
import com.appham.gimmerecipes.R
import com.appham.gimmerecipes.presenter.RecipesPresenter
import com.appham.gimmerecipes.model.recipes.Recipe
import com.appham.gimmerecipes.model.recipes.RecipesList

/**
 * @author thomas
 */

class RecipesListFragment : Fragment(), MvpContract.View, Queryable {
    private val recipesAdapter = RecipesAdapter()

    private lateinit var progressBar: ProgressBar

    val presenter: MvpContract.Presenter = RecipesPresenter(this)
    companion object {

        val TAG = "recipes-list-fragment"
        val RECIPES_LIST = "recipes-list"
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    //region lifecycle methods
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
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                //TODO: also delete from backend db
                Toast.makeText(activity,
                        R.string.delete_not_yet, Toast.LENGTH_LONG).show()

                val pos = viewHolder.adapterPosition
                recipesAdapter.recipesList.recipes?.removeAt(pos)
                recipesAdapter.notifyItemRemoved(pos)
            }
        }
        ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(recipesListView)

        progressBar = view.findViewById(R.id.progressBarList)

//        val btnRefresh: FloatingActionButton? = view.findViewById(R.id.btnRefresh)
//        btnRefresh?.setOnClickListener({ _ ->
//            refreshRecipes(RecipesList())
//            presenter.callRecipes()
//        })

        if (savedInstanceState != null) {
            refreshRecipes(savedInstanceState.getParcelable<Parcelable>(RECIPES_LIST) as RecipesList)
//        } else {
//            presenter.callRecipes()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
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
        (activity as MvpContract.View).showToast(msg, length)
    }

    override fun showLoadingBar(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun activePresenter(): MvpContract.Presenter {
        return presenter
    }

    override fun updateRecipeDetails(recipe: Recipe) {
    }
    //endregion

    override fun query(q: String) {
        presenter.callRecipes()
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

}
