package com.example.mazhengyang.news.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mazhengyang.news.Adapter.NewsAdapter;
import com.example.mazhengyang.news.Animation.ScaleInAnimation;
import com.example.mazhengyang.news.Bean.NewsBean;
import com.example.mazhengyang.news.NewsApplication;
import com.example.mazhengyang.news.NewsDetailActivity;
import com.example.mazhengyang.news.Present.INewsPresent;
import com.example.mazhengyang.news.Present.NewsPresentImpl;
import com.example.mazhengyang.news.R;
import com.example.mazhengyang.news.View.INewsView;
import com.example.mazhengyang.news.util.Logger;
import com.example.mazhengyang.news.util.NetWorkUtil;
import com.example.mazhengyang.news.util.RecycleViewDivider;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-8-16.
 */

public class ContentFragment extends Fragment implements INewsView {

    private final String TAG = ContentFragment.class.getSimpleName();

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private INewsPresent mNewsPresent;
    private NewsAdapter mNewsAdapter;

    private String title;
    private String type;

    private int page = 0;

    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsPresent = new NewsPresentImpl(this);
        Logger.d(TAG, "onCreate: title=" + title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Logger.d(TAG, "onCreateView: title=" + title);

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_news_content, null);
            ButterKnife.bind(this, rootView);

            Context context = getActivity().getApplicationContext();
            mNewsAdapter = new NewsAdapter(context);
            mNewsAdapter.setOnNewsItemClickListener(mOnNewsItemClickListener);
            mNewsAdapter.openLoadAnimation(new ScaleInAnimation());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            //添加Android自带的分割线
//            mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            mRecyclerView.addItemDecoration(new RecycleViewDivider(
                    context, LinearLayoutManager.VERTICAL, R.drawable.divider));
            DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
//            defaultItemAnimator.setAddDuration(500);
//            defaultItemAnimator.setRemoveDuration(1000);
            mRecyclerView.setItemAnimator(defaultItemAnimator);
            mRecyclerView.setAdapter(mNewsAdapter);

            mRefreshLayout.setRefreshHeader(new MaterialHeader(context));
            mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {

                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    boolean netAvailable = NetWorkUtil.isNetWorkConnected(NewsApplication.getAppContext());
                    Logger.d(TAG, "onRefresh: netAvailable=" + netAvailable);
                    if (!netAvailable) {
                        Toast.makeText(getContext(), getContext().getText(R.string.net_not_connect),
                                Toast.LENGTH_SHORT).show();
                        mRefreshLayout.finishRefresh();
                        return;
                    }
                    page = 0;
                    mNewsAdapter.clear();
                    load();
                }

                @Override
                public void onLoadMore(RefreshLayout refreshLayout) {
                    boolean netAvailable = NetWorkUtil.isNetWorkConnected(NewsApplication.getAppContext());
                    Logger.d(TAG, "onLoadMore: netAvailable=" + netAvailable);
                    if (!netAvailable) {
                        Toast.makeText(getContext(), getContext().getText(R.string.net_not_connect),
                                Toast.LENGTH_SHORT).show();
                        mRefreshLayout.finishLoadMore();
                        return;
                    }
                    load();
                }

            });

        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }

        boolean netAvailable = NetWorkUtil.isNetWorkConnected(NewsApplication.getAppContext());
        if (netAvailable) {
            if (mNewsAdapter.getItemCount() == 0) {
                mRefreshLayout.autoRefresh();
            }
        } else {
            Logger.d(TAG, "network not available!");
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d(TAG, "onResume: title=" + title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d(TAG, "onDestroyView: title=" + title);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(TAG, "onDestroy: title=" + title);
    }

    public void setTitleType(String title, String type) {
        this.title = title;
        this.type = type;
    }

    @Override
    public void addNews(NewsBean newsBean) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        mNewsAdapter.add(newsBean);
        mNewsAdapter.addAdvert(15);
        page++;
    }

    private void load() {
        mNewsPresent.loadData(type, 10, page);
    }

    private void startDetail(View view, int position) {

        if (mNewsAdapter.getItem(position) instanceof NewsBean.Entity) {
            NewsBean.Entity item = (NewsBean.Entity) mNewsAdapter.getItem(position);
            Intent i = new Intent();
            i.setClass(getActivity(), NewsDetailActivity.class);
            i.putExtra("title", item.getTitle());
            i.putExtra("picUrl", item.getPicUrl());
            i.putExtra("detailUrl", item.getUrl());

            View transitionView = view.findViewById(R.id.ivImage);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            transitionView, getString(R.string.transition_news_img));

            ActivityCompat.startActivity(getActivity(), i, options.toBundle());
        }
    }

    private void deleteAdvert(int position) {
        mNewsAdapter.remove(position);
        int count = mNewsAdapter.getItemCount();
        if (count < 10) {
            Logger.d(TAG, "onAdItemTagClick: onLoadMore");
            mRefreshLayout.autoLoadMore();
        }
    }


    private NewsAdapter.OnNewsItemClickListener mOnNewsItemClickListener = new NewsAdapter.OnNewsItemClickListener() {
        @Override
        public void onNewsItemClick(View view, int position) {
            startDetail(view, position);
        }

        @Override
        public void onAdvertDropDownClick(int id, int position) {
            switch (id) {
                case 0:
                    deleteAdvert(position);
                    break;
                default:
                    break;
            }
        }
    };

}
