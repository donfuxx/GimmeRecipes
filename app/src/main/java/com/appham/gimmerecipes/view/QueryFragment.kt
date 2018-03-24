package com.appham.gimmerecipes.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
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
        val TAG = "query-fragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parentView = parentFragment as MvpContract.View
        presenter = parentView.activePresenter()
        return inflater.inflate(R.layout.fragment_query, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val editQuery: EditText? = view.findViewById(R.id.editQuery)

        // on click on keyboard search button run the query
        editQuery?.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                refreshRecipes(RecipesList())
                query(v.text.toString())

                // Hide soft keyboard
                activity?.currentFocus?.let {
                    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE)
                            as InputMethodManager
                    imm.hideSoftInputFromWindow(it.windowToken, 0)
                }


                return@OnEditorActionListener true
            }
            false
        })

        //TODO: make Android Lint happy about not having overriden performClick()
        //Handle click on microphone icon and record voice
        editQuery?.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= v.right - editQuery.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {

                    // record voice
                    recordVoice()
                    return@OnTouchListener true
                }
            }
            false
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
        presenter.callWit(q)
    }

    override fun recordVoice() {
        activity?.let {
            (it as Talkable).recordVoice()
        }
    }
}
