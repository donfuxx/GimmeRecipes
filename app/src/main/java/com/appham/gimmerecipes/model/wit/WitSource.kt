package com.appham.gimmerecipes.model.wit

import com.appham.gimmerecipes.presenter.MvpContract
import com.appham.gimmerecipes.model.ApiFactory
import com.appham.gimmerecipes.model.BaseSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * @author thomas
 */
class WitSource() : BaseSource() {

    override fun subscribeWit(q: String, presenter: MvpContract.Presenter) {
        val recipesApi = ApiFactory.createWitApi()

        // for Wit the query should neither be too short nor too long
        val validQ = if(q.isEmpty()) {"_"} else q.substring(0, minOf(255, q.length))

        recipesApi.getEntities(validQ).subscribeOn(Schedulers.io()) //do work in background thread
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread()) //do emissions on main thread
                .subscribe({ presenter.onNext(it) }, { presenter.onError(it) })
    }


}