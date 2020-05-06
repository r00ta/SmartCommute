package com.r00ta.telematics.android.ui.routes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.r00ta.telematics.android.R;

public class RoutesFragment extends Fragment {

    private RoutesViewModel routesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        routesViewModel =
                ViewModelProviders.of(this).get(RoutesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_trips, container, false);
//        final TextView textView = root.findViewById(R.id.text_routes);
//        routesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
