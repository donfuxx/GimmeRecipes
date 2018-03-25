package com.appham.gimmerecipes.model

import com.appham.gimmerecipes.presenter.MvpContract
import io.reactivex.disposables.Disposable


/**
 * @author thomas
 */
abstract class BaseSource : MvpContract.Model {

    override fun subscribeRecipes(presenter: MvpContract.Presenter): Disposable? {
        TODO("not implemented")
    }

    override fun subscribeRecipes(q: String, presenter: MvpContract.Presenter): Disposable? {
        TODO("not implemented")
    }

    override fun subscribeRecipe(id: String, presenter: MvpContract.Presenter): Disposable? {
        TODO("not implemented")
    }

    override fun subscribeWit(q: String, presenter: MvpContract.Presenter): Disposable? {
        TODO("not implemented")
    }
}