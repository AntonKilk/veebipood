package ee.anton.veebipood.mapper;

import ee.anton.veebipood.dto.ProductDto;
import ee.anton.veebipood.entity.Category;
import ee.anton.veebipood.entity.Product;
import ee.anton.veebipood.repository.CategoryRepository;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    protected CategoryRepository categoryRepository;

    @Mapping(source = "categoryId", target = "category", qualifiedByName = "categoryFromId")
    public abstract Product toEntity(ProductDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "categoryId", target = "category", qualifiedByName = "categoryFromId")
    public abstract void update(ProductDto dto, @MappingTarget Product product);

    @Named("categoryFromId")
    protected Category categoryFromId(Long id) {
        if (id == null) return null;
        return categoryRepository.findById(id).orElseThrow();
    }
}
