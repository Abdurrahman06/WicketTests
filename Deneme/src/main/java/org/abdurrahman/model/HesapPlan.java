package org.abdurrahman.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class HesapPlan implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "hesapKod", length = 40)
	private String hesapKod;
	
	@Column(name = "hesapAdi", length = 255)
	private String hesapAdi;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hesapPlan")
	private List<Yevmiye> yevmiyeList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHesapKod() {
		return hesapKod;
	}

	public void setHesapKod(String hesapKod) {
		this.hesapKod = hesapKod;
	}

	public String getHesapAdi() {
		return hesapAdi;
	}

	public void setHesapAdi(String hesapAdi) {
		this.hesapAdi = hesapAdi;
	}
	
	public List<Yevmiye> getYevmiyeList() {
		return yevmiyeList;
	}

	public void setYevmiyeList(List<Yevmiye> yevmiyeList) {
		this.yevmiyeList = yevmiyeList;
	}

}
