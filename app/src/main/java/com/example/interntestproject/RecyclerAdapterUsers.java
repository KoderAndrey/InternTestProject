package com.example.interntestproject;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static com.example.interntestproject.MainActivity.LOG_TAG;

/**
 * Created by Андрей on 13.09.2017.
 */

public class RecyclerAdapterUsers extends RecyclerView.Adapter<RecyclerAdapterUsers.UserViewHolder> {
    private Context mContext;
    private CustomItemClickListener mListener;
    private List<ObjectUserDataBase> mList;

    public RecyclerAdapterUsers(List<ObjectUserDataBase> mList, Context mContext, CustomItemClickListener customItemClickListener) {
        this.mList = mList;
        this.mContext = mContext;
        mListener =  customItemClickListener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        final UserViewHolder userViewHolder = new UserViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mListener.onItemClick(v, userViewHolder.getPosition());
            }
        });
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.mName.setText(mList.get(position).getFirstName());
        holder.mLastName.setText(mList.get(position).getLastName());
        holder.mDate.setText(mList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        if (mList == null)
        {return  0;}
        return mList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView mName;
        private TextView mLastName;
        private TextView mDate;
        private String mItem;
        public UserViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.first_name);
            mLastName = (TextView) itemView.findViewById(R.id.last_name);
            mDate = (TextView) itemView.findViewById(R.id.birth_date);
        }
    }
}
