package org.abdurrahman.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table
@Immutable
public class Mizan implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	private String hesapKod;
	private BigDecimal borcTop;
	private BigDecimal alacakTop;

	public String getHesapKod() {
		return hesapKod;
	}

	public void setHesapKod(String hesapKod) {
		this.hesapKod = hesapKod;
	}

	public BigDecimal getBorcTop() {
		return borcTop;
	}

	public void setBorcTop(BigDecimal borcTop) {
		this.borcTop = borcTop;
	}

	public BigDecimal getAlacakTop() {
		return alacakTop;
	}

	public void setAlacakTop(BigDecimal alacakTop) {
		this.alacakTop = alacakTop;
	}

}
