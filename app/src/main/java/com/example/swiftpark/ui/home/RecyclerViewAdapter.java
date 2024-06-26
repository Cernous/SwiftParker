package com.example.swiftpark.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swiftpark.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";


    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mAddresses = new ArrayList<>();
    private Context mContext;

    private Fragment mFragment;

    public RecyclerViewAdapter(Context context, ArrayList<String> names, ArrayList<String> imageUrls, ArrayList<String> addresses) {
        mNames = names;
        mImageUrls = imageUrls;
        mContext = context;
        mAddresses = addresses;
    }

    public RecyclerViewAdapter(Fragment fragment, ArrayList<String> names, ArrayList<String> imageUrls, ArrayList<String> addresses) {
        mNames = names;
        mImageUrls = imageUrls;
        mFragment = fragment;
        mContext = mFragment.getContext();
        mAddresses = addresses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.image);

        holder.name.setText(mNames.get(position));
        holder.address.setText(mAddresses.get(position));

        holder.listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: clicked on an image: " + mNames.get(position));
                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
                if(mImageUrls.get(position).contentEquals("https://www.clipartbest.com/cliparts/dT8/XEd/dT8XEdj6c.png")) return;
                parkingInfoDialog dialog = new parkingInfoDialog(mFragment, mAddresses.get(position).replace(" ", "_"));
                dialog.show(mFragment.getParentFragmentManager(), "parkingLotDialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView address;

        CardView listView;

        public ViewHolder(View itemView){
            super(itemView);
            listView = itemView.findViewById(R.id.card);
            address = itemView.findViewById(R.id.item_address);
            image = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);
        }
    }
}
