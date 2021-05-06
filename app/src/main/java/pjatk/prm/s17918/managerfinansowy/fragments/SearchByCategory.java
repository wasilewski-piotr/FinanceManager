package pjatk.prm.s17918.managerfinansowy.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pjatk.prm.s17918.managerfinansowy.R;
import pjatk.prm.s17918.managerfinansowy.adapters.RecyclerViewAdapter;
import pjatk.prm.s17918.managerfinansowy.databases.DatabaseHelper;
import pjatk.prm.s17918.managerfinansowy.listeners.ClickListener;
import pjatk.prm.s17918.managerfinansowy.models.Event;

public class SearchByCategory extends Fragment implements ClickListener {

    private int transaction_count = 0;
    private double transaction_money = 0;
    private Context context;
    private List<Event> eventList = new ArrayList<Event>();
    private TextView transMoney, transCount;
    private Spinner spinner;

    public SearchByCategory() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        return inflater.inflate(R.layout.fragment_search_by_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        transMoney = getView().findViewById(R.id.transaction_money);
        transCount = getView().findViewById(R.id.transaction_count);

        spinner = getView().findViewById(R.id.item_show_category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.add_category_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button button = getView().findViewById(R.id.button_item_category_spinner);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = spinner.getSelectedItem().toString();
                getData(view, input);
                dismissKeyboard(getActivity());
            }
        });
    }

    private void getData(View view, String input) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        eventList = databaseHelper.getAllEventsByCategory(input);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.item_events_list_by_category_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getView().getContext());
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

        getFullMoneyReport(input);
        getFullTransCountReport();

        recyclerView.setAdapter(eventAdapter);
        eventAdapter.setEventList(eventList);
    }

    private void getFullTransCountReport(){
        transaction_count = eventList.size();
        transCount.setText(Integer.toString(transaction_count));
    }

    private void getFullMoneyReport(String input){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        transaction_money= databaseHelper.getAllMoneyByCategory(input);
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

    private void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }
}