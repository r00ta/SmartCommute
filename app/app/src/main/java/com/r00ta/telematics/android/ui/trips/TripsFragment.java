package com.r00ta.telematics.android.ui.trips;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.r00ta.telematics.android.LoginActivity;
import com.r00ta.telematics.android.R;
import com.r00ta.telematics.android.RecordingTripActivity;

public class TripsFragment extends Fragment {

    private TripsViewModel tripsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tripsViewModel =
                ViewModelProviders.of(this).get(TripsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_trips, container, false);
        final FloatingActionButton recordTripBtn = (FloatingActionButton) root.findViewById(R.id.recordOccasionalTrip);
        recordTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecordingTripActivity.class);
                startActivity(intent);
            }
        });

//        tripsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
