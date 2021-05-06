package pjatk.prm.s17918.managerfinansowy.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.text.DecimalFormat;
import java.util.List;

import pjatk.prm.s17918.managerfinansowy.MainActivity;
import pjatk.prm.s17918.managerfinansowy.R;
import pjatk.prm.s17918.managerfinansowy.custom.views.charts.LineChart;
import pjatk.prm.s17918.managerfinansowy.databases.DatabaseHelper;
import pjatk.prm.s17918.managerfinansowy.models.Event;
import pjatk.prm.s17918.managerfinansowy.transporters.MonthTransporter;
import pjatk.prm.s17918.managerfinansowy.transporters.SpinnerNameTransporter;

public class MonthStatsFragment extends Fragment {

    private Context context;
    private TextView transactionCount, monthBilans, income, expense;
    private Spinner spinner;
    private CardView bilansCard, incomeCard, expenseCard;
    private LineChart lineChart;

    public MonthStatsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(R.string.null_string);
        return inflater.inflate(R.layout.fragment_month_stats, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findViews(view);
        SpinnerNameTransporter.setName(spinner.getSelectedItem().toString());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setValues();
                lineChart.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        bilansCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpinnerNameTransporter.setName(spinner.getSelectedItem().toString());
                Navigation.findNavController(getView()).navigate(R.id.bilans_fragment);
            }
        });

        incomeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpinnerNameTransporter.setName(spinner.getSelectedItem().toString());
                Navigation.findNavController(getView()).navigate(R.id.bilans_income_fragment);
            }
        });

        expenseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpinnerNameTransporter.setName(spinner.getSelectedItem().toString());
                Navigation.findNavController(getView()).navigate(R.id.bilans_expenses_fragment);
            }
        });
    }

    private void findViews(View view){
        transactionCount = getView().findViewById(R.id.transaction_count_monthStats);
        monthBilans = getView().findViewById(R.id.money_month_stats);
        income = getView().findViewById(R.id.income_month_stats);
        expense = getView().findViewById(R.id.expenses_mont_stats);
        bilansCard = getView().findViewById(R.id.bilans_card);
        incomeCard = getView().findViewById(R.id.income_card);
        expenseCard = getView().findViewById(R.id.expenses_card);
        lineChart = getView().findViewById(R.id.line_chart);

        spinner = getView().findViewById(R.id.month_spinner_selector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.month_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(MonthTransporter.getMonth()));
    }

    private void setValues(){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        List<Event> eventList = databaseHelper.getEventsByMonth(spinner.getSelectedItem().toString());
        LineChart.setEventList(eventList);

        double moneyBilans = databaseHelper.getMoneyByMonth(spinner.getSelectedItem().toString());
        double moneyIncome = databaseHelper.getMoneyIncomeByMonth(spinner.getSelectedItem().toString());
        double moneyExpense = databaseHelper.getMoneyExpenseByMonth(spinner.getSelectedItem().toString());

        setColors(moneyBilans, monthBilans);
        setColors(moneyIncome, income);
        setColors(moneyExpense, expense);

        transactionCount.setText(String.valueOf(eventList.size()));
    }

    private void setColors(double money, TextView view){
        DecimalFormat decim = new DecimalFormat("0.00");
        if(money > 0.00){
            view.setTextColor(Color.parseColor("#048838"));
            view.setText(decim.format(money) + " zł");
        }else if(money == 0.00){
            view.setTextColor(Color.BLACK);
            view.setText(decim.format(money) + " zł");
        }else if(money < 0.00){
            view.setTextColor(Color.RED);
            view.setText(decim.format(money) + " zł");
        }
    }
}