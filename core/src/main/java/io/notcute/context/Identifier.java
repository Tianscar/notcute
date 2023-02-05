package io.notcute.context;

import java.util.Objects;

public class Identifier {

    private final String tag;
    private final String name;

    public Identifier(String tag, String name) {
        this.tag = Objects.requireNonNull(tag);
        this.name = Objects.requireNonNull(name);
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Identifier that = (Identifier) o;

        if (!getTag().equals(that.getTag())) return false;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        int result = getTag().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }

}
