package com.coderdhruv.unitconvert.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.coderdhruv.unitconvert.R;

public class CustomDialog {

    public interface OnItemSelectedListener {
        void onItemSelected(String selectedItem, String selectedUnit);
    }

    private final Context context;

    public CustomDialog(Context context) {
        this.context = context;
    }

    public void UnitDialog(String[] items, String[] unitMap, OnItemSelectedListener listener) {
        Dialog dialog = new Dialog(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
        dialog.setContentView(dialogView);

        ListView listView = dialog.findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.custom_list_item, R.id.itemTextView, items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                View divider = view.findViewById(R.id.divider);
                if (position == getCount() - 1) {
                    divider.setVisibility(View.GONE);
                } else {
                    divider.setVisibility(View.VISIBLE);
                }

                return view;
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = items[position];
            String selectedUnit = unitMap[position];
            listener.onItemSelected(selectedItem, selectedUnit);
            dialog.dismiss();
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.dimAmount = 0.95f;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
