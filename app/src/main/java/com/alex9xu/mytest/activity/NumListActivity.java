package com.alex9xu.mytest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.alex9xu.mytest.R;
import com.alex9xu.mytest.adapter.NumShowAdapter;
import com.alex9xu.mytest.model.NumCounter;
import com.alex9xu.mytest.utils.NumUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex9Xu@hotmail.com on 2016/10/26
 */

public class NumListActivity extends AppCompatActivity {
    private final int LIST_SIZE = 100;
    private final int NUM_LENGTH = 20;

    // RecyclerView is newer and more advanced than ListView
    private RecyclerView mRecyclerViewNumShow;
    private NumShowAdapter mNumShowAdapter;
    private List<NumCounter> nNumStrList = new ArrayList<>();

    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_list);

        initView();
        initData();
    }

    private void initView() {
        // Do sth about view if necessary
        mRecyclerViewNumShow = (RecyclerView) findViewById(R.id.num_list_recycler_view);
        final LinearLayoutManager layNumShow = new LinearLayoutManager(this);
        layNumShow.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewNumShow.setLayoutManager(layNumShow);
        mNumShowAdapter = new NumShowAdapter(this, nNumStrList);
        mNumShowAdapter.setOnItemClickListener(new NumShowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(0 == position) {
                    finish();
                }
            }
        });
        mRecyclerViewNumShow.setAdapter(mNumShowAdapter);

        mProgressBar = (ProgressBar) findViewById(R.id.num_list_progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void initData() {
        // Do sth about data if necessary
        // It may take long time, so do in background thread
        new Thread(){
            @Override
            public void run(){
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                for(int i=0; i<LIST_SIZE; i++) {
                    String numStr = NumUtils.getRandomNums(NUM_LENGTH);
                    NumCounter numCounter = new NumCounter();
                    numCounter.setNumber(numStr);
                    numCounter.setTimes(NumUtils.countNum(numStr));
                    nNumStrList.add(numCounter);
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        mNumShowAdapter.notifyDataSetChanged();
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
            }
        }.start();

    }



}
