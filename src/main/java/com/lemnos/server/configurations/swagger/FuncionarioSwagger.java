package com.lemnos.server.configurations.swagger;

import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.exceptions.entidades.funcionario.FuncionarioNotFoundException;
import com.lemnos.server.models.dtos.requests.FuncionarioFiltroRequest;
import com.lemnos.server.models.dtos.requests.FuncionarioRequest;
import com.lemnos.server.models.dtos.responses.FuncionarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

@Tag(name = "Funcionário", description = "Employee")
public interface FuncionarioSwagger extends SwaggerConfiguration{

    @Operation(description = "Fetch all employees.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all employees successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FuncionarioResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to fetch the employees", content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<List<FuncionarioResponse>> getAll();

    @Operation(description = "Fetch one employees with the token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched the employee successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to fetch the employee", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found, employee not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<FuncionarioResponse> getOne(JwtAuthenticationToken token);

    @Operation(description = "Fetch an employee by email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched the employee", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to fetch the employee", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found, employee not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<FuncionarioResponse> getOneByEmail(String email);

    @Operation(description = "Fetch a list of employees by name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched the employee", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FuncionarioResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to fetch the employees", content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<List<FuncionarioResponse>> getBy(FuncionarioFiltroRequest nome);

    @Operation(description = "Update an employee with optional fields.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the employee", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to update the employee", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found, employee not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> updateFuncionario(String email, FuncionarioRequest funcionarioRequest);

    @Operation(description = "Activate or deactivate a list of employees by email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activate or deactivate successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to execute the action", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found, employee not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> ativarOuDesativar(List<String> emails);

    @Operation(description = "Delete an employees by email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to delete the employee", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found, employee not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> deleteById(String email);
}