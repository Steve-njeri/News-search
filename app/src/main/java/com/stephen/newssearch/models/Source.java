
package com.stephen.newssearch.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Source {

    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Source() {
    }

    /**
     * 
     * @param name

     */
    public Source(String name) {
        super();
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s", this.name);
    }

}
