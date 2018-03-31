package com.appham.gimmerecipes.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import com.appham.gimmerecipes.R
import com.appham.gimmerecipes.model.recipes.Recipe
import com.appham.gimmerecipes.model.recipes.RecipesList
import com.appham.gimmerecipes.presenter.MvpContract


/**
 * @author thomas
 */
class QueryFragment : Fragment(), MvpContract.View, Queryable, Talkable {
    lateinit var parentView: MvpContract.View

    lateinit var presenter: MvpContract.Presenter

    companion object {
        const val TAG = "query-fragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parentView = parentFragment as MvpContract.View
        presenter = parentView.activePresenter()
        return inflater.inflate(R.layout.fragment_query, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val searchView : SearchView? = view.findViewById(R.id.searchQuery)
        searchView?.setIconifiedByDefault(false)
        searchView?.isSubmitButtonEnabled = true

        // on click on keyboard search button run the query
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(q: String): Boolean {
                query(q)
                return false
            }

        })

        super.onViewCreated(view, savedInstanceState)
    }

    //region MVP View methods
    override fun updateRecipeDetails(recipe: Recipe) {
        TODO("not implemented")
    }

    override fun refreshRecipes(recipesList: RecipesList) {
        parentView.refreshRecipes(recipesList)
    }

    override fun showToast(msg: String, length: Int) {
        parentView.showToast(msg, length)
    }

    override fun showLoadingBar(show: Boolean) {
        parentView.showLoadingBar(show)
    }

    override fun activePresenter(): MvpContract.Presenter {
        return presenter
    }
    //endregion

    override fun query(q: String) {
        presenter.cancelRequest()
        refreshRecipes(RecipesList())
        presenter.callWit(q)
        hideKeyboard()
    }

    override fun recordVoice() {
        activity?.let {
            (it as Talkable).recordVoice()
        }
    }

    /**
     * Hide soft keyboard
     */
    private fun hideKeyboard() {
        activity?.currentFocus?.let {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}
