package ee.anton.veebipood.mapper;

import ee.anton.veebipood.dto.CategoryDto;
import ee.anton.veebipood.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryDto dto);

    void update(CategoryDto dto, @MappingTarget Category category);
}
