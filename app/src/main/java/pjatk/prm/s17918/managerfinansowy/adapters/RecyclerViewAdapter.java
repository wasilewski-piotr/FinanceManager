package pjatk.prm.s17918.managerfinansowy.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import pjatk.prm.s17918.managerfinansowy.R;
import pjatk.prm.s17918.managerfinansowy.databases.DatabaseHelper;
import pjatk.prm.s17918.managerfinansowy.listeners.ClickListener;
import pjatk.prm.s17918.managerfinansowy.models.Event;
import pjatk.prm.s17918.managerfinansowy.transporters.EventTransporter;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder> {

    private Context context;
    private OnQuantityChangeListener listener;
    private List<Event> eventList;
    private ClickListener clickListener;

    public RecyclerViewAdapter(List<Event> eventList, ClickListener clickListener, OnQuantityChangeListener listener) {
        this.eventList = eventList;
        this.clickListener = clickListener;
        this.listener = listener;
    }

    public void setEventList(List<Event> eventList){
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        Event event = eventList.get(position);

        double price_double = Double.parseDouble(event.getPrice());

        holder.event_name.setText(event.getPlace_name());
        holder.event_date.setText(event.getDate());

        DecimalFormat decim = new DecimalFormat("0.00");
        String price = decim.format(price_double);

        if(price_double < 0.00){
            holder.event_price.setTextColor(Color.RED);
            holder.event_price.setText(price + " zł");
        }else if(price_double == 0.00){
            holder.event_price.setTextColor(Color.BLACK);
            holder.event_price.setText(price + " zł");
        }else if(price_double > 0.00){
            holder.event_price.setTextColor(Color.parseColor("#048838"));
            holder.event_price.setText(price + " zł");
        }

        holder.event_category.setText(event.getCategory());
        holder.event_date.setBackgroundResource(R.color.date);

        if(event.getCategory().equals("Inne wydatki")){
            holder.event_category.setBackgroundResource(R.color.category_1);
        }else if(event.getCategory().equals("Inne opłaty i rachunki")){
            holder.event_category.setBackgroundResource(R.color.category_2);
        }else if(event.getCategory().equals("Przelew")){
            holder.event_category.setBackgroundResource(R.color.category_3);
        }else if(event.getCategory().equals("Jedzenie")){
            holder.event_category.setBackgroundResource(R.color.category_4);
        }else if(event.getCategory().equals("Transport")){
            holder.event_category.setBackgroundResource(R.color.category_5);
        }else if(event.getCategory().equals("Mieszkanie")){
            holder.event_category.setBackgroundResource(R.color.category_6);
        }else if(event.getCategory().equals("Zdrowie i higiena")){
            holder.event_category.setBackgroundResource(R.color.category_7);
        }else if(event.getCategory().equals("Ubranie")){
            holder.event_category.setBackgroundResource(R.color.category_8);
        }else if(event.getCategory().equals("Relaks")){
            holder.event_category.setBackgroundResource(R.color.category_9);
        }
    }

    @Override
    public int getItemCount() {
        if(eventList != null){
            return eventList.size();
        }
        return 0;
    }

    public interface OnQuantityChangeListener{
        void onQuantityChange( double change );
        void onTransactionChange ( int change );
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        public TextView event_name, event_price, event_date, event_category, trans_money, trans_count;

        public myViewHolder(@NonNull final View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_name);
            event_date = itemView.findViewById(R.id.event_date);
            event_price = itemView.findViewById(R.id.event_price);
            trans_money = itemView.findViewById(R.id.transaction_money);
            trans_count = itemView.findViewById(R.id.transaction_count);
            event_category = itemView.findViewById(R.id.event_category);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    clickListener.onClick(getAdapterPosition());

                    EventTransporter.setEvent(eventList.get(getAdapterPosition()));

                    NavController controller = Navigation.findNavController(view);
                    controller.navigate(R.id.show_fragment);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view) {
                    clickListener.onClick(getAdapterPosition());
                    context = view.getContext();

                    AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    alert.setTitle("Uwaga !");
                    alert.setMessage("Czy na pewno chcesz usunąć ten wpis ?");
                    alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            DatabaseHelper databaseHelper = new DatabaseHelper(context);
                            Event event = eventList.get(getAdapterPosition());
                            eventList.remove(event);
                            databaseHelper.deleteOneEvent(event);

                            double difference = Double.parseDouble(event.getPrice());
                            listener.onQuantityChange(-difference);
                            listener.onTransactionChange(-1);

                            notifyItemRemoved(getAdapterPosition());
                            notifyDataSetChanged();

                            dialog.dismiss();

                        }
                    });
                    alert.setNegativeButton("Nie", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    alert.show();
                    return true;
                }
            });
        }
    }

}
