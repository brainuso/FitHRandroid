package com.uop.fithr;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivitiesHeartIntraday {

    @SerializedName("dataset")
    @Expose
    private List<Dataset> dataset = null;
    @SerializedName("datasetInterval")
    @Expose
    private Integer datasetInterval;
    @SerializedName("datasetType")
    @Expose
    private String datasetType;

    /**
     * No args constructor for use in serialization
     *
     */
    public ActivitiesHeartIntraday() {
    }

    /**
     *
     * @param datasetType
     * @param dataset
     * @param datasetInterval
     */
    public ActivitiesHeartIntraday(List<Dataset> dataset, Integer datasetInterval, String datasetType) {
        super();
        this.dataset = dataset;
        this.datasetInterval = datasetInterval;
        this.datasetType = datasetType;
    }

    public List<Dataset> getDataset() {
        return dataset;
    }

    public void setDataset(List<Dataset> dataset) {
        this.dataset = dataset;
    }

    public ActivitiesHeartIntraday withDataset(List<Dataset> dataset) {
        this.dataset = dataset;
        return this;
    }

    public Integer getDatasetInterval() {
        return datasetInterval;
    }

    public void setDatasetInterval(Integer datasetInterval) {
        this.datasetInterval = datasetInterval;
    }

    public ActivitiesHeartIntraday withDatasetInterval(Integer datasetInterval) {
        this.datasetInterval = datasetInterval;
        return this;
    }

    public String getDatasetType() {
        return datasetType;
    }

    public void setDatasetType(String datasetType) {
        this.datasetType = datasetType;
    }

    public ActivitiesHeartIntraday withDatasetType(String datasetType) {
        this.datasetType = datasetType;
        return this;
    }

}