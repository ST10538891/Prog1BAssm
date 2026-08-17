/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospital_system;

/**
 *
 * @author Student
 */


/**
* Represents an Inpatient. Extends Patient and stores additional
* ward number and bed number information.
*/
public class Inpatient extends Patient {
private String wardNumber;
private String bedNumber; // e.g. "B01", "B12", etc. null if not allocated

public Inpatient(String patientId, String firstName, String lastName,
int age, String gender, String medicalCondition,
String wardNumber) {
// Initialise inherited attributes via super()
super(patientId, firstName, lastName, age, gender, medicalCondition,
PatientCategory.INPATIENT);
this.wardNumber = wardNumber;
this.bedNumber = null; // no bed allocated yet
}

// ---------- Getters ----------
public String getWardNumber() {
return wardNumber;
}

public String getBedNumber() {
return bedNumber;
}

// ---------- Setters ----------
public void setWardNumber(String wardNumber) {
this.wardNumber = wardNumber;
}

public void setBedNumber(String bedNumber) {
this.bedNumber = bedNumber;
}

/**
* Override of displayDetails() to include ward and bed information.
*/
@Override
public void displayDetails() {
super.displayDetails(); // reuse base display
System.out.println("Ward Number : " + wardNumber);
System.out.println("Bed Number : " + (bedNumber == null ? "Not allocated" : bedNumber));
System.out.println("----------------------------------------");
}
}

