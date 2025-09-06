package com.ujjwal.mediconnect.service;

import com.ujjwal.mediconnect.entity.Appointment;
import java.util.List;

public interface AppointmentService {
    Appointment bookAppointment(Appointment appointment, Long patientId, Long doctorId);
    Appointment getAppointmentById(Long id);
    List<Appointment> getAppointmentsByPatient(Long patientId);
    List<Appointment> getAppointmentsByDoctor(Long doctorId);
    Appointment updateAppointment(Long id, Appointment appointment);
    void cancelAppointment(Long id);
}
