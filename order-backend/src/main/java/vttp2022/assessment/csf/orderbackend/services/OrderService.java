package vttp2022.assessment.csf.orderbackend.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.repositories.OrderRespository;

@Service
public class OrderService {

	public String SQL_GET_ORDER_BY_EMAIL = "select * from orders where email = ?";

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private PricingService priceSvc;

	@Autowired
	private OrderRespository oRepo;

	// POST /api/order
	// Create a new order by inserting into orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public void createOrder(Order order) {
		Boolean b = oRepo.createOrder(order);
	}

	// GET /api/order/<email>/all
	// Get a list of orders for email from orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public List<OrderSummary> getOrdersByEmail(String email) {
		// Use priceSvc to calculate the total cost of an order
		List<OrderSummary> ordersums = new LinkedList<>();
		
		SqlRowSet rs = template.queryForRowSet(SQL_GET_ORDER_BY_EMAIL, email);
		while (rs.next()) {
			OrderSummary os = OrderSummary.convert(rs);
			Float total = getTotal(rs);
			os.setAmount(total);
			ordersums.add(os);
		}
		return ordersums;

	}

	public Float getTotal(SqlRowSet rs) {
		Float total = 0f;
		Float size = priceSvc.size(rs.getInt("pizza_size"));
		Float crust = 0f;
		if (rs.getBoolean("thick_crust")) {
			crust = priceSvc.thickCrust();
		} else if (!rs.getBoolean("thick_crust")) {
			crust = priceSvc.thinCrust();
		}
		Float sauce = priceSvc.sauce(rs.getString("sauce"));
		Float topping = 0f;
		String[] toppings = rs.getString("toppings").split(",");
		for (String t : toppings) {
			topping += priceSvc.topping(t);
		}
		total = size + crust + sauce + topping;
		return total;
	}
}
