package com.example.yeipos.interfaces;
import android.view.View;

public interface ItemClickListener {
    void onDeleteClick(View view, int position);
    void onEditClick(View view, int position);
}

