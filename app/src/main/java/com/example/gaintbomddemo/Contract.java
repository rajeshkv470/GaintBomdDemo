package com.example.gaintbomddemo;

public interface Contract {
    interface GaintView<T extends GaintPresenter> {
        void setData(Object data);

        void setPresenter(T presenter);

        void showProgress();

        void hideProgress();
    }

    interface GaintPresenter {
        void getGaintResults(String searchQuery, int page, boolean showProgress);

        void start();
    }
}
