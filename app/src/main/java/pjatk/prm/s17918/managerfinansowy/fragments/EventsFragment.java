package pjatk.prm.s17918.managerfinansowy.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pjatk.prm.s17918.managerfinansowy.MainActivity;
import pjatk.prm.s17918.managerfinansowy.R;
import pjatk.prm.s17918.managerfinansowy.adapters.RecyclerViewAdapter;
import pjatk.prm.s17918.managerfinansowy.databases.DatabaseHelper;
import pjatk.prm.s17918.managerfinansowy.listeners.ClickListener;
import pjatk.prm.s17918.managerfinansowy.models.Event;
import pjatk.prm.s17918.managerfinansowy.transporters.MonthTransporter;

public class EventsFragment extends Fragment implements ClickListener {

    private int transaction_count = 0;
    private double transaction_money = 0;
    private TextView transMoney, transCount;
    private Context context;
    private List<Event> eventList = new ArrayList<Event>();

    public EventsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(MonthTransporter.getMonth());
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        transMoney = getView().findViewById(R.id.transaction_money);
        transCount = getView().findViewById(R.id.transaction_count);
        getData(view);
    }

    private void getData(View view) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        eventList = databaseHelper.getAllEvents();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.item_events_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter eventAdapter = new RecyclerViewAdapter(eventList, this, new RecyclerViewAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityChange(double change) {
                transaction_money += change;
                setColor(transaction_money, transMoney);
            }

            @Override
            public void onTransactionChange(int change) {
                transaction_count += change;
                transCount.setText(Integer.toString(transaction_count));
            }
        });

        getFullMoneyReport();
        getFullTransCountReport();

        recyclerView.setAdapter(eventAdapter);
        eventAdapter.setEventList(eventList);
    }

    private void getFullTransCountReport(){
        transaction_count = eventList.size();
        transCount.setText(Integer.toString(transaction_count));
    }

    private void getFullMoneyReport(){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        transaction_money= databaseHelper.getAllMoney();
        setColor(transaction_money, transMoney);
    }

    private void setColor(double price, TextView view){
        DecimalFormat decim = new DecimalFormat("0.00");
        String priceForm = decim.format(transaction_money);

        if(price > 0.00){
            view.setTextColor(Color.parseColor("#048838"));
            view.setText(priceForm + " zł");
        }else if(price == 0.00){
            view.setTextColor(Color.BLACK);
            view.setText(priceForm + " zł");
        }else if(price < 0.00){
            view.setTextColor(Color.RED);
            view.setText(priceForm + " zł");
        }
    }

    @Override
    public void onClick(Object data) {
    }
}