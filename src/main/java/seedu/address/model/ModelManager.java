package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Customer;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final DeliveryBook deliveryBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Customer> filteredCustomers;
    private final FilteredList<Delivery> filteredDeliveries;

    /**
     * Initializes a ModelManager with the given addressBook, deliveryBook and userPrefs.
     */
    public ModelManager(ReadOnlyBook<Customer> addressBook,
                        ReadOnlyBook<Delivery> deliveryBook,
                        ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook
                + ", delivery book" + deliveryBook
                + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.deliveryBook = new DeliveryBook(deliveryBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCustomers = new FilteredList<>(this.addressBook.getList());
        filteredDeliveries = new FilteredList<>(this.deliveryBook.getList());
    }

    public ModelManager() {
        this(new AddressBook(), new DeliveryBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getDeliveryBookFilePath() {
        return userPrefs.getDeliveryBookFilePath();
    }

    @Override
    public void setDeliveryBookFilePath(Path deliveryBookFilePath) {
        requireNonNull(deliveryBookFilePath);
        userPrefs.setDeliveryBookFilePath(deliveryBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyBook<Customer> addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyBook<Customer> getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Customer customer) {
        requireNonNull(customer);
        return addressBook.hasPerson(customer);
    }

    @Override
    public void deletePerson(Customer target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Customer customer) {
        addressBook.addPerson(customer);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    @Override
    public void setPerson(Customer target, Customer editedCustomer) {
        requireAllNonNull(target, editedCustomer);

        addressBook.setPerson(target, editedCustomer);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Customer> getFilteredPersonList() {
        return filteredCustomers;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Customer> predicate) {
        requireNonNull(predicate);
        filteredCustomers.setPredicate(predicate);
    }

    //=========== DeliveryBook ================================================================================

    @Override
    public void setDeliveryBook(ReadOnlyBook<Delivery> deliveryBook) {
        this.deliveryBook.resetData(deliveryBook);
    }

    @Override
    public ReadOnlyBook<Delivery> getDeliveryBook() {
        return deliveryBook;
    }

    @Override
    public boolean hasDelivery(Delivery delivery) {
        requireNonNull(delivery);
        return deliveryBook.hasDelivery(delivery);
    }

    @Override
    public void deleteDelivery(Delivery target) {
        deliveryBook.removeDelivery(target);
    }

    @Override
    public void addDelivery(Delivery delivery) {
        deliveryBook.addDelivery(delivery);
        updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
    }

    @Override
    public void setDelivery(Delivery target, Delivery editedDelivery) {
        requireAllNonNull(target, editedDelivery);

        deliveryBook.setDelivery(target, editedDelivery);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Delivery> getFilteredDeliveryList() {
        return filteredDeliveries;
    }

    @Override
    public void updateFilteredDeliveryList(Predicate<Delivery> predicate) {
        requireNonNull(predicate);
        filteredDeliveries.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && deliveryBook.equals(otherModelManager.deliveryBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredCustomers.equals(otherModelManager.filteredCustomers)
                && filteredDeliveries.equals(otherModelManager.filteredDeliveries);
    }

}
