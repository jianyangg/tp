package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.VIPCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class VIPCommandParser implements Parser<VIPCommand> {

    public VIPCommand parse(String args) throws ParseException {
        // vip command format is just vip INDEX
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, VIPCommand.MESSAGE_USAGE), pe);
        }

        return new VIPCommand(index);
    }
}
