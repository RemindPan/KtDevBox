package com.cysion.usercenter.presenter

import com.cysion.ktbox.base.BasePresenter
import com.cysion.ktbox.net.BaseResponseRx
import com.cysion.other.addTo
import com.cysion.targetfun._subscribe
import com.cysion.usercenter.net.UserCaller
import com.cysion.usercenter.ui.iview.SquareView

class SquarePresenter : BasePresenter<SquareView>() {

    fun getCarousel() {
        checkViewAttached()
        UserCaller.api.getgetCarousel()
            .compose(BaseResponseRx.validateToMain())
            ._subscribe {
                _onNext {
                    attchedView?.setCarousels(it)
                }
                _onError {
                    error(it)
                }
            }.addTo(compositeDisposable)
    }
}