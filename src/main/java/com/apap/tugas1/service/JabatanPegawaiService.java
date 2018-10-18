package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.JabatanPegawaiModel;

public interface JabatanPegawaiService {
	Optional<List<JabatanPegawaiModel>> getJabatanByNip(String nip);
	List<JabatanPegawaiModel> getPegawaiById(Long id);
}
