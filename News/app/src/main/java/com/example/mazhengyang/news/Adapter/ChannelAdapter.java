package com.example.mazhengyang.news.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mazhengyang.news.Bean.ChannelBean;
import com.example.mazhengyang.news.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-8-29.
 */

public class ChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChannelBean.Channel> channelList;
    private boolean canDrag = false;
    private boolean editing = false;

    private Context mContext;

    private OnDataChangedListener mOnDataChangedListener;

    public interface OnDataChangedListener {
        void onAdd(ChannelBean.Channel channel);

        void onDelete(ChannelBean.Channel channel);
    }

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public ChannelAdapter(Context mContext, List<ChannelBean.Channel> list, boolean drag) {
        this.mContext = mContext;
        channelList = list;
        canDrag = drag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_channel, parent, false);
        ChannelViewHolder cv = new ChannelViewHolder(v);
        return cv;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((ChannelViewHolder) holder).tv_title.setText(channelList.get(position).getName());

        if (canDrag && editing) {
            ((ChannelViewHolder) holder).iv_close.setVisibility(View.VISIBLE);
        } else {
            ((ChannelViewHolder) holder).iv_close.setVisibility(View.INVISIBLE);
        }

        ((ChannelViewHolder) holder).tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!canDrag && mOnDataChangedListener != null) {
                    if (channelList != null && channelList.size() > 0) {
                        mOnDataChangedListener.onDelete(channelList.get(position));
                        channelList.remove(position);
                        notifyDataSetChanged();
                    }
                }
            }
        });

        ((ChannelViewHolder) holder).tv_title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!editing) {
                    editing = true;
                    notifyDataSetChanged();
                }
                return true;
            }
        });

        ((ChannelViewHolder) holder).iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canDrag && editing) {
                    if (channelList != null && channelList.size() > 0) {
                        mOnDataChangedListener.onDelete(channelList.get(position));
                        channelList.remove(position);
                        notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    public void add(ChannelBean.Channel channel) {
        channelList.add(channel);
        notifyItemInserted(channelList.size() - 1);
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
        notifyDataSetChanged();
    }

    public boolean isEditing() {
        return editing;
    }

    public List<ChannelBean.Channel> getList() {
        return channelList;
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_channel_title)
        TextView tv_title;
        @BindView(R.id.tv_channel_close)
        ImageView iv_close;

        public ChannelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
