package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.repository.JabatanPegawaiDb;

@Service
@Transactional
public class JabatanPegawaiServiceImpl implements JabatanPegawaiService{

	@Autowired
	private JabatanPegawaiDb jabatanPegawaiDb;
	
	@Override
	public Optional<List<JabatanPegawaiModel>> getJabatanByNip(String nip) {
		// TODO Auto-generated method stub
		return jabatanPegawaiDb.findAllByPegawai_Nip(nip);
	}

	@Override
	public List<JabatanPegawaiModel> getPegawaiById(Long id) {
		// TODO Auto-generated method stub
		return jabatanPegawaiDb.findAllByJabatan_Id(id);
	}

	@Override
	public void addJabatanPegawai(JabatanPegawaiModel jabatanPegawai) {
		// TODO Auto-generated method stub
		jabatanPegawaiDb.save(jabatanPegawai);
	}
}
