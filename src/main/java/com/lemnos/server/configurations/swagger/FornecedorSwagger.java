package com.lemnos.server.configurations.swagger;

import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.dtos.requests.FornecedorRequest;
import com.lemnos.server.models.dtos.responses.FornecedorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.checkerframework.checker.units.qual.C;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Fornecedor", description = "Supplier")
public interface FornecedorSwagger extends SwaggerConfiguration{

    @Operation(description = "Fetch all suppliers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all suppliers successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FornecedorResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated"),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to fetch the suppliers")
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<List<FornecedorResponse>> getAll();

    @Operation(description = "Fetch a supplier by its email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched the supplier successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FornecedorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated"),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to fetch the supplier")
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<FornecedorResponse> getOneByEmail(String email);

    @Operation(description = "Fetch a list of suppliers by name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all suppliers by name successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FornecedorResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated"),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to fetch suppliers")
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<List<FornecedorResponse>> getBy(String nome);

    @Operation(description = "Update a supplier with their information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated supplier successfully"),
            @ApiResponse(responseCode = "209", description = "Conflict, email, CNPJ or telefone is already in use", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, some information was passed wrong", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated"),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to update a supplier")
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> update(String email, FornecedorRequest fornecedorRequest);

    @Operation(description = "Delete a supplier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted supplier successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not Found, supplier not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to delete a supplier")
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> delete(String email);
}
