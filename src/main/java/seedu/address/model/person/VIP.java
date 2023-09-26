package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public class VIP {
    public final boolean isVip;

    /**
     * Constructs a {@code VIP}.
     * 
     * @param isVip
     */
    public VIP(boolean isVip) {
        requireNonNull(isVip);

        this.isVip = isVip;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VIP)) {
            return false;
        }

        VIP otherVIP = (VIP) other;
        return isVip == otherVIP.isVip;
    }

    @Override
    public int hashCode() {
        return isVip ? 1 : 0;
    }

    public boolean isVip() {
        return isVip;
    }

    @Override
    public String toString() {
        return isVip ? "VIP" : "Non-VIP";
    }
}
