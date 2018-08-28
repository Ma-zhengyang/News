package com.example.mazhengyang.news.Adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapDropDown;
import com.bumptech.glide.Glide;
import com.example.mazhengyang.news.Animation.AlphaInAnimation;
import com.example.mazhengyang.news.Animation.BaseAnimation;
import com.example.mazhengyang.news.Bean.AdBean;
import com.example.mazhengyang.news.Bean.NewsBean;
import com.example.mazhengyang.news.R;
import com.example.mazhengyang.news.util.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-8-16.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = NewsAdapter.class.getSimpleName();

    private List<Object> mNewsBeanList = new ArrayList<>();

    private Context mContext;

    private static final int TYPE_NEW = 0;
    private static final int TYPE_AD = 1;

    private boolean mOpenAnimationEnable = true;
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mDuration = 300;
    private int mLastPosition = -1;

    private OnNewsItemClickListener mOnNewsItemClickListener;

    public interface OnNewsItemClickListener {
        void onNewsItemClick(View view, int position);

        void onAdDropDownClick(int id, int position);
    }

    public void setOnNewsItemClickListener(OnNewsItemClickListener listener) {
        this.mOnNewsItemClickListener = listener;
    }

    public NewsAdapter(Context context) {
        super();
        mContext = context;
    }

    public void add(NewsBean newsBean) {
        int i = getItemCount();

        for (NewsBean.Entity b : newsBean.getEntitylist()) {

            if (i == 12) {//insert ads
                AdBean ad = new AdBean();
                mNewsBeanList.add(i, ad);
            } else {
                mNewsBeanList.add(i, b);
            }

            this.notifyItemInserted(i);
            i++;
        }
//        this.notifyDataSetChanged();
    }

    public void remove(int position) {
        Logger.d(TAG, "remove: positon=" + position);
        mNewsBeanList.remove(position);
        this.notifyItemRemoved(position);
    }

    public void clear() {
        mNewsBeanList.clear();
        this.notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return mNewsBeanList.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NEW) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_news, parent, false);
            NewsItemViewHolder vh = new NewsItemViewHolder(v);
            return vh;
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_ad, parent, false);
            AdItemViewHolder vh = new AdItemViewHolder(v);
            return vh;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (mNewsBeanList.get(position) instanceof NewsBean.Entity) {
            Logger.d(TAG, "onBindViewHolder: position" + position + ", news");
            NewsBean.Entity b = (NewsBean.Entity) mNewsBeanList.get(position);
            ((NewsItemViewHolder) holder).mTitle.setText(b.getTitle());
            ((NewsItemViewHolder) holder).mDesc.setText(b.getDescription());
            Glide.with(mContext)
                    .load(b.getPicUrl())
                    .placeholder(R.drawable.ic_image_loading)
                    .error(R.drawable.ic_image_loadfail)
                    .into(((NewsItemViewHolder) holder).mImg);
            addAnimation(holder);
        } else {
            Logger.d(TAG, "onBindViewHolder: position" + position + ", ads");
            AdBean ad = (AdBean) mNewsBeanList.get(position);
//            ((AdItemViewHolder) holder).mImg.setImageResource(R.drawable.ic_image_ad);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mNewsBeanList.get(position) instanceof NewsBean.Entity) {
            return TYPE_NEW;
        } else {
            return TYPE_AD;
        }
    }

    @Override
    public int getItemCount() {
        return mNewsBeanList.size();
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ivImage)
        ImageView mImg;
        @BindView(R.id.tvTitle)
        TextView mTitle;
        @BindView(R.id.tvDesc)
        TextView mDesc;

        public NewsItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnNewsItemClickListener != null) {
                mOnNewsItemClickListener.onNewsItemClick(v, this.getPosition());
            }
        }

    }

    public class AdItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            BootstrapDropDown.OnDropDownItemClickListener {

        @BindView(R.id.ivImage)
        ImageView mImg;
        @BindView(R.id.bootstrap)
        BootstrapDropDown mBootstrapDropDown;

        public AdItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            mBootstrapDropDown.setOnDropDownItemClickListener(this);
        }

        @Override
        public void onItemClick(ViewGroup parent, View v, int id) {
            if (mOnNewsItemClickListener != null) {
                mOnNewsItemClickListener.onAdDropDownClick(id, this.getPosition());
            }
        }

        @Override
        public void onClick(View v) {
            if (mOnNewsItemClickListener != null) {
                Toast.makeText(mContext, "Ad clicked", Toast.LENGTH_SHORT).show();
//                mOnNewsItemClickListener.onNewsItemClick(v, this.getPosition());
            }
        }
    }

    public void openLoadAnimation(BaseAnimation animation) {
        this.mOpenAnimationEnable = true;
        this.mSelectAnimation = animation;
    }

    public void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = null;
                if (mSelectAnimation != null) {
                    animation = mSelectAnimation;
                }
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim, holder.getLayoutPosition());
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    private void startAnim(Animator anim, int index) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
    }

}
