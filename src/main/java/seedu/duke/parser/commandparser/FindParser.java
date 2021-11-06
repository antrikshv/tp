package seedu.duke.parser.commandparser;

import seedu.duke.Duke;
import seedu.duke.commands.Command;
import seedu.duke.commands.FindCommand;
import seedu.duke.exceptions.DukeException;
import seedu.duke.exceptions.parserexceptions.InvalidItemTypeException;
import seedu.duke.items.Event;
import seedu.duke.parser.ItemType;
import seedu.duke.parser.Parser;


import static seedu.duke.parser.ItemType.EVENT;

public abstract class FindParser extends Parser {

    private static int numberOfEventsFound;
    private static String result;

    public static Command getFindCommand(String[] command, String commandDetails) {
        try {
            ItemType itemType = getItemType(commandDetails);
            if (itemType == EVENT) {
                parseFindKeyword(command);
                return new FindCommand(result);
            }
            throw new InvalidItemTypeException();
        } catch (InvalidItemTypeException e) {
            System.out.println("Please add -e to find event(s)!");
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static void parseFindKeyword(String[] command) throws DukeException {
        String keyword = extractKeywords(command);
        result = filterEvents(keyword);
        if (noEventsFound()) {
            throw new DukeException("No matching events found!");
        }
    }

    private static String extractKeywords(String[] command) throws DukeException {
        if (command.length < 3) {
            throw new DukeException("Please specify what Events you wish to find!");
        }
        StringBuilder keyword = new StringBuilder();
        for (int i = 2; i < command.length; i++) {
            keyword.append(command[i].trim());
            keyword.append(" ");
        }
        return keyword.toString().trim();
    }

    private static String filterEvents(String keyword) {
        StringBuilder result = new StringBuilder();
        numberOfEventsFound = 0;
        for (int i = 0; i < Duke.eventCatalog.size(); i++) {
            Event event = Duke.eventCatalog.get(i);
            if (event.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                result.append(i + 1).append(". ");
                result.append(event.getTitle()).append("\n");
                numberOfEventsFound++;
            }
        }
        return result.toString();
    }

    private static boolean noEventsFound() {
        return numberOfEventsFound == 0;
    }
}
