package org.ecos.logic;

import java.util.List;
import java.util.Objects;

public class Film {
    private final String title;
    private final List<Director> directorCollection;

    public Film(String title, List<Director> directorCollection) {
        this.title = title;
        this.directorCollection = directorCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        if (!Objects.equals(title, film.title)) return false;
        return Objects.equals(directorCollection, film.directorCollection);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (directorCollection != null ? directorCollection.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", directorCollection=" + directorCollection +
                '}';
    }
}
