package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping("/")
	private String home(Model model){
		List<JabatanModel> listJabatan = jabatanService.getAll();
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listinstansi" , listInstansi);
		model.addAttribute("listjabatan" , listJabatan);
		return "home";
	}
	
	@RequestMapping(value = "/pegawai")
	private String viewPegawai(@RequestParam(name="pegawaiNip") String nip, Model model){
		PegawaiModel pegawai = pegawaiService.getPegawaiByNIP(nip).get();
		List<JabatanPegawaiModel> jabatanPegawai = jabatanPegawaiService.getJabatanByNip(nip).get();
		double gaji = 0.0;
		for(JabatanPegawaiModel jabatans : jabatanPegawai) {
			if (jabatans.getJabatan().getGaji_pokok() > gaji) {
				gaji=jabatans.getJabatan().getGaji_pokok();
			}
		}
		gaji += pegawai.getInstansi().getProvinsi().getPresentase_tunjangan()/100 * gaji;
		model.addAttribute("gaji", (long)gaji);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("jabatanPegawai", jabatanPegawai);
		return "view-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method = RequestMethod.GET)
	private String updatePegawai(@RequestParam(name = "nip")String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNIP(nip).get();
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		
		List<InstansiModel> listInstansi = pegawai.getInstansi().getProvinsi().getListInstansi();
		
		List<JabatanModel> listJabatan = jabatanService.getAll();
		
		model.addAttribute("title", "Ubah Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		return "update-pegawai";
		
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params= {"addJabatan"})
	public String rowJabatan(@ModelAttribute PegawaiModel pegawai_new, Model model) {
		PegawaiModel pegawai = pegawai_new;
		
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getListJabatanPegawai().add(jabatanPegawai);
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getListInstansi();
		
		List<JabatanModel> listJabatan = jabatanService.getAll();
		
		model.addAttribute("title", "Ubah Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		return "update-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.POST)
	private String updatePegawaiSubmit(@ModelAttribute PegawaiModel newPegawai, Model model) {
		PegawaiModel oldPegawai = pegawaiService.getPegawaiByNIP(newPegawai.getNip()).get();
		
		if (oldPegawai.getInstansi()==newPegawai.getInstansi()&&oldPegawai.getTahunmasuk().equals(newPegawai.getTahunmasuk())&&oldPegawai.getTanggallahir().equals(newPegawai.getTanggallahir())) {
			pegawaiService.update(newPegawai, oldPegawai);
		}
		else {
			String nip = pegawaiService.makeNip(newPegawai);
			newPegawai.setNip(nip);
			pegawaiService.update(newPegawai, oldPegawai);
		}
		model.addAttribute("status", "SUKSES!");
		model.addAttribute("info", "Pegawai dengan NIP " + newPegawai.getNip() + " berhasil diubah");
		return "notifications";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String addPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		List<JabatanPegawaiModel> listJabatanPegawai = new ArrayList<JabatanPegawaiModel>();
		pegawai.setListJabatanPegawai(listJabatanPegawai);
		
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getListJabatanPegawai().add(jabatanPegawai);
		List<InstansiModel> listInstansi = listProvinsi.get(0).getListInstansi();
		
		List<JabatanModel> listJabatan = jabatanService.getAll();
		
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Tambah Pegawai");
		model.addAttribute("pegawai", pegawai);
		return "add-pegawai";
		
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"addJabatan"})
	public String tambahJabatan(@ModelAttribute PegawaiModel pegawai_new, Model model) {
		PegawaiModel pegawai = pegawai_new;
		
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getListJabatanPegawai().add(jabatanPegawai);
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getListInstansi();
	
		List<JabatanModel> listJabatan = jabatanService.getAll();
		
		model.addAttribute("title", "Tambah Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {

		String nip = pegawaiService.makeNip(pegawai);
		pegawai.setNip(nip);
		
		List<JabatanPegawaiModel> listJabatan = pegawai.getListJabatanPegawai();
		
		pegawai.setListJabatanPegawai(new ArrayList<JabatanPegawaiModel>());
		
		pegawaiService.addPegawai(pegawai);
		
		for (int i = 0; i < listJabatan.size(); i++) {
			listJabatan.get(i).setPegawai(pegawai);
			jabatanPegawaiService.addJabatanPegawai(listJabatan.get(i));
		}
		
		model.addAttribute("status", "SUKSES!");
		model.addAttribute("info", "Pegawai dengan NIP "+ nip +" berhasil ditambahkan");
		return "notifications";
		
	}
	
	@RequestMapping(value = "/pegawai/termuda-tertua")
	private String viewPegawaiMudaTua(@RequestParam(value = "idInstansi") String id, Model model) {
		InstansiModel instansi = instansiService.getIntansiById(Long.parseLong(id)).get();
		List<PegawaiModel> listPegawaiInstansi = pegawaiService.getPegawaiByInstansi(instansi);
		PegawaiModel termuda = listPegawaiInstansi.get(listPegawaiInstansi.size()-1);
		PegawaiModel tertua = listPegawaiInstansi.get(0);
		List<JabatanPegawaiModel> jabatanPegawaiMuda = jabatanPegawaiService.getJabatanByNip(termuda.getNip()).get();
		List<JabatanPegawaiModel> jabatanPegawaiTua = jabatanPegawaiService.getJabatanByNip(tertua.getNip()).get();
		model.addAttribute("jabatanmuda", jabatanPegawaiMuda);
		model.addAttribute("jabatantua", jabatanPegawaiTua);
		model.addAttribute("termuda" , termuda);
		model.addAttribute("tertua" , tertua);
		return "view-pegawai-termuda-tertua";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	public String cariPegawai(Model model) {
		
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getListInstansi();
		List<JabatanModel> listJabatan = jabatanService.findAll();
		
		model.addAttribute("title", "Cari Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);

		return "search-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET, params= {"search"})
	public String cariPegawaiBaru(@RequestParam(value="IdProvinsi", required=false) Optional<Long> IdProvinsi, 
			@RequestParam(value="IdInstansi", required=false) Optional<Long> IdInstansi,
			@RequestParam(value="IdJabatan", required=false) Optional<Long> IdJabatan, Model model) {
		
	
		
		List<PegawaiModel> listPegawai = new ArrayList<PegawaiModel>();
		if (IdProvinsi.isPresent()) {
			if (IdInstansi.isPresent()) {
				if (IdJabatan.isPresent()) {
					InstansiModel instansi = instansiService.findById(IdInstansi.get());
					JabatanModel jabatan = jabatanService.findById(IdJabatan.get());
					
					listPegawai = pegawaiService.findByInstansiAndJabatan(instansi, jabatan);
					
				}
				else {
					InstansiModel instansi = instansiService.findById(IdInstansi.get());
					listPegawai = pegawaiService.findByInstansi(instansi);
				}
				
			}
			else {
				if (IdJabatan.isPresent()) {
					List<InstansiModel> listInstansi = provinsiService.findById(IdProvinsi.get()).getListInstansi();
					
					for (int i = 0; i < listInstansi.size(); i++) {
						List<PegawaiModel> listPegawaiBaru = listInstansi.get(i).getListPegawai();
						for (int j = 0; j < listPegawaiBaru.size(); j++) {
							for (int k = 0; k < listPegawaiBaru.get(j).getListJabatanPegawai().size(); k++) {
								if (listPegawaiBaru.get(j).getListJabatanPegawai().get(k).getJabatan().getId() == IdJabatan.get()) {
									listPegawai.add(listPegawaiBaru.get(j));
									break;
								}
							}	
						}
					}
				}
				else {
					List<InstansiModel> listInstansi = provinsiService.findById(IdProvinsi.get()).getListInstansi();
					for (int i = 0; i < listInstansi.size(); i++) {
						List<PegawaiModel> listPegawaiBaru = listInstansi.get(i).getListPegawai();
						listPegawai.addAll(listPegawaiBaru);
					}
				}
			}
		}
		else {
			if (IdJabatan.isPresent()) {
				JabatanModel jabatan = jabatanService.findById(IdJabatan.get());
				for (int i = 0; i< jabatan.getListJabatanPegawai().size(); i++) {
					listPegawai.add(jabatan.getListJabatanPegawai().get(i).getPegawai());
				}	
			}
		}
		
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getListInstansi();
		List<JabatanModel> listJabatan = jabatanService.findAll();
		
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listPegawai", listPegawai);
		return "search-pegawai";
	}
	
}
