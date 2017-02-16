package com.example.android.popularmovies.data;

import android.graphics.Path;
import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Tony on 2/12/17.
 */

@ContentProvider(authority = FavoriteProvider.AUTHORITY, database = FavoriteDatabase.class)
public class FavoriteProvider {

    public static final String AUTHORITY = "com.example.android.popularmovies.data.FavoriteProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path{
        String FAVORITES = "favorite";
    }

    private static Uri builderUri(String ... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths){
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = FavoriteDatabase.FAVORITE) public static class Favorite {
        @ContentUri(
                path = "favorite",
                type = "vnd.android.cursor.dir/favorite",
                defaultSort = FavoriteColumns.TITLE + " ASC")
        public static final Uri FAVORITE_URI = builderUri(Path.FAVORITES);
    }

    @InexactContentUri(
            name = "FAVORITE_ID",
            path = Path.FAVORITES + "/#",
            type = "vnd.android.cursor.item/favorite",
            whereColumn = FavoriteColumns._ID,
            pathSegment = 1)
    public static Uri withId(long id){
        return builderUri(Path.FAVORITES, String.valueOf(id));
    }
}
