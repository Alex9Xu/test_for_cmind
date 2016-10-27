package com.alex9xu.mytest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex9xu.mytest.R;
import com.alex9xu.mytest.model.NumCounter;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Alex9Xu@hotmail.com on 2016/10/26
 */

public class NumShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "NumShowAdapter";

    private List<NumCounter> mDataList;

    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;
    private static WeakReference<Context> mContextRef;

    public NumShowAdapter(Context context, List<NumCounter> dataList)
    {
        mInflater = LayoutInflater.from(context);
        mDataList = dataList;
        mContextRef = new WeakReference<>(context);
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View itemView = mInflater.inflate(R.layout.item_num_show, viewGroup, false);
        return new ViewHolderNumShow(itemView);
    }

    private class ViewHolderNumShow extends RecyclerView.ViewHolder
    {
        TextView tvwNum;
        TextView tvwCount;

        public ViewHolderNumShow(View itemView)
        {
            super(itemView);

            tvwNum = (TextView) itemView.findViewById(R.id.item_num_show_num);
            tvwCount = (TextView) itemView.findViewById(R.id.item_num_show_count);
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount()
    {
        return mDataList.size();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position)
    {
        if(null == viewHolder || null == mDataList || position > mDataList.size()) {
            return;
        }

        final ViewHolderNumShow holder = (ViewHolderNumShow) viewHolder;

        holder.tvwNum.setText(mDataList.get(position).getNumber());
        holder.tvwCount.setText(mDataList.get(position).getTimes());

        if (mOnItemClickListener != null)
        {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickListener.onItemClick(viewHolder.itemView, position);
                }
            });
        }
    }


}





