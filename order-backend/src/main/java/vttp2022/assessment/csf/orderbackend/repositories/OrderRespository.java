package vttp2022.assessment.csf.orderbackend.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.assessment.csf.orderbackend.models.Order;

@Repository
public class OrderRespository {

    public String SQL_INSERT_ORDER = "insert into orders (name, email, pizza_size, thick_crust, sauce, toppings, comments) values (?, ?, ?, ?, ?, ?, ?)";
    // public String SQL_GET_ORDER_BY_EMAIL = "select * from orders where email = ?";

    @Autowired
    private JdbcTemplate template;

    public Boolean createOrder(Order o) {
        String toppings =  o.getToppings().toString().replace("[", "").replace("]", "").replaceAll("\\s","");
        int added = template.update(SQL_INSERT_ORDER, o.getName(), o.getEmail(), o.getSize(), o.isThickCrust(),
                o.getSauce(), toppings, o.getComments());
        return added > 0;
    }

    // public List<Order> getOrdersByEmail(String email) {
    //     List<Order> orders = new LinkedList<>();
    //     SqlRowSet rs = template.queryForRowSet(SQL_GET_ORDER_BY_EMAIL, email);
    //     while (rs.next()) {
    //         Order o = Order.convert(rs);
    //         orders.add(o);
    //     }
    //     return orders;
    // }

}
