package com.lemnos.server.configurations.swagger;

import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.dtos.requests.EnderecoRequest;
import com.lemnos.server.models.viacep.ViaCepDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Endereco", description = "Address")
public interface EnderecoSwagger extends SwaggerConfiguration{

    @Operation(description = "Fetch the fields of the address from the CEP from the ViaCEP API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched the address successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ViaCepDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, some field was passed wrong", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to fetch the address", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Service Down, The ViaCep Api is down", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "503", description = "Internal Server Erros, Network error trying to access Via Cep or RestTemplate isn't working", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<ViaCepDTO> getFieldsEndereco(String cep);

    @Operation(description = "Register the Address with cep and the user, adding to the list of address if it is a client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registered the address successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Bad Request, some field was passed wrong", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to record the address", content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> create(EnderecoRequest enderecoRequest);

    @Operation(description = "Update the Address of an user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the address successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Bad Request, some field was passed wrong", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to update the address", content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> update(EnderecoRequest enderecoRequest);

    @Operation(description = "Deleting the Address of an user, or removing from the list if it is a client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Removed the address successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Bad Request, some field was passed wrong", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to record the address", content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> remove(String email, String cep, String entidade);

    @Operation(description = "Verify the fields to register the Address without a new record in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "It's possible to register the address successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Bad Request, some field was passed wrong", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to record the address", content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> verificarCampos(EnderecoRequest enderecoRequest);
}
