package com.example.onurhuseyincantay.myshoppingcart.Adapter;

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

import com.example.onurhuseyincantay.myshoppingcart.Model.ShoppingList;
import com.example.onurhuseyincantay.myshoppingcart.Network.DataService;
import com.example.onurhuseyincantay.myshoppingcart.R;

import java.util.List;

/**
 * Created by Bertug on 06/12/2017.
 */

public class ShoppingCartAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<ShoppingList> ShoppingLists;

    public ShoppingCartAdapter(Activity activity, List<ShoppingList> shoppinglists){
        layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ShoppingLists = shoppinglists;
    }

    @Override
    public int getCount(){
        return ShoppingLists.size();
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
        ShoppingList item;
        final int position = i;

        final ShoppingCartAdapter me = this;

        final CheckedTextView checkedTextView;


        row = layoutInflater.inflate(R.layout.shopping_list_cell, null);
        item = ShoppingLists.get(i);

        nameTextView = (TextView)row.findViewById(R.id.nameTextView);
        countTextView = (TextView)row.findViewById(R.id.countTextView);
        removeButton = (Button)row.findViewById(R.id.removeButton);

        nameTextView.setText(item.getName());

        /*checkedTextView = (CheckedTextView)row.findViewById(R.id.checkedTextView);

        checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checked;
                checked = checkedTextView.isChecked();
                checkedTextView.setChecked(!checked);
            }
        });
*/

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
                                ShoppingList selected;
                                selected = ShoppingLists.get(position);

                                DataService.ds.removeSelectedCart(selected.getListId());
                                ShoppingLists.clear();
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

    public void setShoppingCartList(List<ShoppingList> itemList) {
        this.ShoppingLists= itemList;
    }

    public List<ShoppingList> getShoppingCartList() {
        return ShoppingLists;
    }

    public boolean alertDialog(View view){
        final boolean[] isDelete = new boolean[1];

        return isDelete[0];
    }

}
