package com.example.leruyn.weatherappmvvm.model;

/**
 * Created by LeRuyn on 4/21/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Object Clouds
 */
public class Clouds  implements Serializable {

    @SerializedName("all")
    @Expose
    private Long all;

    public Long getAll() {
        return all;
    }

    public void setAll(Long all) {
        this.all = all;
    }

}