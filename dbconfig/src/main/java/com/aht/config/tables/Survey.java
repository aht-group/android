package com.aht.config.tables;

import com.aht.config.tables.Section;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * Created by roblick on 16.02.2017.
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
    private Collection<Section> sections;

    public Collection<Section> getSections() {return sections;}
    public void setSections(Collection<Section> sections) {this.sections = sections;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Long getSystemId() {return systemId;}
    public void setSystemId(Long systemId) {this.systemId = systemId;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
}
