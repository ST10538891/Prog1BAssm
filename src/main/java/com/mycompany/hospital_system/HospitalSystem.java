/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospital_system;

/**
 *
 * @author Student
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
* Core class that manages patients and the 20-bed ward.
* All data is stored in memory while the program is running.
*/
public class HospitalSystem {
// Exactly one ward with 20 beds arranged in a 4 x 5 layout
private static final int ROWS = 4;
private static final int COLS = 5;
private static final int TOTAL_BEDS = 20;
private static final String WARD_NUMBER = "W1"; // only one ward

// 2-D array representing the ward layout (null = available)
private final String[][] beds; // stores Patient ID when occupied
private final ArrayList<Patient> patients;

public HospitalSystem() {
beds = new String[ROWS][COLS];
// initialise all beds as empty
for (int r = 0; r < ROWS; r++) {
for (int c = 0; c < COLS; c++) {
beds[r][c] = null;
}
}
patients = new ArrayList<>();
}

// =====================================================================
// FEATURE 1 - Patient Management
// =====================================================================

/**
* Registers a new patient. Prevents duplicate Patient IDs.
* @return true if registration succeeded, false otherwise
*/
public boolean registerPatient(Patient patient) {
if (findPatientById(patient.getPatientId()) != null) {
System.out.println("Error: Patient ID has already been made. Registration will be cancelled.");
return false;
}
patients.add(patient);
System.out.println("Patient registered successfully.");
return true;
}

/**
* will be able to Search each patient by there respective id Patient ID.
*/
public Patient findPatientById(String patientId) {
for (Patient p : patients) {
if (p.getPatientId().equalsIgnoreCase(patientId)) {
return p;
}
}
return null;
}

/**
* Updates an existing patient's details (non-ID fields).
*/
public boolean updatePatient(String patientId, String firstName, String lastName,
int age, String gender, String medicalCondition) {
Patient p = findPatientById(patientId);
if (p == null) {
System.out.println("Patient not found.");
return false;
}
p.setFirstName(firstName);
p.setLastName(lastName);
p.setAge(age);
p.setGender(gender);
p.setMedicalCondition(medicalCondition);
System.out.println("Patient details updated successfully.");
return true;
}

/**
* Deletes a patient. If the patient is an Inpatient with a bed,
* the bed is automatically released first.
*/
public boolean deletePatient(String patientId) {
Patient p = findPatientById(patientId);
if (p == null) {
System.out.println("Patient not found.");
return false;
}
// Release bed if occupied
if (p instanceof Inpatient) {
Inpatient ip = (Inpatient) p;
if (ip.getBedNumber() != null) {
releaseBed(ip.getBedNumber());
}
}
patients.remove(p);
System.out.println("Patient deleted successfully.");
return true;
}

/**
* Displays all registered patients.
*/
public void displayAllPatients() {
if (patients.isEmpty()) {
System.out.println("No patients registered.");
return;
}
System.out.println("\n===== ALL REGISTERED PATIENTS =====");
for (Patient p : patients) {
p.displayDetails();
}
}

/**
* Sorts patients by surname (last name) or by Patient ID.
*/
public void sortPatients(String criteria) {
if (criteria.equalsIgnoreCase("surname")) {
Collections.sort(patients, Comparator.comparing(Patient::getLastName)
.thenComparing(Patient::getFirstName));
System.out.println("Patients sorted by surname.");
} else if (criteria.equalsIgnoreCase("id")) {
Collections.sort(patients, Comparator.comparing(Patient::getPatientId));
System.out.println("Patients sorted by Patient ID.");
} else {
System.out.println("Invalid sort criteria. Use 'surname' or 'id'.");
}
}

public ArrayList<Patient> getPatients() {
return patients;
}

// =====================================================================
// FEATURE 2 - Bed Management
// =====================================================================

/**
* Converts a bed label (B01-B20) to row/col indices.
* @return int[]{row, col} or null if invalid
*/
private int[] bedLabelToIndex(String bedLabel) {
if (bedLabel == null || bedLabel.length() != 3 || !bedLabel.startsWith("B")) {
return null;
}
try {
int num = Integer.parseInt(bedLabel.substring(1));
if (num < 1 || num > TOTAL_BEDS) {
return null;
}
int index = num - 1;
return new int[]{index / COLS, index % COLS};
} catch (NumberFormatException e) {
return null;
}
}

/**
* Converts row/col indices back to a bed label (B01-B20).
*/
private String indexToBedLabel(int row, int col) {
int num = row * COLS + col + 1;
return String.format("B%02d", num);
}

/**
* Allocates an available bed to an inpatient.
* Prevents allocation of an occupied bed and prevents allocation when full.
*/
public boolean allocateBed(String patientId, String bedLabel) {
Patient p = findPatientById(patientId);
if (p == null) {
System.out.println("Patient not found.");
return false;
}
if (!(p instanceof Inpatient)) {
System.out.println("Only Inpatients may be allocated a hospital bed.");
return false;
}
Inpatient ip = (Inpatient) p;
if (ip.getBedNumber() != null) {
System.out.println("This inpatient already has a bed allocated (" + ip.getBedNumber() + ").");
return false;
}

int[] pos = bedLabelToIndex(bedLabel);
if (pos == null) {
System.out.println("Invalid bed number. Use B01-B20.");
return false;
}
int row = pos[0];
int col = pos[1];

if (beds[row][col] != null) {
System.out.println("Bed " + bedLabel + " is already occupied.");
return false;
}

// Check if any beds are free (extra safety)
if (countOccupiedBeds() >= TOTAL_BEDS) {
System.out.println("No beds available. Ward is full.");
return false;
}

beds[row][col] = patientId;
ip.setBedNumber(bedLabel);
System.out.println("Bed " + bedLabel + " allocated to patient " + patientId + ".");
return true;
}

/**
* Releases a bed when a patient is discharged.
*/
public boolean releaseBed(String bedLabel) {
int[] pos = bedLabelToIndex(bedLabel);
if (pos == null) {
System.out.println("Invalid bed number.");
return false;
}
int row = pos[0];
int col = pos[1];

if (beds[row][col] == null) {
System.out.println("Bed " + bedLabel + " is already free.");
return false;
}

String patientId = beds[row][col];
beds[row][col] = null;

// Clear the bed number on the patient object
Patient p = findPatientById(patientId);
if (p instanceof Inpatient) {
((Inpatient) p).setBedNumber(null);
}

System.out.println("Bed " + bedLabel + " released.");
return true;
}

/**
* Displays the complete 4 x 5 ward layout.
*/
public void displayWardLayout() {
System.out.println("\n===== WARD LAYOUT (Ward " + WARD_NUMBER + ") =====");
for (int r = 0; r < ROWS; r++) {
for (int c = 0; c < COLS; c++) {
String label = indexToBedLabel(r, c);
if (beds[r][c] == null) {
System.out.printf("%-6s", label); // free
} else {
System.out.printf("%-6s", "[" + label + "]"); // occupied
}
}
System.out.println();
}
System.out.println("( [Bxx] = occupied )");
}

/**
* Displays all available (free) beds.
*/
public void displayAvailableBeds() {
System.out.println("\n===== AVAILABLE BEDS =====");
boolean any = false;
for (int r = 0; r < ROWS; r++) {
for (int c = 0; c < COLS; c++) {
if (beds[r][c] == null) {
System.out.print(indexToBedLabel(r, c) + " ");
any = true;
}
}
}
if (!any) {
System.out.println("No beds available.");
} else {
System.out.println();
}
}

/**
* Displays all occupied beds together with the occupying patient ID.
*/
public void displayOccupiedBeds() {
System.out.println("\n===== OCCUPIED BEDS =====");
boolean any = false;
for (int r = 0; r < ROWS; r++) {
for (int c = 0; c < COLS; c++) {
if (beds[r][c] != null) {
System.out.println(indexToBedLabel(r, c) + " -> Patient " + beds[r][c]);
any = true;
}
}
}
if (!any) {
System.out.println("No beds currently occupied.");
}
}

public int countOccupiedBeds() {
int count = 0;
for (int r = 0; r < ROWS; r++) {
for (int c = 0; c < COLS; c++) {
if (beds[r][c] != null) {
count++;
}
}
}
return count;
}

public int getTotalBeds() {
return TOTAL_BEDS;
}

public String getWardNumber() {
return WARD_NUMBER;
}

// =====================================================================
// FEATURE 3 - Reports
// =====================================================================

public void generateReports() {
System.out.println("\n========== WARD REPORTS ==========");
System.out.println("Total registered patients : " + patients.size());
System.out.println("Total occupied beds : " + countOccupiedBeds());
System.out.println("Total available beds : " + (TOTAL_BEDS - countOccupiedBeds()));
double occupancy = (countOccupiedBeds() * 100.0) / TOTAL_BEDS;
System.out.printf("Ward occupancy percentage : %.1f%%%n", occupancy);
System.out.println("==================================");
}
}

