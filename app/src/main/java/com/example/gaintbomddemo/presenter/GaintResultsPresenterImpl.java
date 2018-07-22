package com.example.gaintbomddemo.presenter;

import com.example.gaintbomddemo.Contract;
import com.example.gaintbomddemo.model.GaintBombResponse;
import com.example.gaintbomddemo.network.GaintBombAPIWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GaintResultsPresenterImpl implements Contract.GaintPresenter {

    private final Contract.GaintView gaintView;

    public GaintResultsPresenterImpl(Contract.GaintView gaintView) {
        this.gaintView = gaintView;
        gaintView.setPresenter(this);
    }

    @Override
    public void getGaintResults(String searchQuery, int page, boolean showProgress) {
        if (showProgress)
            gaintView.showProgress();
        GaintBombAPIWrapper.getInstance().getGaintResults(searchQuery, page, new Callback<GaintBombResponse>() {
            @Override
            public void onResponse(Call<GaintBombResponse> call, Response<GaintBombResponse> response) {
                if (response.isSuccessful()) {
                    gaintView.setData(response.body());
                }
                gaintView.hideProgress();
            }

            @Override
            public void onFailure(Call<GaintBombResponse> call, Throwable t) {
                gaintView.hideProgress();
            }
        });
    }

    @Override
    public void start() {

    }
}
