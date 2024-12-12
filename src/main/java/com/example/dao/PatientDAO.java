package com.example.dao;

import com.example.model.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    
    public boolean insertPatient(Patient patient) {
        String sql = "INSERT INTO patient (Nom, Prenom, Sexe, BirthDate, Adresse, Tel, IDInsurance, CIN, Ville) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, patient.getNom());
            pstmt.setString(2, patient.getPrenom());
            pstmt.setString(3, patient.getSexe());
            pstmt.setTimestamp(4, Timestamp.valueOf(patient.getBirthDate()));
            pstmt.setString(5, patient.getAdresse());
            pstmt.setString(6, patient.getTel());
            pstmt.setInt(7, patient.getIdInsurance());
            pstmt.setString(8, patient.getCin());
            pstmt.setString(9, patient.getVille());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePatient(Patient patient) {
        String sql = "UPDATE patient SET Nom=?, Prenom=?, Sexe=?, BirthDate=?, Adresse=?, " +
                     "Tel=?, IDInsurance=?, CIN=?, Ville=? WHERE IDPatient=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, patient.getNom());
            pstmt.setString(2, patient.getPrenom());
            pstmt.setString(3, patient.getSexe());
            pstmt.setTimestamp(4, Timestamp.valueOf(patient.getBirthDate()));
            pstmt.setString(5, patient.getAdresse());
            pstmt.setString(6, patient.getTel());
            pstmt.setInt(7, patient.getIdInsurance());
            pstmt.setString(8, patient.getCin());
            pstmt.setString(9, patient.getVille());
            pstmt.setInt(10, patient.getIdPatient());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePatient(int id) {
        String sql = "DELETE FROM patient WHERE IDPatient = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Patient getPatientByCIN(String cin) {
        String sql = "SELECT * FROM patient WHERE CIN = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cin);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractPatientFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                patients.add(extractPatientFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    private Patient extractPatientFromResultSet(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setIdPatient(rs.getInt("IDPatient"));
        patient.setNom(rs.getString("Nom"));
        patient.setPrenom(rs.getString("Prenom"));
        patient.setSexe(rs.getString("Sexe"));
        patient.setBirthDate(rs.getTimestamp("BirthDate").toLocalDateTime());
        patient.setAdresse(rs.getString("Adresse"));
        patient.setTel(rs.getString("Tel"));
        patient.setIdInsurance(rs.getInt("IDInsurance"));
        patient.setCin(rs.getString("CIN"));
        patient.setVille(rs.getString("Ville"));
        return patient;
    }
}
