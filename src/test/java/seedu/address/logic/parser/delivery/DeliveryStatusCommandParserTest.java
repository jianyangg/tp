package seedu.address.logic.parser.delivery;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.delivery.DeliveryStatusCommand;
import seedu.address.model.delivery.DeliveryStatus;

public class DeliveryStatusCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliveryStatusCommand.MESSAGE_USAGE);

    private DeliveryStatusCommandParser parser = new DeliveryStatusCommandParser();

    @Test
    public void parse_allFields_success() {
        // CREATED
        DeliveryStatusCommand deliveryStatusCommand = new DeliveryStatusCommand(1, DeliveryStatus.CREATED);
        assertParseSuccess(parser, "1 " + VALID_STATUS_CREATED, deliveryStatusCommand);

        // SHIPPED
        deliveryStatusCommand = new DeliveryStatusCommand(1, DeliveryStatus.SHIPPED);

        assertParseSuccess(parser, VALID_STATUS_SHIPPED_VIEW + " 1", deliveryStatusCommand);

        // COMPLETED
        deliveryStatusCommand = new DeliveryStatusCommand(1, DeliveryStatus.COMPLETED);
        assertParseSuccess(parser, VALID_STATUS_COMPLETED_VIEW + " 1", deliveryStatusCommand);

        // CANCELLED
        deliveryStatusCommand = new DeliveryStatusCommand(1, DeliveryStatus.CANCELLED);
        assertParseSuccess(parser, VALID_STATUS_CANCELLED_VIEW + " 1", deliveryStatusCommand);

    }

    @Test
    public void parse_allFieldsExtraSpaceBetween_success() {
        DeliveryStatusCommand deliveryStatusCommand = new DeliveryStatusCommand(1, DeliveryStatus.CREATED);
        assertParseSuccess(parser, "1    " + VALID_STATUS_CREATED, deliveryStatusCommand);
    }

    @Test
    public void parse_allFieldsExtraSpaceBefore_success() {
        DeliveryStatusCommand deliveryStatusCommand = new DeliveryStatusCommand(1, DeliveryStatus.CREATED);
        assertParseSuccess(parser, " 1 " + VALID_STATUS_CREATED, deliveryStatusCommand);
    }

    @Test
    public void parse_allFieldsExtraSpaceAfter_success() {
        DeliveryStatusCommand deliveryStatusCommand = new DeliveryStatusCommand(1, DeliveryStatus.CREATED);
        assertParseSuccess(parser, "1 " + VALID_STATUS_CREATED + " ", deliveryStatusCommand);
    }

    @Test
    public void parse_statusLowerCase_success() {
        DeliveryStatusCommand deliveryStatusCommand = new DeliveryStatusCommand(1, DeliveryStatus.CREATED);

        assertParseSuccess(parser, VALID_STATUS_CREATED_VIEW.toLowerCase() + " 1", deliveryStatusCommand);

    }

    @Test
    public void parse_statusUpperCase_success() {
        DeliveryStatusCommand deliveryStatusCommand = new DeliveryStatusCommand(1, DeliveryStatus.CREATED);

        assertParseSuccess(parser, VALID_STATUS_CREATED_VIEW.toUpperCase() + " 1", deliveryStatusCommand);

    }

    @Test
    public void parse_invalidStatus_failure() {
        assertParseFailure(parser, "1 " + INVALID_STATUS, DeliveryStatus.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidAllStatus_failure() {
        assertParseFailure(parser, "1 ALL", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingStatus_failure() {
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_extraWordBeforeStatus_failure() {
        assertParseFailure(parser, "1 word " + INVALID_STATUS, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_negativeId_failure() {
        assertParseFailure(parser,
            INVALID_ID_NEGATIVE + " " + INVALID_STATUS, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_nanId_failure() {
        assertParseFailure(parser,
            INVALID_ID_NAN + " " + INVALID_STATUS, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_extraNumberAfterId_failure() {
        assertParseFailure(parser,
            "1 1 " + INVALID_STATUS, MESSAGE_INVALID_FORMAT);
    }
}
