package com.aware.app.testx.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Test implements Serializable {
    private String username;
    private int age;
    private boolean diagnosed_pd;
    private ArrayList<Medication> medications;
    private Symptoms symptoms;

    public Test(String username, int age, boolean diagnosed_pd) {
        this.username = username;
        this.age = age;
        this.diagnosed_pd = diagnosed_pd;
    }

    public Test(String username, int age, boolean diagnosed_pd,
                ArrayList<Medication> medications, Symptoms symptoms) {
        this.username = username;
        this.age = age;
        this.diagnosed_pd = diagnosed_pd;
        this.medications = medications;
        this.symptoms = symptoms;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isDiagnosed_pd() {
        return diagnosed_pd;
    }

    public void setDiagnosed_pd(boolean diagnosed_pd) {
        this.diagnosed_pd = diagnosed_pd;
    }

    public ArrayList<Medication> getMedications() {
        return medications;
    }

    public void setMedications(ArrayList<Medication> medications) {
        this.medications = medications;
    }

    public Symptoms getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Symptoms symptoms) {
        this.symptoms = symptoms;
    }

    public class Medication {
        private String name;
        private String intakeTime;
        private String dosage;
        private String notes;

        public Medication(String name, String intakeTime, String dosage, String notes) {
            this.name = name;
            this.intakeTime = intakeTime;
            this.dosage = dosage;
            this.notes  = notes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntakeTime() {
            return intakeTime;
        }

        public void setIntakeTime(String intakeTime) {
            this.intakeTime = intakeTime;
        }

        public String getDosage() {
            return dosage;
        }

        public void setDosage(String dosage) {
            this.dosage = dosage;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }

    private class Symptoms {
        private int swallowing;
        private int dressing;
        private int falling;
        private int turning_in_bed;
        private int walking;
        private int handwriting;
        private int freezing_when_walking;
        private int tremor;
        private int sensory_complaints;
        private int speech;
        private int salivation;
        private int cutting_food;
        private int hygiene;

        private Symptoms(int swallowing, int dressing, int falling, int turning_in_bed, int walking,
                        int handwriting, int freezing_when_walking, int tremor, int sensory_complaints,
                        int speech, int salivation, int cutting_food, int hygiene) {
            this.swallowing = swallowing;
            this.dressing = dressing;
            this.falling = falling;
            this.turning_in_bed = turning_in_bed;
            this.walking = walking;
            this.handwriting = handwriting;
            this.freezing_when_walking = freezing_when_walking;
            this.tremor = tremor;
            this.sensory_complaints = sensory_complaints;
            this.speech = speech;
            this.salivation = salivation;
            this.cutting_food = cutting_food;
            this.hygiene = hygiene;
        }

        public int getSwallowing() {
            return swallowing;
        }

        public void setSwallowing(int swallowing) {
            this.swallowing = swallowing;
        }

        public int getDressing() {
            return dressing;
        }

        public void setDressing(int dressing) {
            this.dressing = dressing;
        }

        public int getFalling() {
            return falling;
        }

        public void setFalling(int falling) {
            this.falling = falling;
        }

        public int getTurning_in_bed() {
            return turning_in_bed;
        }

        public void setTurning_in_bed(int turning_in_bed) {
            this.turning_in_bed = turning_in_bed;
        }

        public int getWalking() {
            return walking;
        }

        public void setWalking(int walking) {
            this.walking = walking;
        }

        public int getHandwriting() {
            return handwriting;
        }

        public void setHandwriting(int handwriting) {
            this.handwriting = handwriting;
        }

        public int getFreezing_when_walking() {
            return freezing_when_walking;
        }

        public void setFreezing_when_walking(int freezing_when_walking) {
            this.freezing_when_walking = freezing_when_walking;
        }

        public int getTremor() {
            return tremor;
        }

        public void setTremor(int tremor) {
            this.tremor = tremor;
        }

        public int getSensory_complaints() {
            return sensory_complaints;
        }

        public void setSensory_complaints(int sensory_complaints) {
            this.sensory_complaints = sensory_complaints;
        }

        public int getSpeech() {
            return speech;
        }

        public void setSpeech(int speech) {
            this.speech = speech;
        }

        public int getSalivation() {
            return salivation;
        }

        public void setSalivation(int salivation) {
            this.salivation = salivation;
        }

        public int getCutting_food() {
            return cutting_food;
        }

        public void setCutting_food(int cutting_food) {
            this.cutting_food = cutting_food;
        }

        public int getHygiene() {
            return hygiene;
        }

        public void setHygiene(int hygiene) {
            this.hygiene = hygiene;
        }
    }

}
