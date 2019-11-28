package com.myRetail.myRetailDemo;

import com.myRetail.myRetailDemo.dao.ProductDao;
import com.myRetail.myRetailDemo.entity.ProductName;
import com.myRetail.myRetailDemo.dao.PriceDao;
import com.myRetail.myRetailDemo.dao.ProductNameDao;
import com.myRetail.myRetailDemo.constants.Currency;
import com.myRetail.myRetailDemo.entity.Price;
import com.myRetail.myRetailDemo.entity.Product;
import com.myRetail.myRetailDemo.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class MyRetailDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRetailDemoApplication.class, args);
	}

	@Component
	class DemoCommandLineRunner implements CommandLineRunner {

		@Autowired
		private ProductDao productDao;

		@Autowired
		private PriceDao priceDAO;

		@Autowired
		private ProductNameDao productNameDAO;

		@Override
		public void run(String... args) throws Exception {

			Product prod1 = new Product(null,null);
			productDao.save(prod1);
			ProductName productName1 = new ProductName(prod1.getProductId(),"ProductName1");
			productNameDAO.save(productName1);
			Price price1 = new Price(prod1.getProductId(), 1f, 1f, Currency.RUPEES);
			priceDAO.save(price1);

			Product prod2 = new Product(null,null);
			productDao.save(prod2);
			ProductName productName2 = new ProductName(prod2.getProductId(),"ProductName2");
			productNameDAO.save(productName2);
			Price price2 = new Price(prod2.getProductId(), 2f, 2f, Currency.DOLLARS);
			priceDAO.save(price2);
		}
	}
}
