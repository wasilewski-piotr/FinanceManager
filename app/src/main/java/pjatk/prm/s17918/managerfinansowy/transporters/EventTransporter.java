package pjatk.prm.s17918.managerfinansowy.transporters;

import pjatk.prm.s17918.managerfinansowy.models.Event;

public class EventTransporter {
    private static Event event;

    public static Event getEvent() {
        return event;
    }

    public static void setEvent(Event event) {
        EventTransporter.event = event;
    }
}
