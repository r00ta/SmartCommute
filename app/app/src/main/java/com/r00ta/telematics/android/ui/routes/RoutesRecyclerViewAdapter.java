package com.r00ta.telematics.android.ui.routes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.r00ta.telematics.android.R;
import com.r00ta.telematics.android.persistence.retrieved.routes.RouteHeader;
import com.r00ta.telematics.android.ui.RecyclerViewClickListener;
import com.r00ta.telematics.android.utils.RealmRecyclerViewAdapter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import io.realm.OrderedRealmCollection;

class RoutesRecyclerViewAdapter extends RealmRecyclerViewAdapter<RouteHeader, RoutesRecyclerViewAdapter.MyViewHolder> {

    private boolean inDeletionMode = false;
    private Set<Integer> countersToDelete = new HashSet<>();
    private RecyclerViewClickListener mListener;
    private RecordRouteTripBtnListener btnListener;

    RoutesRecyclerViewAdapter(OrderedRealmCollection<RouteHeader> data, RecyclerViewClickListener listener, RecordRouteTripBtnListener btnListener) {
        super(data, true);
        mListener = listener;
        this.btnListener = btnListener;
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.routes_card_layout, parent, false);
        return new MyViewHolder(itemView, mListener, btnListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final RouteHeader obj = getItem(position);
        holder.data = obj;
        final long itemId = obj.id;
        //noinspection ConstantConditions
        holder.routeStartLabel.setText(obj.startPositionUserLabel);
        holder.routeToLabel.setText(obj.endPositionUserLabel);
        holder.routeDaysLabel.setText(String.join(", ", obj.days.stream().map(x -> x.toString()).collect(Collectors.toList())));
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
//        holder.subTitle.setVisibility(inDeletionMode ? View.VISIBLE : View.GONE);
    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).id;
    }

    class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        private RecyclerViewClickListener mListener;

        TextView routeStartLabel;
        TextView routeToLabel;

        TextView routeDaysLabel;

        Button startRouteTripRecording;

        public RouteHeader data;

        MyViewHolder(View view, RecyclerViewClickListener listener, RecordRouteTripBtnListener btnListener) {
            super(view);
            mListener = listener;
            view.setClickable(true);
            view.setOnClickListener(this);

            startRouteTripRecording = view.findViewById(R.id.recordRouteTripBtn);
            startRouteTripRecording.setClickable(true);
            startRouteTripRecording.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    btnListener.onClick(v, data.routeId);
                }
            });

            routeStartLabel = view.findViewById(R.id.routeStartLabel);
            routeToLabel = view.findViewById(R.id.routeToLabel);

            routeDaysLabel = view.findViewById(R.id.routeDaysHeader);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }
}