package com.example.controller;

import com.example.model.Patient;
import com.example.dao.PatientDAO;
import java.util.List;

public class PatientController {
    private final PatientDAO patientDAO;

    public PatientController() {
        this.patientDAO = new PatientDAO();
    }

    public boolean addPatient(Patient patient) {
        // Validation des données
        if (!validatePatient(patient)) {
            return false;
        }
        return patientDAO.insertPatient(patient);
    }

    public boolean updatePatient(Patient patient) {
        if (!validatePatient(patient)) {
            return false;
        }
        return patientDAO.updatePatient(patient);
    }

    public boolean deletePatient(int id) {
        if (id <= 0) {
            return false;
        }
        return patientDAO.deletePatient(id);
    }

    public Patient getPatientByCIN(String cin) {
        if (cin == null || cin.trim().isEmpty()) {
            return null;
        }
        return patientDAO.getPatientByCIN(cin);
    }

    public List<Patient> getAllPatients() {
        return patientDAO.getAllPatients();
    }

    private boolean validatePatient(Patient patient) {
        if (patient == null) return false;
        if (patient.getNom() == null || patient.getNom().trim().isEmpty()) return false;
        if (patient.getPrenom() == null || patient.getPrenom().trim().isEmpty()) return false;
        if (patient.getCin() == null || patient.getCin().trim().isEmpty()) return false;
        if (patient.getSexe() == null || patient.getSexe().trim().isEmpty()) return false;
        if (patient.getBirthDate() == null) return false;
        if (patient.getTel() == null || !patient.getTel().matches("\\d{10}")) return false;
        
        return true;
    }
}
