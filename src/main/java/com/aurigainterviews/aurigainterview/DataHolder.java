package com.aurigainterviews.aurigainterview;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataHolder {

    private static final Logger log = LoggerFactory.getLogger(DataHolder.class);

    @JsonProperty("name")
    public String name;

    @JsonProperty("agentVersion")
    public String agentVersion;

    @JsonProperty("alertIds")
    public JSONArray howManyAlerts;

    @JsonProperty("architecture")
    public String architecture;

    @JsonProperty("collector")
    public JSONObject collector;

    @JsonProperty("cpuModel")
    public String cpuModel;

    @JsonProperty("description")
    public String description;

    @JsonProperty("discoveryDate")
    public String discoveryDate;

    @JsonProperty("ipAddresses")
    public JSONArray ipAddressArray;

    public String getName() {
        return name;
    }

    public String getAgentVersion() {
        return agentVersion;
    }

    public String getArchitecture() {
        return architecture;
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getIpAddresses() {
        ArrayList<String> ipAddresses = new ArrayList<>();
        String ipAddressString = ipAddressArray.toString();
        try {
            org.json.JSONArray jsonArr = new org.json.JSONArray(ipAddressString);
            for(int i = 0; i < this.ipAddressArray.size(); i++){
                org.json.JSONObject newObject = new org.json.JSONObject(jsonArr.get(i).toString());
                ipAddresses.add(newObject.get("ipAddress").toString());
            }
            return ipAddresses;
        } catch (JSONException e) {
            log.info(e.toString());
            return Collections.emptyList();
        }
    }

    String getDiscoveryDate(){
        this.discoveryDate = this.discoveryDate.substring(0, 10);
        return this.discoveryDate;
    }

    String getCollector(){
        return this.collector.get("collectorName").toString();
    }

    int getAlertIds(){
        return howManyAlerts.size();
    }

    @Override
    public String toString() {
            return "{ " +
                    "name:'" + name + '\'' +
                    ", agentVersion:'" + agentVersion + '\'' +
                    ", howManyAlerts:" + getAlertIds() +
                    ", architecture:'" + architecture + '\'' +
                    ", collector:" + getCollector() +
                    ", cpuModel:'" + cpuModel + '\'' +
                    ", description:'" + description + '\'' +
                    ", discoveryDate:'" + getDiscoveryDate() + '\'' +
                    ", ipAddresses:" + getIpAddresses() +
                    "} ";
    }
}
