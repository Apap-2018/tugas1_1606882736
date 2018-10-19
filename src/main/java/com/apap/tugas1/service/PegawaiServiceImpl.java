package com.apap.tugas1.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanPegawaiDb;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDb PegawaiDb;
	
	@Autowired
	private JabatanPegawaiDb jabatanPegawaiDb;

	@Override
	public Optional<PegawaiModel> getPegawaiByNIP(String NIP) {
		return PegawaiDb.findByNip(NIP);
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

	@Override
	public void update(PegawaiModel pegawaiUpdate, PegawaiModel pegawaiBefore) {
		// TODO Auto-generated method stub
		pegawaiBefore.setInstansi(pegawaiUpdate.getInstansi());
		pegawaiBefore.setNama(pegawaiUpdate.getNama());
		pegawaiBefore.setNip(pegawaiUpdate.getNip());
		pegawaiBefore.setTahunmasuk(pegawaiUpdate.getTahunmasuk());
		pegawaiBefore.setTanggallahir(pegawaiUpdate.getTanggallahir());
		pegawaiBefore.setTempatlahir(pegawaiUpdate.getTempatlahir());
		
		
		// update jabatan
		int jumlahList = pegawaiBefore.getListJabatanPegawai().size();
		for (int i = 0; i< jumlahList; i++) {
			pegawaiBefore.getListJabatanPegawai().get(i).setJabatan(pegawaiUpdate.getListJabatanPegawai().get(i).getJabatan());
		}
		
		for (int i = jumlahList; i < pegawaiUpdate.getListJabatanPegawai().size(); i++) {
			pegawaiUpdate.getListJabatanPegawai().get(i).setPegawai(pegawaiBefore);
			jabatanPegawaiDb.save(pegawaiUpdate.getListJabatanPegawai().get(i));
		}
	}


}
