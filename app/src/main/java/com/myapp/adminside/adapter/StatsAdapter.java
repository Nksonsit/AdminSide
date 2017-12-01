package com.myapp.adminside.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapp.adminside.R;
import com.myapp.adminside.custom.TfTextView;
import com.myapp.adminside.model.Stats;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ishan on 22-11-2017.
 */

public class StatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Stats> list;
    private Context context;

    public StatsAdapter(Context context, List<Stats> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.setValues(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setDataList(List<Stats> list) {
        this.list = new ArrayList<>();
        this.list = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TfTextView txtSite, txtTotalDlUl, txtAvgDl,txtAvgUl;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtSite = (TfTextView) itemView.findViewById(R.id.txtSite);
            txtTotalDlUl = (TfTextView) itemView.findViewById(R.id.txtTotalDlUl);
            txtAvgDl = (TfTextView) itemView.findViewById(R.id.txtAvgDl);
            txtAvgUl = (TfTextView) itemView.findViewById(R.id.txtAvgUl);
        }

        public void setValues(Stats stuts) {
            txtSite.setText(stuts.getSite());
            txtTotalDlUl.setText((int)Float.parseFloat(stuts.getCount()) + "");
            txtAvgDl.setText((int)Float.parseFloat(stuts.getAvgDl()) + "");
            txtAvgUl.setText((int)Float.parseFloat(stuts.getAvgUl()) + "");
        }
    }
}
