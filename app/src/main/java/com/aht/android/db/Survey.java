package com.aht.android.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * Created by krueger on 16.02.2017.
 */
@DatabaseTable(tableName = "survey")
public class Survey {

    @DatabaseField(generatedId = true)
    private Long id;

	/** systemId represents the ID given from main system*/
    @DatabaseField
    private Long systemId;

    @DatabaseField
    private String name;

    //Many_to_One
    @ForeignCollectionField
    Collection<Question> ql;

}
