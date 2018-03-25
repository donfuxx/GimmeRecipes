package com.appham.gimmerecipes.model.wit

import com.appham.gimmerecipes.model.ApiFactory
import com.appham.gimmerecipes.model.BaseSource
import com.appham.gimmerecipes.presenter.MvpContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * The source of the wit data retrieved from the api
 * @author thomas
 */
class WitSource : BaseSource() {

    override fun subscribeWit(q: String, presenter: MvpContract.Presenter): Disposable? {
        val recipesApi = ApiFactory.createWitApi()

        // for Wit the query should neither be too short nor too long
        val validQ = if(q.isEmpty()) {"_"} else q.substring(0, minOf(255, q.length))

        return recipesApi.getEntities(validQ).subscribeOn(Schedulers.io()) //do work in background thread
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread()) //do emissions on main thread
                .subscribe({ presenter.onNext(it) }, { presenter.onError(it) })
    }


}