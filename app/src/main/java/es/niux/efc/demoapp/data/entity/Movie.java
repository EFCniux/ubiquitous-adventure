package es.niux.efc.demoapp.data.entity;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.Date;

import es.niux.efc.core.common.util.Check;

public class Movie {
    private final int id;
    private final @NonNull String title;
    private final @NonNull String overview;
    private final @NonNull Uri picture;
    private final @NonNull Date dateRelease;

    public Movie(
            int id,
            @NonNull String title, @NonNull String overview,
            @NonNull Uri picture, @NonNull Date dateRelease
    ) {
        this.id = id;
        this.title = Check.nonNull(title);
        this.overview = Check.nonNull(overview);
        this.picture = Check.nonNull(picture);
        this.dateRelease = Check.nonNull(dateRelease);
    }

    public int getId() {
        return id;
    }

    public @NonNull String getTitle() {
        return title;
    }

    public @NonNull String getOverview() {
        return overview;
    }

    public @NonNull Uri getPicture() {
        return picture;
    }

    public @NonNull Date getDateRelease() {
        return dateRelease;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        return id == movie.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
