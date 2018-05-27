package es.niux.efc.demoapp.data.source.network.themoviedb.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

import es.niux.efc.core.common.util.Check;

public class SearchKeywordsResponse {
    @SerializedName("page")
    public final @NonNull Integer page;
    @SerializedName("total_results")
    public final @NonNull Integer totalResults;
    @SerializedName("total_pages")
    public final @NonNull Integer totalPages;
    @SerializedName("results")
    public final @NonNull Result[] results;

    public SearchKeywordsResponse(
            @NonNull Integer page,
            @NonNull Integer totalResults,
            @NonNull Integer totalPages,
            @NonNull Result[] results
    ) {
        this.page = Check.nonNull(page);
        this.totalResults = Check.nonNull(totalResults);
        this.totalPages = Check.nonNull(totalPages);
        this.results = Check.nonNull(results);
    }

    public static class Result {
        @SerializedName("id")
        public final @NonNull Integer id;
        @SerializedName("name")
        public final @NonNull String name;

        public Result(
                @NonNull Integer id,
                @NonNull String name
        ) {
            this.id = Check.nonNull(id);
            this.name = Check.nonNull(name);
        }

        @Override
        public String toString() {
            return "Result{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SearchKeywordsResponse{" +
                "page=" + page +
                ", totalResults=" + totalResults +
                ", totalPages=" + totalPages +
                ", results=" + Arrays.toString(results) +
                '}';
    }
}
