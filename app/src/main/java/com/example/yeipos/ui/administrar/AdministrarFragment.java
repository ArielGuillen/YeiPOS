package com.example.yeipos.ui.administrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.yeipos.R;

public class AdministrarFragment extends Fragment {

    private AdministrarViewModel administrarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        administrarViewModel = new ViewModelProvider(this).get(AdministrarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_administrar, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        administrarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}