package com.example.babycarev1;

public class Reserva {

    private int idReserva, idUsuario, idCuidador;
    private String fecha, hora;

    public Reserva(int idReserva, int idUsuario, int idCuidador, String fecha, String hora) {
        this.idReserva = idReserva;
        this.idUsuario = idUsuario;
        this.idCuidador = idCuidador;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCuidador() {
        return idCuidador;
    }

    public void setIdCuidador(int idCuidador) {
        this.idCuidador = idCuidador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }
}
