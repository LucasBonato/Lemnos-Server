package com.lemnos.server.configurations.swagger;

import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.dtos.requests.AvaliacaoRequest;
import com.lemnos.server.models.dtos.requests.ProdutoFiltroRequest;
import com.lemnos.server.models.dtos.requests.ProdutoRequest;
import com.lemnos.server.models.dtos.responses.ProdutoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Produto", description = "Product")
public interface ProdutoSwagger extends SwaggerConfiguration {
    @Operation(description = "Fetch all products and their data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all products successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProdutoResponse.class))))
    })
    ResponseEntity<List<ProdutoResponse>> getAll();

    @Operation(description = "Fetch some products based on an filter, all of them are optional.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all products successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProdutoResponse.class))))
    })
    ResponseEntity<List<ProdutoResponse>> getBy(ProdutoFiltroRequest produtoFiltroRequest);

    @Operation(description = "Fetch just one product by its id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched the product successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    ResponseEntity<ProdutoResponse> getOneById(String id);

    @Operation(description = "Register a product sending an body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request, some information passed wrong", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> register(ProdutoRequest produtoRequest);

    @Operation(description = "Update a product sending some itens of the body or it all.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request, some information passed wrong", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> update(String id, ProdutoRequest produtoRequest);

    @Operation(description = "Deleted just one product by its id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the product successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> delete(String id);

    @Operation(description = "Fetch all products and their data that just have discounts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all products successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProdutoResponse.class))))
    })
    ResponseEntity<List<ProdutoResponse>> getAllWithDiscount();

    @Operation(description = "Remove the discount of a product its id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The discount got removed successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> retirarDesconto(String id);

    @Operation(description = "Rate a product from 1 to 5 stars.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product rated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request, some information passed wrong", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> avaliar(String id, AvaliacaoRequest avaliacaoRequest);
}
