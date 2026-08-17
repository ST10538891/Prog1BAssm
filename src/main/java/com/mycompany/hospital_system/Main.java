/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospital_system;

/**
 *
 * @author Student
 */

import java.util.Scanner;

/**
* Console-based, menu-driven Hospital Patient Admission System.
*/
public class Main {
private static final Scanner scanner = new Scanner(System.in);
private static final HospitalSystem system = new HospitalSystem();

public static void main(String[] args) {
System.out.println("=================================================");
System.out.println(" MediCare Hospital - Patient Admission System ");
System.out.println("=================================================");

boolean running = true;
while (running) {
printMainMenu();
int choice = readInt("Enter choice: ");
switch (choice) {
case 1 -> patientManagementMenu();
case 2 -> bedManagementMenu();
case 3 -> reportsMenu();
case 4 -> {
System.out.println("Thank you for using the system. Goodbye!");
running = false;
}
default -> System.out.println("Invalid choice. Please try again.");
}
}
scanner.close();
}

// ------------------------------------------------------------------
// Menus
// ------------------------------------------------------------------
private static void printMainMenu() {
System.out.println("\n---------- MAIN MENU ----------");
System.out.println("1. Patient Management");
System.out.println("2. Bed Management");
System.out.println("3. Reports");
System.out.println("4. Exit");
System.out.println("-------------------------------");
}

private static void patientManagementMenu() {
boolean back = false;
while (!back) {
System.out.println("\n----- PATIENT MANAGEMENT -----");
System.out.println("1. Register a new patient");
System.out.println("2. Search for a patient");
System.out.println("3. Update patient details");
System.out.println("4. Delete a patient");
System.out.println("5. Display all patients");
System.out.println("6. Sort patients");
System.out.println("7. Back to Main Menu");
int choice = readInt("Enter choice: ");
switch (choice) {
case 1 -> registerPatient();
case 2 -> searchPatient();
case 3 -> updatePatient();
case 4 -> deletePatient();
case 5 -> system.displayAllPatients();
case 6 -> sortPatients();
case 7 -> back = true;
default -> System.out.println("Invalid choice.");
}
}
}

private static void bedManagementMenu() {
boolean back = false;
while (!back) {
System.out.println("\n----- BED MANAGEMENT -----");
System.out.println("1. Allocate a bed");
System.out.println("2. Release a bed");
System.out.println("3. Display complete ward layout");
System.out.println("4. Display available beds");
System.out.println("5. Display occupied beds");
System.out.println("6. Back to Main Menu");
int choice = readInt("Enter choice: ");
switch (choice) {
case 1 -> allocateBed();
case 2 -> releaseBed();
case 3 -> system.displayWardLayout();
case 4 -> system.displayAvailableBeds();
case 5 -> system.displayOccupiedBeds();
case 6 -> back = true;
default -> System.out.println("Invalid choice.");
}
}
}

private static void reportsMenu() {
System.out.println("\n----- REPORTS -----");
system.displayAllPatients();
system.displayAvailableBeds();
system.displayOccupiedBeds();
system.generateReports();
}

// ------------------------------------------------------------------
// Patient operations
// ------------------------------------------------------------------
private static void registerPatient() {
System.out.println("\n--- Register New Patient ---");
String id = readString("Patient ID: ");
String firstName = readString("First Name: ");
String lastName = readString("Last Name: ");
int age = readInt("Age: ");
String gender = readString("Gender (M/F/Other): ");
String condition = readString("Medical Condition: ");

System.out.println("Category:");
System.out.println("1. Inpatient");
System.out.println("2. Outpatient");
System.out.println("3. Emergency");
int catChoice = readInt("Choose category (1-3): ");

Patient patient;
switch (catChoice) {
case 1 -> {
// Create Inpatient - ward is fixed
patient = new Inpatient(id, firstName, lastName, age, gender,
condition, system.getWardNumber());
}
case 2 -> patient = new Patient(id, firstName, lastName, age, gender,
condition, PatientCategory.OUTPATIENT);
case 3 -> patient = new Patient(id, firstName, lastName, age, gender,
condition, PatientCategory.EMERGENCY);
default -> {
System.out.println("Invalid category. Registration cancelled.");
return;
}
}
system.registerPatient(patient);
}

private static void searchPatient() {
String id = readString("Enter Patient ID to search: ");
Patient p = system.findPatientById(id);
if (p != null) {
System.out.println("Patient found:");
p.displayDetails();
} else {
System.out.println("Patient not found.");
}
}

private static void updatePatient() {
String id = readString("Enter Patient ID to update: ");
Patient p = system.findPatientById(id);
if (p == null) {
System.out.println("Patient not found.");
return;
}
System.out.println("Current details:");
p.displayDetails();
System.out.println("Enter new details (leave blank to keep current value for text fields):");

String firstName = readStringAllowBlank("First Name [" + p.getFirstName() + "]: ");
if (firstName.isEmpty()) firstName = p.getFirstName();

String lastName = readStringAllowBlank("Last Name [" + p.getLastName() + "]: ");
if (lastName.isEmpty()) lastName = p.getLastName();

System.out.print("Age [" + p.getAge() + "]: ");
String ageStr = scanner.nextLine().trim();
int age = ageStr.isEmpty() ? p.getAge() : Integer.parseInt(ageStr);

String gender = readStringAllowBlank("Gender [" + p.getGender() + "]: ");
if (gender.isEmpty()) gender = p.getGender();

String condition = readStringAllowBlank("Medical Condition [" + p.getMedicalCondition() + "]: ");
if (condition.isEmpty()) condition = p.getMedicalCondition();

system.updatePatient(id, firstName, lastName, age, gender, condition);
}

private static void deletePatient() {
String id = readString("Enter Patient ID to delete: ");
system.deletePatient(id);
}

private static void sortPatients() {
System.out.println("Sort by:");
System.out.println("1. Surname");
System.out.println("2. Patient ID");
int choice = readInt("Choice: ");
if (choice == 1) {
system.sortPatients("surname");
} else if (choice == 2) {
system.sortPatients("id");
} else {
System.out.println("Invalid choice.");
}
system.displayAllPatients();
}

// ------------------------------------------------------------------
// Bed operations
// ------------------------------------------------------------------
private static void allocateBed() {
String patientId = readString("Enter Inpatient ID: ");
system.displayAvailableBeds();
String bed = readString("Enter bed number to allocate (e.g. B01): ").toUpperCase();
system.allocateBed(patientId, bed);
}

private static void releaseBed() {
system.displayOccupiedBeds();
String bed = readString("Enter bed number to release (e.g. B01): ").toUpperCase();
system.releaseBed(bed);
}

// ------------------------------------------------------------------
// Helper input methods (with basic exception handling)
// ------------------------------------------------------------------
private static int readInt(String prompt) {
while (true) {
try {
System.out.print(prompt);
int value = Integer.parseInt(scanner.nextLine().trim());
return value;
} catch (NumberFormatException e) {
System.out.println("Please enter a valid integer.");
}
}
}

private static String readString(String prompt) {
System.out.print(prompt);
return scanner.nextLine().trim();
}

private static String readStringAllowBlank(String prompt) {
System.out.print(prompt);
return scanner.nextLine().trim();
}
}
