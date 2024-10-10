package com.nyok.bottom_navigation.database;

import android.content.Context;
import android.widget.Toast;

import com.nyok.bottom_navigation.domain.PopularDomain;

import java.util.ArrayList;

public class ManagmentCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(PopularDomain item) {
        ArrayList<PopularDomain> listpop = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listpop.size(); i++) {
            if (listpop.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        if (existAlready) {
            listpop.get(n).setNumberInCart(item.getNumberInCart());
        } else {
            listpop.add(item);
        }

        tinyDB.putListObject("CartList", listpop);
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<PopularDomain> getListCart() {
        ArrayList<PopularDomain> list = tinyDB.getListObject("CartList");
        return list != null ? list : new ArrayList<>(); // Mengembalikan list kosong jika null
    }


    public Double getTotalFee() {
        ArrayList<PopularDomain> listItem = getListCart();
        double fee = 0;
        for (PopularDomain item : listItem) {
            fee += (item.getPrice() * item.getNumberInCart());
        }
        return fee;
    }

    public void minusNumberItem(ArrayList<PopularDomain> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listItem != null && !listItem.isEmpty() && position < listItem.size()) {
            if (listItem.get(position).getNumberInCart() == 1) {
                listItem.remove(position);
            } else {
                listItem.get(position).setNumberInCart(listItem.get(position).getNumberInCart() - 1);
            }
            tinyDB.putListObject("CartList", listItem);
            changeNumberItemsListener.change();
        }
    }

    public void plusNumberItem(ArrayList<PopularDomain> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listItem != null && !listItem.isEmpty() && position < listItem.size()) {
            listItem.get(position).setNumberInCart(listItem.get(position).getNumberInCart() + 1);
            tinyDB.putListObject("CartList", listItem);
            changeNumberItemsListener.change();
        }
    }

}

