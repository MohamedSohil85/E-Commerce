package com.eCommerce.services;

import com.eCommerce.dtos.AdDto.CategoryDto;
import com.eCommerce.entities.Category;
import com.eCommerce.entities.SubCategory;
import com.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.mapper.CategoryMapper;
import com.eCommerce.persistence.CategoryRepository;
import com.eCommerce.persistence.SubCategoryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
@ApplicationScoped
public class CategoryService {


    @Inject
    CategoryRepository categoryRepository;
    @Inject
    SubCategoryRepository subCategoryRepository;
    CategoryMapper categoryMapper=new CategoryMapper();
   @Transactional
    public CategoryDto createNewCategory(CategoryDto categoryDto){
        Category category=categoryMapper.toEntity(categoryDto);
        categoryRepository.persist(category);
        return categoryDto;
    }
    @Transactional
    public SubCategory createSubCategory(SubCategory subCategory,String categoryName)throws ResourceNotFoundException{
        return categoryRepository.findCategoryByName(categoryName).map(category -> {
            category.getSubCategories().add(subCategory);
            subCategory.setCategory(category);
            subCategory.persist();
            return subCategory;
        }).orElseThrow(()->new ResourceNotFoundException("cannot find Resource !"));
    }
    public List<CategoryDto>loadAllCategories()throws ResourceNotFoundException{
        List<Category>categories=categoryRepository.listAll();
        if (categories.isEmpty()){
            throw new ResourceNotFoundException("List is Empty!");
        }
        return categoryMapper.toDto(categories);
    }
    public List<SubCategory>loadAllSubcategory()throws ResourceNotFoundException{
        List<SubCategory>subCategories=subCategoryRepository.listAll();
        if (subCategories.isEmpty()){
            throw new ResourceNotFoundException("List is Empty");
        }
        return subCategories;
    }
}
