package seedu.duke.storage;

import seedu.duke.items.Event;

import java.util.ArrayList;
import java.util.List;

public class EventDecoder {
    public static final int INDEX_OF_TITLE = 0;
    public static final int INDEX_OF_DATETIME = 1;
    public static final int INDEX_OF_VENUE = 2;
    public static final int INDEX_OF_DESCRIPTION = 3;
    public static final int INDEX_OF_BUDGET = 4;

    public static ArrayList<Event> decodeEventList(List<String> encodedEventList) {
        ArrayList<Event> decodedEvents = new ArrayList<>();

        for (String encodedEvent : encodedEventList) {

        }

        return decodedEvents;
    }

    public static Event decodeEventFromString(String encodedEvent) {
        String[] eventDetails = encodedEvent.trim().split("\\s*\\|\\s*");
        String eventTitle = eventDetails[INDEX_OF_TITLE];
        String eventDateTime = eventDetails[INDEX_OF_DATETIME];
        String eventVenue = eventDetails[INDEX_OF_VENUE];
        String eventDescription = eventDetails[INDEX_OF_DESCRIPTION];
        String eventBudget = eventDetails[INDEX_OF_BUDGET];

        return new Event(eventTitle, eventDateTime, eventVenue, eventDescription, eventBudget);
    }
}
