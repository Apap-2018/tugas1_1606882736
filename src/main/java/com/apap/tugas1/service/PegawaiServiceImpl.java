package com.apap.tugas1.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDb PegawaiDb;

	@Override
	public Optional<PegawaiModel> getPegawaiByNIP(Long NIP) {
		return PegawaiDb.findByNip(String.valueOf(NIP));
	}

	@Override
	public void addPegawai(PegawaiModel Pegawai) {
		PegawaiDb.save(Pegawai);
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel Instansi) {
		// TODO Auto-generated method stub
		return PegawaiDb.findByInstansiOrderByTanggallahirAsc(Instansi);
	}

	@Override
	public String makeNip(PegawaiModel Pegawai) {
		// TODO Auto-generated method stub
		String nip = "";
		nip += Pegawai.getInstansi().getId();
		Date date = Pegawai.getTanggallahir();
		String[] tglLahir = (""+date).split("-");
		for (int i = tglLahir.length-1; i >= 0; i--) {
			int ukuranTgl = tglLahir[i].length();
			nip += tglLahir[i].substring(ukuranTgl-2, ukuranTgl);
		}
		nip += Pegawai.getTahunmasuk();
		
		List<PegawaiModel> listPegawai = PegawaiDb.findByTanggallahirAndTahunmasukAndInstansi(Pegawai.getTanggallahir(), Pegawai.getTahunmasuk(), Pegawai.getInstansi());
		
		int banyakPegawai = listPegawai.size();
		
		if (banyakPegawai >= 10) {
			nip += banyakPegawai;
		}
		else {
			nip += "0" + (banyakPegawai+1);
		}
		
		return nip;
	}
}
