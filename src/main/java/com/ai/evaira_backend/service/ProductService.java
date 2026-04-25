package com.ai.evaira_backend.service;

import com.ai.evaira_backend.dto.ProductDto;
import com.ai.evaira_backend.dto.enums.PriceBucket;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.repository.ProductRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public List<Product> saveExternalProducts(List<ProductDto> products) {

        log.info("Storing {} products", products.size());

        List<Product> entities = products.stream()
                .map(this::mapToEntity)
                .toList();

        return productRepository.saveAll(entities);
    }

    private Product mapToEntity(ProductDto dto) {

        Product product = productRepository
                .findByExternalId(dto.getExternalId())
                .orElse(new Product());

        product.setExternalId(dto.getExternalId());
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setSubCategory(dto.getSubCategory());
        product.setGender(dto.getGender());
        product.setPrimaryColor(dto.getPrimaryColor());

//        if(dto.getFitType().equals(FitType.STRAIGHT))
//            product.setFitType(FitType.RELAXED);
//        else
//            product.setFitType(dto.getFitType());

        if(dto.getPrice()>=400 && dto.getPrice()<=800 )
            product.setPriceBucket(PriceBucket.BUDGET);
        else if(dto.getPrice()>=801 && dto.getPrice()<=1200 )
            product.setPriceBucket(PriceBucket.VALUE);
        if(dto.getPrice()>=1201 && dto.getPrice()<=2500 )
            product.setPriceBucket(PriceBucket.MIDRANGE);
        else if(dto.getPrice()>=2501  )
            product.setPriceBucket(PriceBucket.PREMIUM);


        product.setBrand(dto.getBrand());
        product.setFabric(dto.getFabric());
        product.setStyleType(dto.getStyleType());
        product.setSeason(dto.getSeason());
        product.setImageUrl(dto.getImageUrl());
        product.setDeeplinkUrl(dto.getDeeplinkUrl());
        product.setRating(dto.getRating());
        product.setLikesCount(dto.getLikesCount());
        product.setOccasionTags(dto.getOccasionTags());
        product.setColorFamily(dto.getColorFamily());
        product.setStyleVibe(dto.getStyleVibe());

        return product;
    }

    public List<Product> getAllFromDb() {
        return productRepository.findAllRandom();
    }

    public String getProductDeepLinkById(Long productId){
        return productRepository.findDeeplinkById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}