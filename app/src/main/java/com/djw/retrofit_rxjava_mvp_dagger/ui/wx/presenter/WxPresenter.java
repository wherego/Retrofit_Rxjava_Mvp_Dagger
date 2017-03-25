package com.djw.retrofit_rxjava_mvp_dagger.ui.wx.presenter;

import com.djw.retrofit_rxjava_mvp_dagger.base.CommonSubscriber;
import com.djw.retrofit_rxjava_mvp_dagger.base.RxPresenter;
import com.djw.retrofit_rxjava_mvp_dagger.data.WXHttpResponse;
import com.djw.retrofit_rxjava_mvp_dagger.data.wx.WxData;
import com.djw.retrofit_rxjava_mvp_dagger.http.RetrofitHelper;
import com.djw.retrofit_rxjava_mvp_dagger.ui.wx.contracts.WxContracts;
import com.djw.retrofit_rxjava_mvp_dagger.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by JasonDong on 2017/3/25.
 */

public class WxPresenter extends RxPresenter<WxContracts.View> implements WxContracts.Presenter {

    private final RetrofitHelper helper;

    @Inject
    public WxPresenter(RetrofitHelper helper) {
        this.helper = helper;
    }

    @Override
    public void getListContent(String num, String page) {
        Subscription subscribe = helper.getWxData(num, page)
//                .compose(RxUtil.<WXHttpResponse<List<WxData>>>rxSchedulerHelper())
                .compose(RxUtil.<WxData>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<WxData>(mView) {
                    @Override
                    public void onNext(WxData list) {
                        mView.showListContent(list);
                    }
                });
        addSubscrebe(subscribe);
    }

    @Override
    public void getSearchData(String num, String page, String word) {
        Subscription subscribe = helper.getSearchWxData(num, page, word)
//                .compose(RxUtil.<WXHttpResponse<List<WxData>>>rxSchedulerHelper())
                .compose(RxUtil.<WxData>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<WxData>(mView) {
                    @Override
                    public void onNext(WxData list) {
                        mView.showSearchData(list);
                    }
                });
        addSubscrebe(subscribe);
    }
}
