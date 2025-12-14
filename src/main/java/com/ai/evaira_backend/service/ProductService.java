package com.ai.evaira_backend.service;

import com.ai.evaira_backend.dto.ProductDto;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> saveExternalProducts(List<ProductDto> products) {
        log.info("Storing the products");
        return products.stream()
                .map(this::mapAndSave)
                .toList();
    }

    private Product mapAndSave(ProductDto dto) {
        Product product = productRepository
                .findByExternalId(String.valueOf(dto.getId()))
                .orElse(new Product());

        product.setExternalId(String.valueOf(dto.getId()));
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImage());
        product.setDeeplinkUrl("https://www.amazon.in/Lymio-Jackets-Lightweight-Outwear-J-06-Green-L/dp/B0FMDKS5JN/?_encoding=UTF8&pd_rd_w=NADwY&content-id=amzn1.sym.211684f4-ebe1-443f-8a4a-0773471e979f&pf_rd_p=211684f4-ebe1-443f-8a4a-0773471e979f&pf_rd_r=0A6TPDEXBTRCN1HGYCFB&pd_rd_wg=EyuBl&pd_rd_r=cc79f797-270f-47cf-898a-06160a73a280&ref_=pd_hp_d_btf_crs_zg_bs_1571271031&th=1&psc=1");

        if (dto.getRating() != null) {
            product.setRating(dto.getRating().getRate());
            product.setRatingCount(dto.getRating().getCount());
        }

        return productRepository.save(product);
    }

    public List<Product> getAllFromDb() {
        return productRepository.findAll();
    }

    public String getProductDeepLinkById(Long productId){
        return productRepository.findDeeplinkById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));


    }
}


