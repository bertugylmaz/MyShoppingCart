package com.example.onurhuseyincantay.myshoppingcart;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        ShoppingCart shoppingCart;
        final CheckedTextView checkedTextView;
        String type;

        row = layoutInflater.inflate(R.layout.shopping_list_cell, null);
        shoppingCart = shoppingCartList.get(i);

        nameTextView = (TextView)row.findViewById(R.id.nameTextView);

        countTextView = (TextView)row.findViewById(R.id.countTextView);

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
}
