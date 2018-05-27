package es.niux.efc.demoapp.data.source.network.themoviedb.response;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Date;

import es.niux.efc.core.common.util.Check;

public class DiscoverMoviesResponse {
    @SerializedName("page")
    public final @NonNull Integer page;
    @SerializedName("total_results")
    public final @NonNull Integer totalResults;
    @SerializedName("total_pages")
    public final @NonNull Integer totalPages;
    @SerializedName("results")
    public final @NonNull Result[] results;

    public DiscoverMoviesResponse(
            @NonNull Integer page,
            @NonNull Integer totalResults,
            @NonNull Integer totalPages,
            @NonNull Result[] results
    ) {
        this.page = Check.nonNull(page);
        this.totalResults = Check.nonNull(totalResults);
        this.totalPages = Check.nonNull(totalPages);
        this.results = Check.nonNullArr(results, true);
    }

    public static class Result {
        @SerializedName("id")
        public final @NonNull Integer id;
        @SerializedName("title")
        public final @NonNull String title;
        @SerializedName("overview")
        public final @NonNull String overview;
        @SerializedName("poster_path")
        public final @Nullable String picturePath;
        @SerializedName("release_date")
        public final @NonNull Date dateRelease;

        public Result(
                @NonNull Integer id,
                @NonNull String title,
                @NonNull String overview,
                @Nullable String picturePath,
                @NonNull Date dateRelease
        ) {
            this.id = Check.nonNull(id);
            this.title = Check.nonNull(title);
            this.overview = Check.nonNull(overview);
            this.picturePath = picturePath;
            this.dateRelease = Check.nonNull(dateRelease);
        }

        @Override
        public String toString() {
            return "Result{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DiscoverMoviesResponse{" +
                "page=" + page +
                ", totalResults=" + totalResults +
                ", totalPages=" + totalPages +
                ", results=" + Arrays.toString(results) +
                '}';
    }
}
