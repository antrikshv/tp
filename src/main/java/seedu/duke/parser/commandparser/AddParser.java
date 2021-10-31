package seedu.duke.parser.commandparser;

import seedu.duke.Ui;
import seedu.duke.commands.Command;
import seedu.duke.commands.HelpCommand;
import seedu.duke.exceptions.DukeException;
import seedu.duke.exceptions.parserexceptions.AttributeNotFoundException;
import seedu.duke.exceptions.parserexceptions.InvalidBudgetException;
import seedu.duke.exceptions.parserexceptions.InvalidItemTypeException;
import seedu.duke.exceptions.parserexceptions.NoCommandAttributesException;
import seedu.duke.parser.ItemAttribute;
import seedu.duke.parser.ItemType;
import seedu.duke.parser.Parser;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class AddParser extends Parser {

    private static final int INDEX_OF_TITLE = 0;
    private static final int INDEX_OF_DATETIME = 1;
    private static final int INDEX_OF_VENUE = 2;
    private static final int INDEX_OF_BUDGET = 3;

    /**
     * Parses add command details from user input to determine the type of item to be added along with its respective
     * attributes and returns the respective Add Command.
     *
     * @param commandDetails User input containing the flag of the item to be added and attributes for the command
     * @return The constructed Add Command. Null otherwise.
     */
    public Command getAddCommand(String commandDetails) {
        try {
            ItemType itemTypeToAdd = getItemType(commandDetails);
            switch (itemTypeToAdd) {
            case EVENT:
                String[] parsedAttributes = parseAddEvent(commandDetails);
                assert parsedAttributes != null : "parseAddEvent failed";
                String title = parsedAttributes[INDEX_OF_TITLE];
                LocalDateTime dateTime = convertDateTime(parsedAttributes[INDEX_OF_DATETIME]);
                String venue = parsedAttributes[INDEX_OF_VENUE];
                Double budget = convertEventBudgetToDouble(parsedAttributes[INDEX_OF_BUDGET]);
                String description = Ui.readInput().trim();
                // TODO: Create relevant constructor for AddEventCommand and return it.
                // return new AddEventCommand(title, dateTime, venue, budget);

                // TODO: Delete the HelpCommands after they have been replaced.
                return new HelpCommand();
            case TASK:
                return new HelpCommand();
            case MEMBER:
                return new HelpCommand();
            default:
                return null;
            }
        } catch (InvalidItemTypeException e) {
            System.out.println("Having some trouble understanding what exactly you're trying to add!\n"
                    + "TIP: Specify event '-e', task '-t', or member '-m' after the 'add' command.");
        } catch (InvalidBudgetException | DukeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Takes in command details for an event and returns a String[] containing the parsed attributes for that event.
     *
     * @param commandDetails String that contains command attributes and a redundant event flag.
     * @return The String[] of parsed attributes for an event. Null otherwise.
     */
    private static String[] parseAddEvent(String commandDetails) {
        try {
            String commandAttributes = getCommandAttributes(commandDetails);
            String[] parsedAttributes = new String[4];

            String title = retrieveItemAttribute(commandAttributes, ItemAttribute.TITLE);
            parsedAttributes[INDEX_OF_TITLE] = title;
            String dateTime = retrieveItemAttribute(commandAttributes, ItemAttribute.DATE);
            parsedAttributes[INDEX_OF_DATETIME] = dateTime;
            String venue = retrieveItemAttribute(commandAttributes, ItemAttribute.VENUE);
            parsedAttributes[INDEX_OF_VENUE] = venue;
            String budget = retrieveItemAttribute(commandAttributes, ItemAttribute.BUDGET);
            parsedAttributes[INDEX_OF_BUDGET] = budget;

            return parsedAttributes;
        } catch (NoCommandAttributesException e) {
            System.out.println("No details about the event you're trying to add is given!\n"
                    + "Format: add -e n/TITLE d/dd-MM-yyyy HHmm v/VENUE b/BUDGET");
        } catch (AttributeNotFoundException e) {
            String attributeType = ItemAttribute.getAttributeName(e.getItemAttribute());
            String attributeFlag = ItemAttribute.getItemFlag(e.getItemAttribute());
            System.out.println("Please add a " + attributeType + "for your event using "
                    + attributeFlag + attributeType.toUpperCase());
        }

        return null;
    }

    private static String[] parseAddTask(String commandDetails) {
        return null;
    }

    private static String[] parsedAddMember(String commandDetails) {
        return null;
    }

    /**
     * Converts a budget as a string and formats it into a double.
     *
     * @param budget The budget provided as a String
     * @return The converted budget as a double
     * @throws InvalidBudgetException If the provided budget converts into a negative number of has more than 2 decimals
     */
    private double convertEventBudgetToDouble(String budget) throws InvalidBudgetException {
        Double result = null;
        try {
            result = Double.parseDouble(budget);
            if (result < 0) {
                throw new InvalidBudgetException("Event budget needs to be a positive number.");
            }

            if (BigDecimal.valueOf(result).scale() > 2) {
                throw new InvalidBudgetException("Event budget cannot have more than 2 decimal places.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Event budget needs to be a number.");
        }

        // If conditional checks above fail internally, result will remain null. Throw exception
        if (result == null) {
            throw new InvalidBudgetException("Event budget is null!");
        }

        return result;
    }
}