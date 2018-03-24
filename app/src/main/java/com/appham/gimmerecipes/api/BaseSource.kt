package com.appham.gimmerecipes.api

import com.appham.gimmerecipes.MvpContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


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