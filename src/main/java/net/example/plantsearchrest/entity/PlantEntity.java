package net.example.plantsearchrest.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="plants")
public class PlantEntity extends BaseEntity {
    @Column(name="ua_name")
    private String name;

    @Column(name="la_name")
    private String latinName;

    @Column(name="height")
    private String height;

    @Column(name="habitus")
    private String habitus;

    @Column(name="growth_rate")
    private String growthRate;

    @Column(name="color")
    private String color;

    @Column(name="summer_color")
    private String summerColor;   //

    @Column(name="autumn_color")
    private String autumnColor;   //

    @Column(name="flowering_color")
    private String floweringColor; //

    @Column(name="frost_resistance")
    private String frostResistance;

    @Column(name="sketch")
    private String sketch;

    @Column(name="image")
    private String image;

    @Column(name="place_recommendation")
    private String recommendation;

    @Column(name="lighting")
    private String lighting;

    @Column(name="evergreen")
    private String evergreen;

    @Column(name="flowering_period")
    private String floweringPeriod;

    @Column(name="plant_type")
    private String plantType;

    @Column(name="zoning")
    private String zoning;

    @Column(name="pH")
    private String ph;

    @Column(name="soilMoisture")
    private String soilMoisture;

    @Column(name="hardy")
    private String hardy;

    @Column(name="nutrition")
    private String nutrition;
}
