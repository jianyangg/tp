package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import seedu.address.model.Model;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

public class VIPCommand extends Command {
    public static final String COMMAND_WORD = "vip";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the person identified as VIP "
            + "by the index number used in the displayed person list. "
            + "Existing VIP status will be toggled as a result.\n"
            + "Parameter: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_VIP_PERSON_SUCCESS = "Toggled VIP for Person: %1$s";

    private final Index index;

    /**
     * @param index of the person in the filtered person list to toggle VIP status
     */
    public VIPCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        // copy everything, but toggle VIP status
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.toggleVip());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_VIP_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

}
