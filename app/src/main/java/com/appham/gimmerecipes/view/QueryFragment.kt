package com.appham.gimmerecipes.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appham.gimmerecipes.model.recipes.RecipesList
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.view.MotionEvent
import android.view.View.OnTouchListener
import com.appham.gimmerecipes.presenter.MvpContract
import com.appham.gimmerecipes.R
import com.appham.gimmerecipes.model.recipes.Recipe


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
        editQuery?.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                refreshRecipes(RecipesList())
//                presenter.callRecipes(v.text.toString())
                presenter.callWit(v.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

        //TODO: make Android Lint happy about not having overriden performClick()
        editQuery?.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= v.getRight() - editQuery.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) {

                    // record voice
                    recordVoice()
                    return@OnTouchListener true
                }
            }
            false
        })

        super.onViewCreated(view, savedInstanceState)
    }

    override fun updateRecipeDetails(recipe: Recipe) {
        TODO("not implemented")
    }

    override fun query(q: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override fun recordVoice() {
        activity?.let {
            (it as Talkable).recordVoice()
        }
    }
}
