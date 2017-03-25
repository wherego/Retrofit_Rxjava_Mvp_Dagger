package com.djw.retrofit_rxjava_mvp_dagger.ui.zhihu.presenter;

import com.djw.retrofit_rxjava_mvp_dagger.base.ApiException;
import com.djw.retrofit_rxjava_mvp_dagger.base.CommonSubscriber;
import com.djw.retrofit_rxjava_mvp_dagger.base.RxPresenter;
import com.djw.retrofit_rxjava_mvp_dagger.util.RxUtil;
import com.djw.retrofit_rxjava_mvp_dagger.data.zhihu.DaypaperInfoData;
import com.djw.retrofit_rxjava_mvp_dagger.data.zhihu.DaypaperOtherData;
import com.djw.retrofit_rxjava_mvp_dagger.http.RetrofitHelper;
import com.djw.retrofit_rxjava_mvp_dagger.ui.zhihu.contracts.PaperInfoContracts;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by JasonDong on 2017/3/24.
 */

public class PaperInfoPresenter extends RxPresenter<PaperInfoContracts.View> implements PaperInfoContracts.Presenter {

    private final RetrofitHelper helper;

    @Inject
    PaperInfoPresenter(RetrofitHelper helper) {
        this.helper = helper;
    }

    @Override
    public void getContent(String id) {
        Subscription subscribe = helper.getDaypaperInfoList(id)
                .compose(RxUtil.<DaypaperInfoData>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<DaypaperInfoData>(mView) {
                    @Override
                    public void onNext(DaypaperInfoData daypaperInfoData) {
                        if (daypaperInfoData.getBody().length() < 20)
                            onError(new ApiException("作者已删除"));
                        else mView.showContent(daypaperInfoData);
                    }
                });
        addSubscrebe(subscribe);
    }

    @Override
    public void getContentOther(String id) {
        Subscription subscribe = helper.getDaypaperOther(id)
                .compose(RxUtil.<DaypaperOtherData>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<DaypaperOtherData>(mView) {
                    @Override
                    public void onNext(DaypaperOtherData daypaperOtherData) {
                        mView.showContentOther(daypaperOtherData);
                    }
                });
        addSubscrebe(subscribe);
    }
}
