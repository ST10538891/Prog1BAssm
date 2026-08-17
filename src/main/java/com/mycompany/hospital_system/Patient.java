/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospital_system;

/**
 *
 * @author Student
 *
 *
  /**
* Base class representing a hospital patient.
* Outpatients and Emergency patients use this class directly.
*/
public class Patient {
private String patientId;
private String firstName;
private String lastName;
private int age;
private String gender;
private String medicalCondition;
private PatientCategory category;

public Patient(String patientId, String firstName, String lastName,
int age, String gender, String medicalCondition,
PatientCategory category) {
this.patientId = patientId;
this.firstName = firstName;
this.lastName = lastName;
this.age = age;
this.gender = gender;
this.medicalCondition = medicalCondition;
this.category = category;
}

// ---------- Getters ----------
public String getPatientId() {
return patientId;
}

public String getFirstName() {
return firstName;
}

public String getLastName() {
return lastName;
}

public int getAge() {
return age;
}

public String getGender() {
return gender;
}

public String getMedicalCondition() {
return medicalCondition;
}

public PatientCategory getCategory() {
return category;
}

// ---------- Setters (for update functionality) ----------
public void setFirstName(String firstName) {
this.firstName = firstName;
}

public void setLastName(String lastName) {
this.lastName = lastName;
}

public void setAge(int age) {
this.age = age;
}

public void setGender(String gender) {
this.gender = gender;
}

public void setMedicalCondition(String medicalCondition) {
this.medicalCondition = medicalCondition;
}

public void setCategory(PatientCategory category) {
this.category = category;
}

/**
* Displays the patient's details.
* Overridden by Inpatient to include ward and bed information.
*/
public void displayDetails() {
System.out.println("----------------------------------------");
System.out.println("Patient ID : " + patientId);
System.out.println("Name : " + firstName + " " + lastName);
System.out.println("Age : " + age);
System.out.println("Gender : " + gender);
System.out.println("Medical Condition: " + medicalCondition);
System.out.println("Category : " + category);
System.out.println("----------------------------------------");
}

@Override
public String toString() {
return patientId + " - " + firstName + " " + lastName + " (" + category + ")";
}
}
