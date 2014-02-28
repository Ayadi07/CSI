package com.PortfolioManager.domain.dao;
import com.PortfolioManager.domain.entities.OrderStock;
import java.util.List;

public interface IOrderStock {

	public void persist(OrderStock o);

	public void delete(long orderId);

	public OrderStock getOrderById(long orderId);

	public List<OrderStock> listOrderByPortfolio(Long portfoliotId);

	void update(OrderStock o);

}
