package pjatk.prm.s17918.managerfinansowy.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pjatk.prm.s17918.managerfinansowy.models.Event;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String EVENTS_TABLE = "EVENTS_TABLE";
    private static final String COLUMN_EVENT_PRICE = "EVENT_PRICE";
    private static final String COLUMN_EVENT_NAME = "EVENT_NAME";
    private static final String COLUMN_EVENT_DATE = "EVENT_DATE";
    private static final String COLUMN_EVENT_CATEGORY = "EVENT_CATEGORY";
    private static final String COLUMN_ID = "ID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "events.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + EVENTS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EVENT_PRICE + " TEXT, " + COLUMN_EVENT_NAME + " TEXT, " + COLUMN_EVENT_DATE + " TEXT, " + COLUMN_EVENT_CATEGORY + " TEXT)";

        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public boolean addOneEvent(Event event){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_EVENT_NAME, event.getPlace_name());
        contentValues.put(COLUMN_EVENT_PRICE, event.getPrice());
        contentValues.put(COLUMN_EVENT_DATE, event.getDate());
        contentValues.put(COLUMN_EVENT_CATEGORY, event.getCategory());

        long insert = database.insert(EVENTS_TABLE, null, contentValues);

        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateOneEvent(Event event, String id){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID, event.getId());
        contentValues.put(COLUMN_EVENT_NAME, event.getPlace_name());
        contentValues.put(COLUMN_EVENT_PRICE, event.getPrice());
        contentValues.put(COLUMN_EVENT_DATE, event.getDate());
        contentValues.put(COLUMN_EVENT_CATEGORY, event.getCategory());

        long update = database.update(EVENTS_TABLE, contentValues, "ID = ?", new String[] {id});
        if(update == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean deleteOneEvent(Event event){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "DELETE FROM " + EVENTS_TABLE + " WHERE " + COLUMN_ID + " = " + event.getId();

        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }

    public double getAllMoney(){
        double returnMoney = 0;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MM");
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        String month = month_date.format(cal.getTime());
        String year = year_date.format(cal.getTime());

        String query = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_EVENT_DATE + " LIKE '" + year + "-" + month + "-%' ORDER BY " + COLUMN_EVENT_DATE +" DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String price = cursor.getString(1);
                String name = cursor.getString(2);
                String date =cursor.getString(3);
                String category = cursor.getString(4);

                Event event = new Event(id, price, name, date, category);
                returnMoney += Double.parseDouble(event.getPrice());

            }while (cursor.moveToNext());
        }else{
            //empty list
        }

        return returnMoney;
    }

    public double getAllMoneyByName(String input){
        double returnMoney = 0;

        String query = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_EVENT_NAME + " LIKE '%" + input + "%' ORDER BY " + COLUMN_EVENT_DATE +" DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String price = cursor.getString(1);
                String name = cursor.getString(2);
                String date =cursor.getString(3);
                String category = cursor.getString(4);

                Event event = new Event(id, price, name, date, category);
                returnMoney += Double.parseDouble(event.getPrice());

            }while (cursor.moveToNext());
        }else{
            //empty list
        }

        return returnMoney;
    }

    public double getAllMoneyByCategory(String input){
        double returnMoney = 0;

        String query = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_EVENT_CATEGORY + " LIKE '%" + input + "%' ORDER BY " + COLUMN_EVENT_DATE +" DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String price = cursor.getString(1);
                String name = cursor.getString(2);
                String date =cursor.getString(3);
                String category = cursor.getString(4);

                Event event = new Event(id, price, name, date, category);
                returnMoney += Double.parseDouble(event.getPrice());

            }while (cursor.moveToNext());
        }else{
            //empty list
        }

        return returnMoney;
    }

    public double getAllMoneyByDate(String input){
        double returnMoney = 0;

        String query = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_EVENT_DATE + " LIKE '%" + input + "%' ORDER BY " + COLUMN_EVENT_DATE +" DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String price = cursor.getString(1);
                String name = cursor.getString(2);
                String date =cursor.getString(3);
                String category = cursor.getString(4);

                Event event = new Event(id, price, name, date, category);
                returnMoney += Double.parseDouble(event.getPrice());

            }while (cursor.moveToNext());
        }else{
            //empty list
        }

        return returnMoney;
    }

    public double getMoneyByMonth(String month){
        double returnMoney = 0;
        String decodedMonth = decodeMonth(month);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        String year = year_date.format(cal.getTime());

        String query = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_EVENT_DATE + " LIKE '" + year + "-" + decodedMonth + "-%' ORDER BY " + COLUMN_EVENT_DATE +" DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String price = cursor.getString(1);
                String name = cursor.getString(2);
                String date =cursor.getString(3);
                String category = cursor.getString(4);

                Event event = new Event(id, price, name, date, category);
                returnMoney += Double.parseDouble(event.getPrice());

            }while (cursor.moveToNext());
        }else{
            //empty list
        }

        return returnMoney;
    }

    public double getMoneyIncomeByMonth(String month){
        double returnMoney = 0;
        String decodedMonth = decodeMonth(month);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        String year = year_date.format(cal.getTime());

        String query = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_EVENT_DATE + " LIKE '" + year + "-" + decodedMonth + "-%' ORDER BY " + COLUMN_EVENT_DATE +" DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String price = cursor.getString(1);
                String name = cursor.getString(2);
                String date =cursor.getString(3);
                String category = cursor.getString(4);

                Event event = new Event(id, price, name, date, category);
                if(Double.parseDouble(event.getPrice()) > 0.00){
                    returnMoney += Double.parseDouble(event.getPrice());
                }
            }while (cursor.moveToNext());
        }else{
            //empty list
        }

        return returnMoney;
    }

    public double getMoneyExpenseByMonth(String month){
        double returnMoney = 0;
        String decodedMonth = decodeMonth(month);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        String year = year_date.format(cal.getTime());

        String query = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_EVENT_DATE + " LIKE '" + year + "-" + decodedMonth + "-%' ORDER BY " + COLUMN_EVENT_DATE +" DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String price = cursor.getString(1);
                String name = cursor.getString(2);
                String date =cursor.getString(3);
                String category = cursor.getString(4);

                Event event = new Event(id, price, name, date, category);
                if(Double.parseDouble(event.getPrice()) < 0.00){
                    returnMoney += Double.parseDouble(event.getPrice());
                }
            }while (cursor.moveToNext());
        }else{
            //empty list
        }

        return returnMoney;
    }

    public List<Event> getEventsByMonth(String month){
        String decodedMonth = decodeMonth(month);
        List<Event> returnList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        String year = year_date.format(cal.getTime());

        String query = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_EVENT_DATE + " LIKE '" + year + "-" + decodedMonth + "-%' ORDER BY " + COLUMN_EVENT_DATE +" DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String price = cursor.getString(1);
                String name = cursor.getString(2);
                String date =cursor.getString(3);
                String category = cursor.getString(4);

                Event event = new Event(id, price, name, date, category);
                returnList.add(event);

            }while (cursor.moveToNext());
        }else{
            //empty list
        }
        return returnList;
    }

    public List<Event> getAllEvents(){
        List<Event> returnList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MM");
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        String month = month_date.format(cal.getTime());
        String year = year_date.format(cal.getTime());

        String query = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_EVENT_DATE + " LIKE '" + year + "-" + month + "-%' ORDER BY " + COLUMN_EVENT_DATE +" DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String price = cursor.getString(1);
                String name = cursor.getString(2);
                String date =cursor.getString(3);
                String category = cursor.getString(4);

                Event event = new Event(id, price, name, date, category);
                returnList.add(event);

            }while (cursor.moveToNext());
        }else{
            //empty list
        }
        return returnList;
    }

    public List<Event> getAllEventsByName(String query_name){
        List<Event> returnList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MM");
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        String month = month_date.format(cal.getTime());
        String year = year_date.format(cal.getTime());

        String query = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_EVENT_NAME + " LIKE '%" + query_name + "%' ORDER BY " + COLUMN_EVENT_DATE +" DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String price = cursor.getString(1);
                String name = cursor.getString(2);
                String date =cursor.getString(3);
                String category = cursor.getString(4);

                Event event = new Event(id, price, name, date, category);
                returnList.add(event);

            }while (cursor.moveToNext());
        }else{
            //empty list
        }
        return returnList;
    }

    public List<Event> getAllEventsByCategory(String query_name){
        List<Event> returnList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MM");
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        String month = month_date.format(cal.getTime());
        String year = year_date.format(cal.getTime());

        String query = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_EVENT_CATEGORY + " LIKE '%" + query_name + "%' ORDER BY " + COLUMN_EVENT_DATE +" DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String price = cursor.getString(1);
                String name = cursor.getString(2);
                String date =cursor.getString(3);
                String category = cursor.getString(4);

                Event event = new Event(id, price, name, date, category);
                returnList.add(event);

            }while (cursor.moveToNext());
        }else{
            //empty list
        }
        return returnList;
    }

    public List<Event> getAllEventsByDate(String query_name){
        List<Event> returnList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MM");
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        String month = month_date.format(cal.getTime());
        String year = year_date.format(cal.getTime());

        String query = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_EVENT_DATE + " LIKE '%" + query_name + "%' ORDER BY " + COLUMN_EVENT_DATE +" DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String price = cursor.getString(1);
                String name = cursor.getString(2);
                String date =cursor.getString(3);
                String category = cursor.getString(4);

                Event event = new Event(id, price, name, date, category);
                returnList.add(event);

            }while (cursor.moveToNext());
        }else{
            //empty list
        }
        return returnList;
    }

    public List<Event> getAllEventsIncome(){
        List<Event> returnList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MM");
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        String month = month_date.format(cal.getTime());
        String year = year_date.format(cal.getTime());

        String query = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_EVENT_PRICE + " > 0.00 AND " + COLUMN_EVENT_DATE + " LIKE '" + year + "-" + month + "-%'  ORDER BY " + COLUMN_EVENT_DATE +" DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String price = cursor.getString(1);
                String name = cursor.getString(2);
                String date =cursor.getString(3);
                String category = cursor.getString(4);

                Event event = new Event(id, price, name, date, category);
                returnList.add(event);

            }while (cursor.moveToNext());
        }else{
            //empty list
        }
        return returnList;
    }

    public List<Event> getAllEventsExpenses(){
        List<Event> returnList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MM");
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        String month = month_date.format(cal.getTime());
        String year = year_date.format(cal.getTime());

        String query = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_EVENT_PRICE + " < 0.00 AND " + COLUMN_EVENT_DATE + " LIKE '" + year + "-" + month + "-%'  ORDER BY " + COLUMN_EVENT_DATE +" DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String price = cursor.getString(1);
                String name = cursor.getString(2);
                String date =cursor.getString(3);
                String category = cursor.getString(4);

                Event event = new Event(id, price, name, date, category);
                returnList.add(event);

            }while (cursor.moveToNext());
        }else{
            //empty list
        }
        return returnList;
    }

    public List<Event> getAllEventsIncomeByMonth(String month){
        String decodedMonth = decodeMonth(month);
        List<Event> returnList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        String year = year_date.format(cal.getTime());

        String query = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_EVENT_PRICE + " > 0.00 AND " + COLUMN_EVENT_DATE + " LIKE '" + year + "-" + decodedMonth + "-%'  ORDER BY " + COLUMN_EVENT_DATE +" DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String price = cursor.getString(1);
                String name = cursor.getString(2);
                String date =cursor.getString(3);
                String category = cursor.getString(4);

                Event event = new Event(id, price, name, date, category);
                returnList.add(event);

            }while (cursor.moveToNext());
        }else{
            //empty list
        }
        return returnList;
    }

    public List<Event> getAllEventsExpensesByMonth(String month){
        String decodedMonth = decodeMonth(month);
        List<Event> returnList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        String year = year_date.format(cal.getTime());

        String query = "SELECT * FROM " + EVENTS_TABLE + " WHERE " + COLUMN_EVENT_PRICE + " < 0.00 AND " + COLUMN_EVENT_DATE + " LIKE '" + year + "-" + decodedMonth + "-%'  ORDER BY " + COLUMN_EVENT_DATE +" DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String price = cursor.getString(1);
                String name = cursor.getString(2);
                String date =cursor.getString(3);
                String category = cursor.getString(4);

                Event event = new Event(id, price, name, date, category);
                returnList.add(event);

            }while (cursor.moveToNext());
        }else{
            //empty list
        }
        return returnList;
    }

    private String decodeMonth(String month){
        switch (month) {
            case "Styczeń":
                return "01";
            case "Luty":
                return "02";
            case "Marzec":
                return "03";
            case "Kwiecień":
                return "04";
            case "Maj":
                return "05";
            case "Czerwiec":
                return "06";
            case "Lipiec":
                return "07";
            case "Sierpień":
                return "08";
            case "Wrzesień":
                return "09";
            case "Październik":
                return "10";
            case "Listopad":
                return "11";
            case "Grudzień":
                return "12";
        }
        return "error";
    }
}
