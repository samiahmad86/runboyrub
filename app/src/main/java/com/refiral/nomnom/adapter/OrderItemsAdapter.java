package com.refiral.nomnom.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.refiral.nomnom.R;
import com.refiral.nomnom.model.OrderItem;

import java.util.ArrayList;

/**
 * Created by tanay on 13/8/15.
 */
public class OrderItemsAdapter extends BaseAdapter {

    private ArrayList<OrderItem> mOrderList;
    private LayoutInflater mInflater;
    private static final String TAG = OrderItemsAdapter.class.getName();

    public OrderItemsAdapter(Context context, ArrayList<OrderItem> orderItems) {
        this.mOrderList = orderItems;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mOrderList.size();
    }

    @Override
    public Object getItem(int i) {
        return mOrderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mOrderList.get(i).id;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.simple_list_item_1, parent, false);
            vh = new ViewHolder();
            vh.tvOrderItem = (TextView) convertView.findViewById(R.id.text1);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        OrderItem oi = mOrderList.get(i);
        String dishName = oi.dishVariation.dish.dishName;
        String dishQuantity = oi.quantity + " ";
        if(oi.dishVariation.variety != null) {
            dishName = oi.dishVariation.variety + " " + dishName;
        }
        if(oi.dishVariation.portion != null) {
            dishQuantity = dishQuantity + "X " + oi.dishVariation.portion + " ";
            Log.d(TAG, dishQuantity);
        } else {
            Log.d(TAG, "portion is half");
        }
        Log.d(TAG, dishName + " " + dishQuantity);
        vh.tvOrderItem.setText(Html.fromHtml("<font color='#2878B3'>" + dishQuantity + "</font> " + dishName));
        return convertView;
    }

    public static class ViewHolder {
        TextView tvOrderItem;
    }
}
