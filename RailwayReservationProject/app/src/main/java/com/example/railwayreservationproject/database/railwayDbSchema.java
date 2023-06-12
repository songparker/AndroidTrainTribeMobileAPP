package com.example.railwayreservationproject.database;

import com.example.railwayreservationproject.Client;
import com.example.railwayreservationproject.Train;

public class railwayDbSchema {

    public static final class ClientTable {
        public static final String NAME = "clients";

        public static final class Cols {
            public static final String CLIENT_ID = "user_id";
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
            public static final String FIRSTNAME = "first_name";
            public static final String LASTNAME = "last_name";
            public static final String MOBILE = "mobile";
        }
    }

    public static final class TrainTable {
        public static final String NAME = "trains";

        public static final class Cols {
            public static final String TRAIN_ID = "trainNumber";
            public static final String ORIGIN = "sourceStation";
            public static final String DESTINATION = "destinationStation";
            public static final String ECONOMY_PRICE = "economyPrice";
            public static final String BUSINESS_PRICE = "businessPrice";
        }
    }

    public static final class ScheduleTable {
        public static final String NAME = "schedules";

        public static final class Cols {
            public static final String SCHEDULE_ID = "scheduleID";
            public static final String TRAIN_ID = "trainNumber";
            public static final String DEPARTURE_DATE = "departureDate";
            public static final String DEPARTURE_TIME = "departureTime";
            public static final String ARRIVAL_DATE = "arrivalDate";
            public static final String ARRIVAL_TIME = "arrivalTime";
            public static final String ECO_AVAILABILITY = "ecoAvailability";
            public static final String BUSI_AVAILABILITY = "busiAvailability";
            public static final String DURATION = "duration";
        }
    }

    public static final class OrderTable {
        public static final String NAME = "orders";

        public static final class Cols {
            public static final String ORDER_ID = "orderId";
            public static final String CLIENT_ID = "user_id";
            public static final String TRAIN_ID = "trainNum";
            public static final String SCHEDULE_ID = "scheduleID";
            public static final String ECO_NUMBER = "ecoNumber";
            public static final String BUSI_NUMBER = "busiNumber";
            public static final String TOTAL_PRICE = "totalPrice";
        }
    }
}