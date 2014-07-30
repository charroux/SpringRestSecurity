package org.univ;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.univ.product.Product;
import org.univ.product.ProductManager;

@Component
@Controller
@RequestMapping("/site")
public class RestController{

	@RequestMapping(method = RequestMethod.GET) 
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResourceSupport get() {
	
		ResourceSupport resourceSupport = new ResourceSupport();
		
		Link link = ControllerLinkBuilder.linkTo(RestController.class).slash("/").withSelfRel();
		resourceSupport.add(link);
		
		link = ControllerLinkBuilder.linkTo(RestController.class).slash("product").withRel("products list");
		resourceSupport.add(link);
		
		return resourceSupport;
	}
	
	@RequestMapping(value = "/product", method = RequestMethod.GET) 
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResourceSupport getProduct() {
		ResourceSupport resourceSupport = new ResourceSupport();
		
		Link link = ControllerLinkBuilder.linkTo(RestController.class).slash("/product").withSelfRel();
		resourceSupport.add(link);
		
		int productId;
		
		List<Product> products = ProductManager.getProducts();
		
		for(int i=0; i<products.size(); i++){
			productId = products.get(i).getIdentifier();
			link = ControllerLinkBuilder.linkTo(RestController.class).slash("product").slash(productId).withRel("product"+productId);
			resourceSupport.add(link);
		}
		
		return resourceSupport;
		
	}
	
	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET) 
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Product getAProduct(@PathVariable("id") Integer productId) {		
		Product product = ProductManager.findProductById(productId);
		if(product != null){
			product.removeLinks();
			Link link = ControllerLinkBuilder.linkTo(RestController.class).slash("/product/"+productId).withSelfRel();
			product.add(link);
		}
		
		return product;
		
	}
	
	
	@RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void putProduct(@PathVariable("id") Integer productId, @RequestBody Product product) {
		Product aProduct = ProductManager.findProductById(productId);
		if(aProduct != null){
			ProductManager.getProducts().remove(aProduct);
			ProductManager.getProducts().add(product);
		}
		
	}
		
	@RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void removeProduct(@PathVariable("id") Integer productId, @RequestBody Product product) {
		Product aProduct = ProductManager.findProductById(productId);
		if(aProduct != null){
			ProductManager.getProducts().remove(product);
		}
		
	}
	
}
