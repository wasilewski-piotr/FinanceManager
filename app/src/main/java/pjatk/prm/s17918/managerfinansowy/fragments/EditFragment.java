package pjatk.prm.s17918.managerfinansowy.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import pjatk.prm.s17918.managerfinansowy.MainActivity;
import pjatk.prm.s17918.managerfinansowy.R;
import pjatk.prm.s17918.managerfinansowy.databases.DatabaseHelper;
import pjatk.prm.s17918.managerfinansowy.models.Event;
import pjatk.prm.s17918.managerfinansowy.transporters.EventTransporter;

public class EditFragment extends Fragment {

    private Context context;
    private EditText edit_name, edit_price, edit_date;
    private Spinner spinner;
    private Button edit_data;

    public EditFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(null);
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        findViews(view);
        setInputTypes();

        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(edit_date);
            }
        });

        edit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event event = EventTransporter.getEvent();
                String old_name, new_name, old_price, new_price, old_date, new_date;

                try {
                    old_name = event.getPlace_name();
                    new_name = edit_name.getText().toString();

                    old_price = event.getPrice();
                    new_price = edit_price.getText().toString();

                    old_date = event.getDate();
                    new_date = edit_date.getText().toString();

                    if(new_name.equals(null) || new_name.equals("")){
                        event.setPlace_name(old_name);
                    }else{
                        event.setPlace_name(new_name);
                    }

                    if(new_price.equals(null) || new_price.equals("")){
                        event.setPrice(old_price);
                    }else{
                        event.setPrice(new_price);
                    }

                    if(new_date.equals(null) || new_date.equals("")){
                        event.setDate(old_date);
                    }else{
                        event.setDate(new_date);
                    }

                    event.setCategory(spinner.getSelectedItem().toString());

                    NavController controller = Navigation.findNavController(view);
                    controller.popBackStack(R.id.events_fragment, false);

                }catch (Exception e){
                    e.printStackTrace();
                }
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                databaseHelper.updateOneEvent(event, String.valueOf(event.getId()));
                dismissKeyboard(getActivity());
            }
        });
    }

    private void findViews(View view){
        edit_name = getView().findViewById(R.id.edit_name);
        edit_price = getView().findViewById(R.id.edit_price);
        edit_date = getView().findViewById(R.id.edit_date);
        edit_data = getView().findViewById(R.id.edit_button);

        Event event = EventTransporter.getEvent();
        edit_name.setHint(event.getPlace_name());
        edit_price.setHint(event.getPrice());
        edit_date.setHint(event.getDate());

        spinner = getView().findViewById(R.id.edit_category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.add_category_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(event.getCategory()));
    }

    private void setInputTypes(){
        edit_date.setInputType(InputType.TYPE_NULL);
        edit_price.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        edit_name.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    private void showDateDialog(final EditText edit_date) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        edit_date.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(context, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        };

        new DatePickerDialog(context, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }
}