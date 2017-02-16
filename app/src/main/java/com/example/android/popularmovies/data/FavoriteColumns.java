package com.example.android.popularmovies.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Tony on 2/12/17.
 */

public interface FavoriteColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey String _ID = "_id";

    @DataType(DataType.Type.TEXT) @NotNull String TITLE = "title";

    @DataType(DataType.Type.TEXT) @NotNull String POSTER = "poster";

    @DataType(DataType.Type.TEXT) @NotNull String PLOT = "plot";

    @DataType(DataType.Type.TEXT) @NotNull String RATING = "rating";

    @DataType(DataType.Type.TEXT) @NotNull String DATE = "date";

}
