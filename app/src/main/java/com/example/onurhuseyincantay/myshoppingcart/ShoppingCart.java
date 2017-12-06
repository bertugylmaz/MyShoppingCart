package com.example.onurhuseyincantay.myshoppingcart;

/**
 * Created by Bertug on 06/12/2017.
 */

public class ShoppingCart {

    public String productName;
    public String productCount;
    public String productType;

    public ShoppingCart(String name, String  count, String type){
        super();
        this.productName = name;
        this.productCount = count;
        this.productType = type;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }
}
