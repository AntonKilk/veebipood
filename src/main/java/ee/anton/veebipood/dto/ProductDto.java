package ee.anton.veebipood.dto;

public record ProductDto(
        String name,
        Double price,
        Boolean active,
        Integer stock,
        String description,
        String image

) {
}
