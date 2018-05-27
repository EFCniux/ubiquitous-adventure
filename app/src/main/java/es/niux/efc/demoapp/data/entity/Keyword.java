package es.niux.efc.demoapp.data.entity;

import android.support.annotation.NonNull;

import es.niux.efc.core.common.util.Check;

public class Keyword {
    private final int id;
    private final @NonNull String name;

    public Keyword(
            int id,
            @NonNull String name
    ) {
        this.id = id;
        this.name = Check.nonNull(name);
    }

    public int getId() {
        return id;
    }

    public @NonNull String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Keyword)) return false;

        Keyword keyword = (Keyword) o;

        return id == keyword.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
