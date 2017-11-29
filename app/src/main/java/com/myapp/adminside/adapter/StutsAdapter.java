package com.myapp.adminside.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapp.adminside.R;
import com.myapp.adminside.custom.TfTextView;
import com.myapp.adminside.model.Site;
import com.myapp.adminside.model.Stuts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ishan on 22-11-2017.
 */

public class StutsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Stuts> list;
    private Context context;

    public StutsAdapter(Context context, List<Stuts> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_stuts, parent, false);
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

    public void setDataList(List<Stuts> list) {
        this.list = new ArrayList<>();
        this.list = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TfTextView txtSite, txtTotalDl, txtAvgDl;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtSite = (TfTextView) itemView.findViewById(R.id.txtSite);
            txtTotalDl = (TfTextView) itemView.findViewById(R.id.txtTotalDl);
            txtAvgDl = (TfTextView) itemView.findViewById(R.id.txtAvgDl);
        }

        public void setValues(Stuts stuts) {
            txtSite.setText(stuts.getSite());
            txtTotalDl.setText(stuts.getTotal());
            txtAvgDl.setText(stuts.getCount());
        }
    }
}
