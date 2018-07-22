package com.example.gaintbomddemo.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gaintbomddemo.Contract;
import com.example.gaintbomddemo.utils.PaginationScrollListener;
import com.example.gaintbomddemo.R;
import com.example.gaintbomddemo.model.GaintBombResponse;
import com.example.gaintbomddemo.presenter.GaintResultsPresenterImpl;

public class GaintResultsActivity extends AppCompatActivity implements Contract.GaintView<GaintResultsPresenterImpl> {

    private GaintResultsPresenterImpl presenter;
    private GaintResultsAdapter gaintResultsAdapter;
    private RecyclerView recyclerView;

    private String searchString = "Hello";

    private boolean isLoading = false;
    private boolean isLastPage = false;

    private static final int PAGE_START = 1;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GaintResultsPresenterImpl(this);
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
        presenter.getGaintResults(searchString, currentPage, true);
    }

    private void initViews() {
        final EditText editSearch = findViewById(R.id.edit_search);
        findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                searchString = editSearch.getText().toString();
                currentPage = PAGE_START;
                presenter.getGaintResults(searchString, currentPage, true);
            }
        });
        recyclerView = findViewById(R.id.rv_gaint_list);
        gaintResultsAdapter = new GaintResultsAdapter(this);
        recyclerView.setAdapter(gaintResultsAdapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                presenter.getGaintResults(searchString, currentPage, false);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Override
    public void setData(Object data) {
        if (data instanceof GaintBombResponse) {
            GaintBombResponse bombResponse = (GaintBombResponse) data;
            if (bombResponse.numberOfTotalResults != 0) {
                TOTAL_PAGES = Math.round(bombResponse.numberOfTotalResults / bombResponse.limit);
                if (currentPage == PAGE_START) {
                    gaintResultsAdapter.clearAll();
                    gaintResultsAdapter.setList(bombResponse.results);
                    if (currentPage <= TOTAL_PAGES) gaintResultsAdapter.addLoadingFooter();
                    else isLastPage = true;
                } else {
                    gaintResultsAdapter.removeLoadingFooter();
                    isLoading = false;
                    gaintResultsAdapter.setList(bombResponse.results);
                    if (currentPage != TOTAL_PAGES) gaintResultsAdapter.addLoadingFooter();
                    else isLastPage = true;
                }
            } else {
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void setPresenter(GaintResultsPresenterImpl presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Loading..");
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
