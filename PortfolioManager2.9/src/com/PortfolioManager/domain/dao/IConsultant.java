package com.PortfolioManager.domain.dao;

import com.PortfolioManager.domain.entities.Consultant;
import java.util.List;




public interface IConsultant {
	public void persist(Consultant c);

	public void update(Consultant c);

	public void delete(Consultant c);

	public Consultant getConsultanttById(String ConsultantId);
	
	public List<Consultant> getAllConsultant();
}
