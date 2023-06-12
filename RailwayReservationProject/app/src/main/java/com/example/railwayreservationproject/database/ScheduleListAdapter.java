package com.example.railwayreservationproject.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.railwayreservationproject.R;
import com.example.railwayreservationproject.Schedule;
import com.example.railwayreservationproject.TrainSchedule;

import java.util.List;

public class ScheduleListAdapter extends ArrayAdapter<TrainSchedule> {

    private Context mContext;
    private List<TrainSchedule> mScheduleList;

    public ScheduleListAdapter(Context context, List<TrainSchedule> scheduleList) {
        super(context, R.layout.list_item_schedule, scheduleList);
        mContext = context;
        mScheduleList = scheduleList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_schedule, parent, false);
        }

        TrainSchedule currentSchedule = mScheduleList.get(position);

        //generation ticket information
        TextView trainNumberTextView = listItem.findViewById(R.id.text_train_id);
        trainNumberTextView.setText("Train Num: " + currentSchedule.getTrainNumber());

        TextView originTextView = listItem.findViewById(R.id.text_departure);
        originTextView.setText("Departure: " + currentSchedule.getOrigin());

        TextView destinationTextView = listItem.findViewById(R.id.text_arrival);
        destinationTextView.setText("Arrival: " + currentSchedule.getDestination());

        TextView departureDateTextView = listItem.findViewById(R.id.text_departure_date);
        departureDateTextView.setText("Departure Date: " + currentSchedule.getDepartureDate());

        TextView departureTimeTextView = listItem.findViewById(R.id.text_departure_time);
        departureTimeTextView.setText("Departure Time: " + currentSchedule.getDepartureTime());
        /*
        TextView ArrivalDateTextView = listItem.findViewById(R.id.text_arrival_date);
        ArrivalDateTextView.setText("Arrival Date: " + currentSchedule.getArrivalDate());

        TextView arrivalTimeTextView = listItem.findViewById(R.id.text_arrival_time);
        arrivalTimeTextView.setText("Arrival Time: " + currentSchedule.getArrivalTime());
         */

        TextView durationTextView = listItem.findViewById(R.id.text_duration);
        durationTextView.setText("Duration: " + currentSchedule.getDuration());

        //Ticket Price
        TextView ecoPriceTextView = listItem.findViewById(R.id.text_eco_price);
        TextView busiPriceTextView = listItem.findViewById(R.id.text_busi_price);

        double ecoPrice = currentSchedule.getEconomyPrice();
        double busiPrice = currentSchedule.getBusinessPrice();

        String ecoPriceText = "Economy Price: $" + String.format("%.2f", ecoPrice);
        String busiPriceText = "Business Price: $" + String.format("%.2f", busiPrice);

        ecoPriceTextView.setText(ecoPriceText);
        busiPriceTextView.setText(busiPriceText);


        //Ticket Availability
        TextView ecoAvailabilityTextView = listItem.findViewById(R.id.eco_availability);
        TextView busiAvailabilityTextView = listItem.findViewById(R.id.busi_availability);

        int ecoAvailability = currentSchedule.getEconomySeatsAvailable();
        int busiAvailability = currentSchedule.getBusinessSeatsAvailable();

        String ecoAvailabilityText = "Economy Availability: " + ecoAvailability;
        String busiAvailabilityText = "Business Availability: " + busiAvailability;

        ecoAvailabilityTextView.setText(ecoAvailabilityText);
        busiAvailabilityTextView.setText(busiAvailabilityText);

        return listItem;
    }
}