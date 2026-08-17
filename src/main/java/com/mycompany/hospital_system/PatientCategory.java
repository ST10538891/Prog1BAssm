/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospital_system;

/**
 *
 * @author Student
 *
    
/**
* Enum representing the three patient categories treated by the hospital.
*/
public enum PatientCategory {
INPATIENT,
OUTPATIENT,
EMERGENCY;

@Override
public String toString() {
String name = name();
return name.charAt(0) + name.substring(1).toLowerCase();
}
}

