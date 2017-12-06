package com.example.onurhuseyincantay.myshoppingcart;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bertug on 06/12/2017.
 */

public class ShoppingCartAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<ShoppingCart> shoppingCartList;

    public ShoppingCartAdapter(Activity activity, List<ShoppingCart> carts){
        layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        shoppingCartList = carts;
    }

    @Override
    public int getCount(){
        return shoppingCartList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row;
        TextView nameTextView;
        TextView countTextView;
        Button removeButton;
        ShoppingCart shoppingCart;
        final int position = i;

        final ShoppingCartAdapter me = this;

        final CheckedTextView checkedTextView;
        String type;

        row = layoutInflater.inflate(R.layout.shopping_list_cell, null);
        shoppingCart = shoppingCartList.get(i);

        nameTextView = (TextView)row.findViewById(R.id.nameTextView);
        countTextView = (TextView)row.findViewById(R.id.countTextView);
        removeButton = (Button)row.findViewById(R.id.removeButton);

        nameTextView.setText(shoppingCart.getProductName());

        type = shoppingCart.getProductCount() + " " + shoppingCart.productType;
        countTextView.setText(type);

        checkedTextView = (CheckedTextView)row.findViewById(R.id.checkedTextView);

        checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checked;
                checked = checkedTextView.isChecked();
                checkedTextView.setChecked(!checked);
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(view.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(view.getContext());
                }
                builder.setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                shoppingCartList.remove(position);
                                me.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("Selam", "olmadı");
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        return row;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    public void setShoppingCartList(List<ShoppingCart> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }

    public List<ShoppingCart> getShoppingCartList() {
        return shoppingCartList;
    }

    public boolean alertDialog(View view){
        final boolean[] isDelete = new boolean[1];


        return isDelete[0];
    }

}
