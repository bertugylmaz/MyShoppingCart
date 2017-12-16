package com.example.onurhuseyincantay.myshoppingcart.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Bertug on 15/12/2017.
 */

public class ShoppingList implements Parcelable {
    private String ListId;
    private String name;
    private List<String> Items;

    public ShoppingList(String listId, String name) {
        ListId = listId;
        this.name = name;
    }
    public String getListId() {
        return ListId;
    }
    public List<String> getItems() {
        return Items;
    }
    public String getName() {
        return name;
    }

    public ShoppingList(Parcel in){
        String [] data = new String[1];
        in.readStringArray(data);
        this.ListId = data[0];
        this.name = data[0];
    }
    @Override
    public int describeContents(){
        return  0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{
                this.ListId,this.name
        });

    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public  ShoppingList createFromParcel (Parcel in){
            return  new ShoppingList(in);
        }
        public ShoppingList[] newArray(int size){
            return  new ShoppingList[size];
        }
    };
}
