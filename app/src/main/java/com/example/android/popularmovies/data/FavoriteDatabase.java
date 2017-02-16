package com.example.android.popularmovies.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Tony on 2/12/17.
 */


@Database(version = FavoriteDatabase.VERSION)
public final class FavoriteDatabase {
    public static final int VERSION = 1;

    @Table(FavoriteColumns.class)public static final String FAVORITE = "favorite";
}
