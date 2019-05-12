package com.example.calorietracker.helper.api.Entities;

public class NndFood {
    String ndb;
    String name;
    String measure;
    Nutrient[] nutrients;

    public String getNdb() {
        return ndb;
    }

    public void setNdb(String ndb) {
        this.ndb = ndb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Nutrient[] getNutrients() {
        return nutrients;
    }

    public void setNutrients(Nutrient[] nutrients) {
        this.nutrients = nutrients;
    }

    public NndFood(String ndb, String name, String measure, Nutrient[] nutrients) {
        this.ndb = ndb;
        this.name = name;
        this.measure = measure;
        this.nutrients = nutrients;
    }

    public class Nutrient {
        String nutrient_id;
        String nutrient;
        String unit;
        String value;
        Double gm;

        public Nutrient(String nutrient_id, String nutrient, String unit, String value, Double gm) {
            this.nutrient_id = nutrient_id;
            this.nutrient = nutrient;
            this.unit = unit;
            this.value = value;
            this.gm = gm;
        }

        public String getNutrient_id() {
            return nutrient_id;
        }

        public void setNutrient_id(String nutrient_id) {
            this.nutrient_id = nutrient_id;
        }

        public String getNutrient() {
            return nutrient;
        }

        public void setNutrient(String nutrient) {
            this.nutrient = nutrient;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Double getGm() {
            return gm;
        }

        public void setGm(Double gm) {
            this.gm = gm;
        }
    }
}
