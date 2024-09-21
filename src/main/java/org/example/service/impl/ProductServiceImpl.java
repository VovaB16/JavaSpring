package org.example.service.impl;

import org.example.dto.product.ProductItemDTO;
import org.example.exception.ProductException;
import org.example.mapper.ProductMapper;
import org.example.model.PaginationResponse;
import org.example.model.ProductEntity;
import org.example.model.SearchData;
import org.example.repo.CategoryRepository;
import org.example.repo.ProductRepository;
import org.example.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements org.example.interfaces.IProductService {

    @Autowired
    private ProductRepository repo;
    @Autowired
    private StorageService storageService;
    @Autowired
    private CategoryRepository categoryRepo;
    //    @Autowired
//    private IImageRepository imageRepo;
    @Autowired
    private ProductMapper mapper;

    @Override
    public PaginationResponse<ProductItemDTO> getProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page, size, Sort.by("id"));
        Page<ProductEntity> productsPage = repo.findAll(pageRequest);
        Iterable<ProductItemDTO> categories = mapper.toDto(productsPage.getContent());
        return  new PaginationResponse<ProductItemDTO>(categories,productsPage.getTotalElements());
    }


    @Override
    public ProductItemDTO getProductById(Long id) {
        Optional<ProductEntity> product = repo.findById(Math.toIntExact(id));
        if(product.isPresent()){
            return mapper.toDto(product.get());
        }
        else{
            throw new ProductException("Invalid Product id");
        }
    }

    @Override
    public boolean deleteProductById(Long id) throws IOException {
        return  false;
    }
}