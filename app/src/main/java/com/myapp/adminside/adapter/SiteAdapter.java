package com.myapp.adminside.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.myapp.adminside.R;
import com.myapp.adminside.api.AppApi;
import com.myapp.adminside.custom.TfTextView;
import com.myapp.adminside.helper.Functions;
import com.myapp.adminside.helper.MyApplication;
import com.myapp.adminside.helper.PrefUtils;
import com.myapp.adminside.model.BaseResponse;
import com.myapp.adminside.model.Site;
import com.myapp.adminside.ui.AddSiteActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ishan on 22-11-2017.
 */

public class SiteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnListEmpty onListEmpty;
    private List<Site> list;
    private Context context;
    private static final long MIN_CLICK_INTERVAL = 1000;

    private long mLastClickTime;

    public SiteAdapter(Context context, List<Site> list,OnListEmpty onListEmpty) {
        this.context = context;
        this.onListEmpty=onListEmpty;
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
        private ImageView imgEdit, imgDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtSite = (TfTextView) itemView.findViewById(R.id.txtSite);
            txtDesc = (TfTextView) itemView.findViewById(R.id.txtDesc);
            txtDistance = (TfTextView) itemView.findViewById(R.id.txtDistance);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
        }

        public void setValues(Site site) {
            txtDistance.setText(site.getDistance());
            txtDesc.setText(site.getDescription().replace("&#39;","'"));
            txtSite.setText(site.getSite().replace("&#39;","'"));

          /*  if (site.getUserId().equalsIgnoreCase(PrefUtils.getUserID(context))) {
                imgEdit.setVisibility(View.VISIBLE);
                imgDelete.setVisibility(View.VISIBLE);
            } else {
                imgEdit.setVisibility(View.GONE);
                imgDelete.setVisibility(View.GONE);
            }
*/

            imgEdit.setVisibility(View.VISIBLE);
            imgDelete.setVisibility(View.VISIBLE);

            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddSiteActivity.class);
                    intent.putExtra("site", list.get(getAdapterPosition()));
                    Functions.fireIntent(context, intent, true);
                }
            });
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    long currentClickTime = SystemClock.uptimeMillis();
                    long elapsedTime = currentClickTime - mLastClickTime;

                    mLastClickTime = currentClickTime;

                    if (elapsedTime <= MIN_CLICK_INTERVAL)
                        return;

                    Functions.showMsg(context, "Are you sure want to delete this site?", new Functions.OnDialogButtonClickListener() {
                        @Override
                        public void onWhichClick(boolean click) {
                            if(click){
                                AppApi appApi = MyApplication.getRetrofit().create(AppApi.class);
                                Log.e("delete site", MyApplication.getGson().toJson(list.get(getAdapterPosition())));
                                appApi.deleteSite(list.get(getAdapterPosition())).enqueue(new Callback<BaseResponse<Site>>() {
                                    @Override
                                    public void onResponse(Call<BaseResponse<Site>> call, Response<BaseResponse<Site>> response) {
                                        if (response.body() != null && response.body().getStatus() == 1) {
                                            list.remove(getAdapterPosition());
                                            notifyItemRemoved(getAdapterPosition());

                                            if(list.size()==0){
                                                onListEmpty.onListEmpty();
                                            }
                                        } else {
                                            Functions.showToast(context, context.getString(R.string.try_again));
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<BaseResponse<Site>> call, Throwable t) {
                                        Functions.showToast(context, context.getString(R.string.try_again));
                                    }
                                });
                            }
                        }
                    });

                }
            });
        }
    }
    public interface OnListEmpty{
        void onListEmpty();
    }
}
