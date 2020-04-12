//Ahmed Ragab Badawy- S1804193

package com.example.infoquakes.helpers.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.infoquakes.R;
import com.example.infoquakes.helpers.RssFeedModel;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//this is adapter for record list of earthquake and recyclerview list
//it attach both with eachother so that the list is displayed


public class RssFeedListAdapter extends RecyclerView.Adapter<RssFeedListAdapter.FeedModelViewHolder> implements Filterable {

    public List<RssFeedModel> mRssFeedModels;
    private List<RssFeedModel> datafilteredlist;
    

    int[] androidColors;

    public RssFeedListAdapter.OnItemClicked onClick;
    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }


    //mRssFeedModels is a list in which all record is stored.
    //this function takes a charsequence and matches with records data
    //if a record contains that charsequence then it is filtered or stored in a list(filteredlist)

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    datafilteredlist = mRssFeedModels;
                } else {
                    List<RssFeedModel> filteredList = new ArrayList<>();
                    for (RssFeedModel row : mRssFeedModels) {
                        // name match condition. this might differ depending on your requirement
                    
                        if (row.getDescription().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                            System.out.println("matched");
                        }
                    }
                    datafilteredlist = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = datafilteredlist;
                return filterResults;
            }

            //it notifies the changes in list

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                mRssFeedModels = (ArrayList<RssFeedModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public static class FeedModelViewHolder extends RecyclerView.ViewHolder {
        private View rssFeedView;

        public FeedModelViewHolder(View v) {
            super(v);
            rssFeedView = v;
        }
    }

    public RssFeedListAdapter(List<RssFeedModel> rssFeedModels) {
        mRssFeedModels = rssFeedModels;

        //these are colors added to magnitude text and random colors are selected from it for each magnitude


    }

    //holds the view of recycler view

    @NonNull
    @Override
    public RssFeedListAdapter.FeedModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rss_feed, parent, false);
        FeedModelViewHolder holder = new FeedModelViewHolder(v);
        androidColors = parent.getResources().getIntArray(R.array.androidcolors);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RssFeedListAdapter.FeedModelViewHolder holder, final int position) {
        final RssFeedModel rssFeedModel = mRssFeedModels.get(position);
        String title=rssFeedModel.description;
        System.out.println("magni : "+title);


        //these below fields are extracted from records and stored in seperate string
        //so that we can use them anywhere when wanted.
        //magnitude,depth,location etc.
        String[] magnitude = new String[2];
        String[] depth = new String[2];
        String[] location = new String[2];

        String[] parts = title.split(";");
        magnitude = parts[4].split(":");
        depth = parts[3].split(":");
        location = parts[1].split(":");

        mRssFeedModels.get(position).setLocation(location[1].trim());
        mRssFeedModels.get(position).setDepth(depth[1].trim());
        mRssFeedModels.get(position).setMagnitude(magnitude[1].trim());



        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        ((TextView)holder.rssFeedView.findViewById(R.id.locationText)).setText(location[1].trim());
        ((TextView)holder.rssFeedView.findViewById(R.id.dateText)).setText(rssFeedModel.pubdate);

        TextView magnitudetxt=holder.rssFeedView.findViewById(R.id.magnitudeText);
        magnitudetxt.setText(magnitude[1].trim());
        magnitudetxt.setTextColor(randomAndroidColor);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });
    }



    public void setOnClick(RssFeedListAdapter.OnItemClicked onClick)
    {
        this.onClick=onClick;
    }

    @Override
    public int getItemCount() {
        return mRssFeedModels.size();
    }

}
