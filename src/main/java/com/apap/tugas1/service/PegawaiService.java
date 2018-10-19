package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	Optional<PegawaiModel> getPegawaiByNIP(Long NIP);
	
	void addPegawai(PegawaiModel Pegawai);
	
	List<PegawaiModel> getPegawaiByInstansi(InstansiModel Instansi);
	
	String makeNip(PegawaiModel pegawai);
}
