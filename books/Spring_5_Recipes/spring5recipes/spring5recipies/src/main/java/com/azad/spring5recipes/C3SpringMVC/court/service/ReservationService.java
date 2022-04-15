package com.azad.spring5recipes.C3SpringMVC.court.service;

import java.util.List;

import com.azad.spring5recipes.C3SpringMVC.court.domain.Reservation;

public interface ReservationService {

	public List<Reservation> query(String courtName);
}
