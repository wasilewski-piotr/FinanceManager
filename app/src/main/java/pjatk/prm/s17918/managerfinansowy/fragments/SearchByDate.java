package pjatk.prm.s17918.managerfinansowy.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pjatk.prm.s17918.managerfinansowy.R;
import pjatk.prm.s17918.managerfinansowy.adapters.RecyclerViewAdapter;
import pjatk.prm.s17918.managerfinansowy.databases.DatabaseHelper;
import pjatk.prm.s17918.managerfinansowy.listeners.ClickListener;
import pjatk.prm.s17918.managerfinansowy.models.Event;

public class SearchByDate extends Fragment implements ClickListener {

    private int transaction_count = 0;
    private double transaction_money = 0;
    private Context context;
    private List<Event> eventList = new ArrayList<Event>();
    private TextView transMoney, transCount;
    private EditText dateInput;

    public SearchByDate() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        return inflater.inflate(R.layout.fragment_search_by_date, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        transMoney = getView().findViewById(R.id.transaction_money);
        transCount = getView().findViewById(R.id.transaction_count);
        dateInput = getView().findViewById(R.id.item_date_input);
        setInputTypes();

        Button button = getView().findViewById(R.id.button_find_by_date);

        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(dateInput);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = dateInput.getText().toString();
                getData(view, input);
                dismissKeyboard(getActivity());
            }
        });
    }

    private void showDateDialog(final EditText add_date) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                add_date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new DatePickerDialog(context, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setInputTypes(){
        dateInput.setInputType(InputType.TYPE_NULL);
    }

    private void getData(View view, String input) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        eventList = databaseHelper.getAllEventsByDate(input);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.item_events_list_by_date_recycler);
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
        transaction_money= databaseHelper.getAllMoneyByDate(input);
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