package org.springframework.cloud.sample.bookstore.servicebroker.service;

import org.springframework.cloud.servicebroker.model.catalog.Catalog;
import org.springframework.cloud.servicebroker.model.catalog.Plan;
import org.springframework.cloud.servicebroker.model.catalog.ServiceDefinition;
import org.springframework.cloud.servicebroker.service.CatalogService;
import org.springframework.stereotype.Service;

/**
 * Created by nanquanyuhao on 2018/8/9.
 */
@Service
public class BookStoreCatalogService implements CatalogService {

	@Override
	public Catalog getCatalog() {
		return Catalog.builder()
			.serviceDefinitions(getServiceDefinition("bdb1be2e-360b-495c-8115-d7697f9c6a9e"))
			.build();
	}

	@Override
	public ServiceDefinition getServiceDefinition(String serviceId) {
		return ServiceDefinition.builder()
			.id(serviceId)
			.name("bookstore")
			.description("A simple book store service")
			.bindable(true)
			.tags("book-store", "books", "sample")
			.plans(getPlan())
			.metadata("displayName", "bookstore")
			.metadata("longDescription", "A simple book store service")
			.metadata("providerDisplayName", "Acme Books")
			.build();
	}

	private Plan getPlan() {
		return Plan.builder()
			.id("b973fb78-82f3-49ef-9b8b-c1876974a6cd")
			.name("standard")
			.description("A simple book store plan")
			.free(true)
			.build();
	}
}
