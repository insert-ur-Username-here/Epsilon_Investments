package Entities;

/* Class Overview:
A tradable stock: what is being charted, searched for, or held in a portfolio. Immutable.
Identity is the symbol: two Securities with the same symbol are equal.
 */
public final class Security {
    private final String symbol;
    private final String name;

    public Security(String symbol, String name) {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol is required");
        }

        this.symbol = symbol.trim().toUpperCase();   // same normalization as literally every other class
        this.name = (name == null || name.isBlank()) ? this.symbol : name.trim();
    }

    /** Getters */
    public String getSymbol() { return symbol; }
    public String getName()   { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Security)) return false;
        return symbol.equals(((Security) o).symbol);
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }

    @Override
    public String toString() {
        return symbol + " - " + name;
    }
}