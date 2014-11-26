package org.univ.product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ProductManager {

	static List<Product> products = new ArrayList<Product>();
	
	static{
		Product p = new Product();
		p.setName("product 1");
		p.setIdentifier(1);
		
		products.add(p);
		
		p = new Product();
		p.setName("product 2");
		p.setIdentifier(2);
		
		products.add(p);
		
		p = new Product();
		p.setName("product 3");
		p.setIdentifier(3);
		
		products.add(p);
	}

	public static List<Product> getProducts() {
		return products;
	}
	
	
	public static Product findProductById(int id) {
		Product product = null;
		Iterator<Product> iterator = ProductManager.getProducts().iterator();
		while(iterator.hasNext() && (product=iterator.next()).getIdentifier()!=id);	
		return product;
	}
	
	
}
