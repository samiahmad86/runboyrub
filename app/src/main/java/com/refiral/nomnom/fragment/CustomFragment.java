package com.refiral.nomnom.fragment;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.refiral.nomnom.R;
import com.refiral.nomnom.activity.BaseActivity;
import com.refiral.nomnom.adapter.OrderItemsAdapter;
import com.refiral.nomnom.config.Constants;
import com.refiral.nomnom.model.Order;
import com.refiral.nomnom.model.SimpleResponse;
import com.refiral.nomnom.request.StatusRequest;
import com.refiral.nomnom.util.PrefUtils;

import java.io.File;

import retrofit.mime.TypedFile;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomFragment extends BaseFragment implements View.OnClickListener, RequestListener<SimpleResponse>, TextView.OnEditorActionListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CODE = "code";
    private static Order order;
    private int code;
    private static final String TAG = CustomFragment.class.getName();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param code tell the fragment the view to load.
     * @return A new instance of fragment CustomFragment.
     */
    public static CustomFragment newInstance(int code) {
        CustomFragment fragment = new CustomFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CODE, code);
        fragment.setArguments(args);
        return fragment;
    }

    public CustomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            code = getArguments().getInt(ARG_CODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment based on the instance variable code
        View view = null;

        switch (code) {
            case Constants.Values.STATUS_PLACEHOLDER: {
                view = inflater.inflate(R.layout.fragment_placeholder, container, false);
                break;
            }

            case Constants.Values.STATUS_CONFIRMED:
            case Constants.Values.STATUS_ARRIVED_AT_RESTAURANT: {
                view = inflater.inflate(R.layout.fragment_arrived_confirmed, container, false);
                Button btnStatus = (Button) view.findViewById(R.id.btn_status);
                if (code == Constants.Values.STATUS_CONFIRMED) {
                    btnStatus.setText(getActivity().getResources().getString(R.string.accept_delivery));
                } else {
                    btnStatus.setText(getActivity().getResources().getString(R.string.reached_restaurant));
                }
                View vRest = view.findViewById(R.id.layout_restaurant);
                if (getOrder() == null) {
                    Log.d(TAG, "order is null");
                } else {
                    try {
                        Log.d(TAG, "order isn't null " + (order.restaurant == null));
                        Log.d(TAG, "order isn't null " + order.id + order.customer.name);
                    } catch (NullPointerException ex) {
                        Log.d(TAG, "restauraant is null");
                    }
                }
                ((TextView) vRest.findViewById(R.id.tv_name)).setText(getOrder().restaurant.brand);
                ((TextView) vRest.findViewById(R.id.tv_address)).setText(getOrder().restaurant.address);
                ((TextView) vRest.findViewById(R.id.tv_details_heading)).
                        setText(getActivity().getResources().getString(R.string.restaurant_details));
                if (getOrder().restaurant.numbers.size() > 0) {
                    ((TextView) vRest.findViewById(R.id.tv_ph_no)).setText("Ph No. " + getOrder().restaurant.numbers.get(0).toString());
                }
                View vCust = view.findViewById(R.id.layout_customer);
                ((TextView) vCust.findViewById(R.id.tv_name)).setText(getOrder().customer.name);
                ((TextView) vCust.findViewById(R.id.tv_address)).setText(getOrder().address.completeAddress);
                ((TextView) vCust.findViewById(R.id.tv_ph_no)).setText("Ph No. " + getOrder().customer.primaryNumber);
                ((TextView) vCust.findViewById(R.id.tv_details_heading)).
                        setText(getActivity().getResources().getString(R.string.customer_details));
                btnStatus.setOnClickListener(this);
                break;
            }

            case Constants.Values.STATUS_PICKUP_MATCH: {
                view = inflater.inflate(R.layout.fragment_pickup_match, container, false);
                ((TextView) view.findViewById(R.id.tv_item_count)).setText("Items = " + getOrder().orderItems.size());
                ((ListView) view.findViewById(R.id.layout_order_list)).setAdapter(new OrderItemsAdapter(getActivity(), getOrder().orderItems));
                view.findViewById(R.id.btn_status).setOnClickListener(this);
                break;
            }

            case Constants.Values.STATUS_PICKUP_PAY: {
                view = inflater.inflate(R.layout.fragment_delivered, container, false);
                ((TextView) view.findViewById(R.id.tv_payment_heading)).setText(getActivity().getResources().getString(R.string.pay_to_restaurant));
                ((TextView) view.findViewById(R.id.tv_collectable_ammount)).setText(Html.fromHtml("<font color='#2876B4'>Payable Ammount : </font> " + getOrder().totalAmount + " \u20B9"));
                EditText etAmt = (EditText) view.findViewById(R.id.et_payment_cash);
                etAmt.setHint("");
                etAmt.requestFocus();
                showKeyboard();
                view.findViewById(R.id.et_payment_card).setVisibility(View.GONE);
                view.findViewById(R.id.btn_status).setOnClickListener(this);
                break;
            }

            case Constants.Values.STATUS_PICKUP_PHOTO: {
                view = inflater.inflate(R.layout.fragment_pickup_photo, container, false);
                break;
            }

            case Constants.Values.STATUS_PICKUP_CONFIRM: {
                view = inflater.inflate(R.layout.fragment_pickup_confirm, container, false);
                Log.d(TAG, PrefUtils.getBillPhoto());
                File fBill = new File(PrefUtils.getBillPhoto());
                if (fBill.exists()) {
                    Bitmap bill = BitmapFactory.decodeFile(PrefUtils.getBillPhoto());
                    ((ImageView) view.findViewById(R.id.iv_bill)).setImageBitmap(bill);
                }
                view.findViewById(R.id.iv_accept).setOnClickListener(this);
                view.findViewById(R.id.iv_cancel).setOnClickListener(this);
                break;
            }

            case Constants.Values.STATUS_REACHED_CUSTOMER_ADDRESS: {
                view = inflater.inflate(R.layout.fragment_reached_customer, container, false);
                View vCust = view.findViewById(R.id.layout_customer);
                ((TextView) vCust.findViewById(R.id.tv_name)).setText(getOrder().customer.name);
                ((TextView) vCust.findViewById(R.id.tv_address)).setText(getOrder().address.completeAddress);
                ((TextView) vCust.findViewById(R.id.tv_ph_no)).setText("Ph No. " + getOrder().customer.primaryNumber);
                ((TextView) vCust.findViewById(R.id.tv_details_heading)).
                        setText(getActivity().getResources().getString(R.string.customer_details));
                ((ListView) view.findViewById(R.id.layout_order_list)).setAdapter(new OrderItemsAdapter(getActivity(), getOrder().orderItems));
                Button btnStatus = (Button) view.findViewById(R.id.btn_status);
                btnStatus.setText(getActivity().getResources().getString(R.string.reached_customer_address));
                btnStatus.setOnClickListener(this);
                break;
            }

            case Constants.Values.STATUS_DELIVERED: {
                view = inflater.inflate(R.layout.fragment_delivered, container, false);
                showKeyboard();
                ((TextView) view.findViewById(R.id.tv_collectable_ammount)).setText(Html.fromHtml("<font color='#2876B4'>Collectable Ammount : </font> " + getOrder().totalAmount + " \u20B9"));
                view.findViewById(R.id.btn_status).setOnClickListener(this);
                break;
            }
        }
        return view;
    }


    @Override
    public void onStop() {
        super.onStop();
        if (isKeyboardShown) {
            hideKeyboard();
        }
    }

    @Override
    public void onClick(final View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_status: {

                view.setEnabled(false);

                switch (code) {

                    case Constants.Values.STATUS_CONFIRMED: {
                        StatusRequest sr = new StatusRequest(PrefUtils.getAccessToken(), getOrder().id, Constants.Values.STATUS_STR_CONFIRMED);
                        mSpiceManager.execute(sr, this);
                        break;
                    }

                    case Constants.Values.STATUS_ARRIVED_AT_RESTAURANT: {
                        StatusRequest sr = new StatusRequest(PrefUtils.getAccessToken(), getOrder().id, Constants.Values.STATUS_STR_ARRIVED);
                        mSpiceManager.execute(sr, this);
                        break;
                    }

                    case Constants.Values.STATUS_PICKUP_MATCH: {
                        onRequestSuccess(null);
                        break;
                    }

                    case Constants.Values.STATUS_PICKUP_PAY: {
                        String amountPaid = ((EditText) getView().findViewById(R.id.et_payment_cash)).getText().toString();
                        if (amountPaid.length() == 0 || !isAmount(amountPaid)) {
                            Toast.makeText(getActivity(), "Enter valild amount", Toast.LENGTH_SHORT).show();
                            view.setEnabled(true);
                            return;
                        }
                        StatusRequest sr = new StatusRequest(PrefUtils.getAccessToken(), getOrder().id,
                                Constants.Values.STATUS_STR_PICKUP, new TypedFile("multipart/form-data",
                                new File(PrefUtils.getBillPhoto())), amountPaid);
                        // TODO: remove the comments once the API gets fixed
//                        mSpiceManager.execute(sr, this);
                        onRequestSuccess(null);
                        break;
                    }

                    case Constants.Values.STATUS_REACHED_CUSTOMER_ADDRESS: {
                        StatusRequest sr = new StatusRequest(PrefUtils.getAccessToken(), getOrder().id, Constants.Values.STATUS_STR_REACHED);
                        mSpiceManager.execute(sr, this);
                        break;
                    }

                    case Constants.Values.STATUS_DELIVERED: {
                        String paymentViaCash = ((EditText) getView().findViewById(R.id.et_payment_cash)).getText().toString();
                        String paymentViaCard = ((EditText) getView().findViewById(R.id.et_payment_card)).getText().toString();

                        if (paymentViaCard.length() == 0 && paymentViaCard.length() == 0) {
                            Toast.makeText(getActivity(), "Enter valild amount", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (paymentViaCard.length() == 0 && !isAmount(paymentViaCash)) {
                            Toast.makeText(getActivity(), "Enter valild amount", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (paymentViaCash.length() == 0 && !isAmount(paymentViaCard)) {
                            Toast.makeText(getActivity(), "Enter valild amount", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        StatusRequest sr = new StatusRequest(PrefUtils.getAccessToken(), getOrder().id,
                                Constants.Values.STATUS_STR_DELIVERED, paymentViaCash, paymentViaCard, true, null, null);
                        mSpiceManager.execute(sr, this);
                        break;
                    }
                }

                break;
            }

            case R.id.iv_accept: {
                PrefUtils.setStatus(Constants.Values.STATUS_PICKUP_PAY);
                fil.onFragmentInteraction(Constants.Values.STATUS_PICKUP_PAY, null);
                break;
            }

            case R.id.iv_cancel: {
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            }
        }
    }


    @Override
    public void onRequestFailure(SpiceException spiceException) {
        if (getView() != null) {
            getView().findViewById(R.id.btn_status).setEnabled(true);
        }
    }

    @Override
    public void onRequestSuccess(SimpleResponse simpleResponse) {

        switch (code) {

            case Constants.Values.STATUS_CONFIRMED: {
                Log.d(TAG, "status confirmed");
                PrefUtils.setStatus(Constants.Values.STATUS_ARRIVED_AT_RESTAURANT);
                if (getView() != null) {
                    Button btnStatus = (Button) getView().findViewById(R.id.btn_status);
                    (btnStatus).setText(getActivity().getResources().getString(R.string.reached_restaurant));
                    btnStatus.setEnabled(true);
                }
                code = Constants.Values.STATUS_ARRIVED_AT_RESTAURANT;
                break;
            }

            case Constants.Values.STATUS_ARRIVED_AT_RESTAURANT: {
                PrefUtils.setStatus(Constants.Values.STATUS_PICKUP_MATCH);
                fil.onFragmentInteraction(Constants.Values.STATUS_PICKUP_MATCH, null);
                break;
            }

            case Constants.Values.STATUS_PICKUP_MATCH: {
                PrefUtils.setStatus(Constants.Values.STATUS_PICKUP_PHOTO);
                fil.onFragmentInteraction(Constants.Values.STATUS_PICKUP_PHOTO, null);
                break;
            }

            case Constants.Values.STATUS_PICKUP_PAY: {
                PrefUtils.setStatus(Constants.Values.STATUS_REACHED_CUSTOMER_ADDRESS);
                fil.onFragmentInteraction(Constants.Values.STATUS_REACHED_CUSTOMER_ADDRESS, null);
                break;
            }

            case Constants.Values.STATUS_REACHED_CUSTOMER_ADDRESS: {
                PrefUtils.setStatus(Constants.Values.STATUS_DELIVERED);
                fil.onFragmentInteraction(Constants.Values.STATUS_DELIVERED, null);
                break;
            }

            case Constants.Values.STATUS_DELIVERED: {
                PrefUtils.setStatus(Constants.Values.STATUS_PLACEHOLDER);
                PrefUtils.deleteOrder();
                PrefUtils.deleteCurrentOrderID();
                ((BaseActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);
                fil.onFragmentInteraction(Constants.Values.STATUS_PLACEHOLDER, null);
                break;
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

        return false;
    }

    private Order getOrder() {
        if (order == null) {
            String json = PrefUtils.getOrder();
            if (json != null) {
                Log.d(TAG, json);
                order = (new Gson()).fromJson(json, Order.class);
            }
        }
        return order;
    }


    private boolean isAmount(String number) {
        try {
            Double.parseDouble(number);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
}
