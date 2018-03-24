package com.appham.gimmerecipes.api

import com.appham.gimmerecipes.MvpContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * @author thomas
 */
class RecipesSource() : BaseSource() { //TODO: move to model package?

    override fun subscribeRecipes(presenter: MvpContract.Presenter) {
        val recipesApi = ApiFactory.createRecipesApi()
        recipesApi.recipes.subscribeOn(Schedulers.io()) //do work in background thread
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread()) //do emissions on main thread
                .subscribe({ presenter.onNext(it) }, { presenter.onError(it) })
    }

    override fun subscribeRecipes(q: String, presenter: MvpContract.Presenter) {
        val recipesApi = ApiFactory.createRecipesApi()
        recipesApi.getRecipesByQuery(q).subscribeOn(Schedulers.io()) //do work in background thread
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread()) //do emissions on main thread
                .subscribe({ presenter.onNext(it) }, { presenter.onError(it) })
    }

    override fun subscribeRecipe(id: String, presenter: MvpContract.Presenter) {
        val recipesApi = ApiFactory.createRecipesApi()
        recipesApi.getRecipeById(id).subscribeOn(Schedulers.io()) //do work in background thread
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread()) //do emissions on main thread
                .subscribe({ presenter.onNext(it) }, { presenter.onError(it) })
    }

}