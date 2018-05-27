package es.niux.efc.demoapp.data.entity;

import android.support.annotation.NonNull;

import java.util.List;

import es.niux.efc.core.common.util.Check;

public class Page<T> {
    private final int page;
    private final int totalResults;
    private final int totalPages;
    private final @NonNull List<T> items;

    public Page(
            int page, int totalResults, int totalPages,
            @NonNull List<T> items
    ) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.items = Check.nonNullCol(items, true);
    }

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public @NonNull List<T> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Page{" +
                "page=" + page +
                ", totalPages=" + totalPages +
                ", items=" + items +
                '}';
    }
}
