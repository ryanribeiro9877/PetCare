package com.petcare.model;

import java.time.LocalDateTime;

public class Consulta {
    private int id;
    private LocalDateTime dataHora;
    private int animalId;
    private int veterinarioId;
    private String diagnostico;
    private double valor;

    public Consulta() {}

    public Consulta(int id, LocalDateTime dataHora, int animalId, int veterinarioId, String diagnostico, double valor) {
        this.id = id;
        this.dataHora = dataHora;
        this.animalId = animalId;
        this.veterinarioId = veterinarioId;
        this.diagnostico = diagnostico;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public int getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(int veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", dataHora=" + dataHora +
                ", animalId=" + animalId +
                ", veterinarioId=" + veterinarioId +
                ", diagnostico='" + diagnostico + '\'' +
                ", valor=" + valor +
                '}';
    }
}
