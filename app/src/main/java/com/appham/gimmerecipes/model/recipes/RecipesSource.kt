package com.appham.gimmerecipes.model.recipes

import com.appham.gimmerecipes.model.ApiFactory
import com.appham.gimmerecipes.model.BaseSource
import com.appham.gimmerecipes.presenter.MvpContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * @author thomas
 */
class RecipesSource : BaseSource() { //TODO: move to model package?

    override fun subscribeRecipes(presenter: MvpContract.Presenter): Disposable? {
        val recipesApi = ApiFactory.createRecipesApi()
        return recipesApi.recipes.subscribeOn(Schedulers.io()) //do work in background thread
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread()) //do emissions on main thread
                .subscribe({ presenter.onNext(it) }, { presenter.onError(it) })
    }

    override fun subscribeRecipes(q: String, presenter: MvpContract.Presenter): Disposable? {
        val recipesApi = ApiFactory.createRecipesApi()
        return recipesApi.getRecipesByQuery(q).subscribeOn(Schedulers.io()) //do work in background thread
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread()) //do emissions on main thread
                .subscribe({ presenter.onNext(it) }, { presenter.onError(it) })
    }

    override fun subscribeRecipe(id: String, presenter: MvpContract.Presenter): Disposable? {
        val recipesApi = ApiFactory.createRecipesApi()
        return recipesApi.getRecipeById(id).subscribeOn(Schedulers.io()) //do work in background thread
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread()) //do emissions on main thread
                .subscribe({ presenter.onNext(it) }, { presenter.onError(it) })
    }

}