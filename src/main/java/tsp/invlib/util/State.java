package tsp.invlib.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BooleanSupplier;

/**
 * Similar to a {@code boolean} but with three states.
 */
public enum State {

    /**
     * State describing the absence of a value.
     */
    NOT_SET,
    /**
     * State describing a {@code false} value.
     */
    FALSE,
    /**
     * State describing a {@code true} value.
     */
    TRUE;

    /**
     * Converts this tri-state back into a {@link Boolean}.
     *
     * @return the boolean representing this tri-state. {@link #NOT_SET} will be represented by {@code null}.
     */
    public @Nullable Boolean toBoolean() {
        return switch (this) {
            case TRUE -> Boolean.TRUE;
            case FALSE -> Boolean.FALSE;
            default -> null;
        };
    }

    /**
     * Converts this tri-state back into a {@code boolean}.
     *
     * <p>As the {@link #NOT_SET} state cannot be represented by the boolean type, this
     * method maps the {@link #NOT_SET} state to other passed boolean value.
     * This method may hence also be viewed as an equivalent to {@link
     * java.util.Optional#orElse(Object)}.</p>
     *
     * @param other the boolean value that should be returned if this tri-state is {@link #NOT_SET}.
     * @return the boolean representing the tri-state or the boolean passed if this state is {@link #NOT_SET}.
     */
    public boolean toBooleanOrElse(final boolean other) {
        return switch (this) {
            case TRUE -> true;
            case FALSE -> false;
            default -> other;
        };
    }

    /**
     * Converts this tri-state back into a {@code boolean}.
     *
     * <p>As the {@link #NOT_SET} state cannot be represented by the boolean type, this
     * method maps the {@link #NOT_SET} state to the suppliers result.
     * This method may hence also be viewed as an equivalent to {@link
     * java.util.Optional#orElseGet(java.util.function.Supplier)}.</p>
     *
     * @param supplier the supplier that will be executed to produce the value that should be returned if this tri-state is {@link #NOT_SET}.
     * @return the boolean representing the tri-state or the result of the passed supplier if this state is {@link #NOT_SET}.
     * @since 4.10.0
     */
    public boolean toBooleanOrElseGet(final @NotNull BooleanSupplier supplier) {
        return switch (this) {
            case TRUE -> true;
            case FALSE -> false;
            default -> supplier.getAsBoolean();
        };
    }

    /**
     * Gets a state from a {@code boolean}.
     *
     * @param value the boolean
     * @return a tri-state
     * @since 4.8.0
     */
    public static @NotNull State byBoolean(final boolean value) {
        return value ? TRUE : FALSE;
    }

    /**
     * Gets a state from a {@link Boolean}.
     *
     * @param value the boolean
     * @return a tri-state
     * @since 4.8.0
     */
    public static @NotNull State byBoolean(final @Nullable Boolean value) {
        return value == null ? NOT_SET : byBoolean(value.booleanValue());
    }

    @Override
    public String toString() {
        return switch (this) {
            case TRUE -> "true";
            case FALSE -> "false";
            case NOT_SET -> "not_set";
        };
    }

}