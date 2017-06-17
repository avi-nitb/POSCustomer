package com.poscustomer.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abhishek on 24-04-2017.
 */

public class ResturantData implements Serializable {
    private String status;
    private List<DataArray> message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataArray> getMessage() {
        return message;
    }

    public void setMessage(List<DataArray> message) {
        this.message = message;
    }

    public class DataArray {
        private String restaurant_id = "";
        private String name = "";
        private String lat = "";
        private String lng = "";
        private String radius = "";

        public String getRestaurant_id() {
            return restaurant_id;
        }

        public void setRestaurant_id(String restaurant_id) {
            this.restaurant_id = restaurant_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getRadius() {
            return radius;
        }

        public void setRadius(String radius) {
            this.radius = radius;
        }
    }
}
