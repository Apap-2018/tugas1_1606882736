package com.apap.tugas1.service;

import java.util.Optional;

import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	Optional<PegawaiModel> getPegawaiByNIP(Long NIP);
	
	void addPegawai(PegawaiModel Pegawai);
}
