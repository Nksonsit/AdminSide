package com.myapp.adminside.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.myapp.adminside.R;
import com.myapp.adminside.custom.TfTextView;
import com.myapp.adminside.helper.Functions;
import com.myapp.adminside.model.Site;
import com.myapp.adminside.ui.AddSiteActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ishan on 22-11-2017.
 */

public class SiteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Site> list;
    private Context context;

    public SiteAdapter(Context context, List<Site> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_site, parent, false);
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

    public void setDataList(List<Site> list) {
        this.list = new ArrayList<>();
        this.list = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TfTextView txtSite, txtDesc, txtDistance;
        private ImageView imgEdit,imgDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtSite = (TfTextView) itemView.findViewById(R.id.txtSite);
            txtDesc = (TfTextView) itemView.findViewById(R.id.txtDesc);
            txtDistance = (TfTextView) itemView.findViewById(R.id.txtDistance);
            imgEdit = (ImageView)itemView.findViewById(R.id.imgEdit);
            imgDelete= (ImageView)itemView.findViewById(R.id.imgDelete);
        }

        public void setValues(Site site) {
            txtDistance.setText(site.getDistance());
            txtDesc.setText(site.getDescription());
            txtSite.setText(site.getSite());
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, AddSiteActivity.class);
                    intent.putExtra("site",list.get(getAdapterPosition()));
                    Functions.fireIntent(context,intent,true);
                }
            });
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
        }
    }
}
