package com.r00ta.telematics.android.ui.trips;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.r00ta.telematics.android.R;
import com.r00ta.telematics.android.persistence.retrieved.trips.TripHeader;
import com.r00ta.telematics.android.ui.RecyclerViewClickListener;
import com.r00ta.telematics.android.utils.RealmRecyclerViewAdapter;

import java.util.HashSet;
import java.util.Set;

import io.realm.OrderedRealmCollection;

class TripsRecyclerViewAdapter extends RealmRecyclerViewAdapter<TripHeader, TripsRecyclerViewAdapter.MyViewHolder> {

    private boolean inDeletionMode = false;
    private Set<Integer> countersToDelete = new HashSet<>();
    private RecyclerViewClickListener mListener;

    TripsRecyclerViewAdapter(OrderedRealmCollection<TripHeader> data, RecyclerViewClickListener listener) {
        super(data, true);
        mListener = listener;
        // Only set this if the model class has a primary key that is also a integer or long.
        // In that case, {@code getItemId(int)} must also be overridden to return the key.
        // See https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#hasStableIds()
        // See https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#getItemId(int)
        setHasStableIds(true);
    }

    void enableDeletionMode(boolean enabled) {
        inDeletionMode = enabled;
        if (!enabled) {
            countersToDelete.clear();
        }
        notifyDataSetChanged();
    }

    Set<Integer> getCountersToDelete() {
        return countersToDelete;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trips_card_layout, parent, false);
        return new MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final TripHeader obj = getItem(position);
        holder.data = obj;
        final long itemId = obj.id;
        //noinspection ConstantConditions
        holder.startDate.setText(obj.startDate);
        holder.startLocation.setText(obj.startLocation);
        holder.endDate.setText(obj.endDate);
        holder.endLocation.setText(obj.endLocation);
        holder.tripDate.setText(obj.tripDate);
        holder.distance.setText(obj.distance);

//        if (inDeletionMode) {
//            holder.subTitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked) {
//                        countersToDelete.add(itemId);
//                    } else {
//                        countersToDelete.remove(itemId);
//                    }
//                }
//            });
//        } else {
//            holder.subTitle.setOnCheckedChangeListener(null);
//        }
        //holder.subTitle.setVisibility(inDeletionMode ? View.VISIBLE : View.GONE);
    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).id;
    }

    class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        private RecyclerViewClickListener mListener;

        TextView startDate;
        TextView startLocation;

        TextView endDate;
        TextView endLocation;

        TextView tripDate;
        TextView distance;

        public TripHeader data;

        MyViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            mListener = listener;
            view.setClickable(true);
            view.setOnClickListener(this);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            startDate = view.findViewById(R.id.routeStartLabel);
            startLocation = view.findViewById(R.id.startCity);

            endDate = view.findViewById(R.id.endDate);
            endLocation = view.findViewById(R.id.endCity);

            tripDate = view.findViewById(R.id.trip_date);
            distance = view.findViewById(R.id.distance);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }
}