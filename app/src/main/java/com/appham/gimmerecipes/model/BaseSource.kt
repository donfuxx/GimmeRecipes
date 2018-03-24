package com.appham.gimmerecipes.model

import com.appham.gimmerecipes.presenter.MvpContract


/**
 * @author thomas
 */
abstract class BaseSource() : MvpContract.Model {

    override fun subscribeRecipes(presenter: MvpContract.Presenter) {
        TODO("not implemented")
    }

    override fun subscribeRecipes(q: String, presenter: MvpContract.Presenter) {
        TODO("not implemented")
    }

    override fun subscribeRecipe(id: String, presenter: MvpContract.Presenter) {
        TODO("not implemented")
    }

    override fun subscribeWit(q: String, presenter: MvpContract.Presenter) {
        TODO("not implemented")
    }
}