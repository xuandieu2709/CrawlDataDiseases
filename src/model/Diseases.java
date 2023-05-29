/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author Dell
 */
public class Diseases {

    private int id;
    private String name; // tên bệnh
    private List<String> image;
    private String overview; // Tổng quan
    private String reason; // nguyên nhân
    private String symptom; // triệu chứng
    private String transmission_route; // đường lây truyền
    private String subjects_at_risk; // đối tượng nguy cơ
    private String prevent; // phòng ngừa
    private String diagnostic_measures; // biện pháp chuẩn đoán
    private String treatment_measures; // biện pháp điều trị

    public Diseases() {
    }

    public Diseases(int id, String name, List<String> image, String overview, String reason, String symptom, String transmission_route, String subjects_at_risk, String prevent, String diagnostic_measures, String treatment_measures) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.overview = overview;
        this.reason = reason;
        this.symptom = symptom;
        this.transmission_route = transmission_route;
        this.subjects_at_risk = subjects_at_risk;
        this.prevent = prevent;
        this.diagnostic_measures = diagnostic_measures;
        this.treatment_measures = treatment_measures;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getTransmission_route() {
        return transmission_route;
    }

    public void setTransmission_route(String transmission_route) {
        this.transmission_route = transmission_route;
    }

    public String getSubjects_at_risk() {
        return subjects_at_risk;
    }

    public void setSubjects_at_risk(String subjects_at_risk) {
        this.subjects_at_risk = subjects_at_risk;
    }

    public String getPrevent() {
        return prevent;
    }

    public void setPrevent(String prevent) {
        this.prevent = prevent;
    }

    public String getDiagnostic_measures() {
        return diagnostic_measures;
    }

    public void setDiagnostic_measures(String diagnostic_measures) {
        this.diagnostic_measures = diagnostic_measures;
    }

    public String getTreatment_measures() {
        return treatment_measures;
    }

    public void setTreatment_measures(String treatment_measures) {
        this.treatment_measures = treatment_measures;
    }

}
