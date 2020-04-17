package com.r00ta.telematics.android.ui.trips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.r00ta.telematics.android.R;

public class TripsFragment extends Fragment {

    private TripsViewModel tripsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tripsViewModel =
                ViewModelProviders.of(this).get(TripsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_trips, container, false);
        final TextView textView = root.findViewById(R.id.text_trips);
        tripsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
