package vttp2022.assessment.csf.orderbackend.models;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

// IMPORTANT: You can add to this class, but you cannot delete its original content

public class Order {

	private Integer orderId;
	private String name;
	private String email;
	private Integer size;
	private String sauce;
	private Boolean thickCrust;
	private List<String> toppings = new LinkedList<>();
	private String comments;

	public void setOrderId(Integer orderId) { this.orderId = orderId; }
	public Integer getOrderId() { return this.orderId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }

	public void setSize(Integer size) { this.size = size; }
	public Integer getSize() { return this.size; }

	public void setSauce(String sauce) { this.sauce = sauce; }
	public String getSauce() { return this.sauce; }

	public void setThickCrust(Boolean thickCrust) { this.thickCrust = thickCrust; }
	public Boolean isThickCrust() { return this.thickCrust; }

	public void setToppings(List<String> toppings) { this.toppings = toppings; }
	public List<String> getToppings() { return this.toppings; }
	public void addTopping(String topping) { this.toppings.add(topping); }

	public void setComments(String comments) { this.comments = comments; }
	public String getComments() { return this.comments; }


	public static Order create(String payload) {
		Order o = new Order();
		List<String> t = new LinkedList<>();
        JsonObject obj = Json.createReader(new StringReader(payload)).readObject();
        o.setEmail(obj.getString("email"));
        o.setName(obj.getString("name"));
		o.setSauce(obj.getString("sauce"));
		o.setSize(obj.getInt("size"));
		if(obj.getString("base").equals("thin")){
			o.setThickCrust(false);
		}else if(obj.getString("base").equals("thick")){
			o.setThickCrust(true);
		}
		for(int i = 0; i <obj.getJsonArray("toppings").size(); i++){
			t.add(obj.getJsonArray("toppings").getString(i));
		}
		o.setToppings(t);
		o.setComments(obj.getString("comments"));
        return o;
	}

}
