package com.lemnos.server.configurations.swagger;

import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.dtos.requests.FireBaseLoginRequest;
import com.lemnos.server.models.dtos.requests.FornecedorRequest;
import com.lemnos.server.models.dtos.requests.FuncionarioRequest;
import com.lemnos.server.models.dtos.requests.auth.LoginRequest;
import com.lemnos.server.models.dtos.requests.auth.RegisterRequest;
import com.lemnos.server.models.dtos.responses.auth.LoginReponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "0Auth", description = "Authorization")
public interface AuthSwagger extends SwaggerConfiguration{

    @Operation(description = "Login with email and password to get the token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged in successfully", content = @Content(schema = @Schema(implementation = LoginReponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, some information was passed wrong", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    ResponseEntity<LoginReponse> login(@RequestBody LoginRequest loginRequest);

    @Operation(description = "Login with the google access token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged in successfully", content = @Content(schema = @Schema(implementation = LoginReponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, some information was passed wrong", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorizes, the access token is expired", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    ResponseEntity<LoginReponse> loginFirebase(@RequestBody FireBaseLoginRequest fbLoginRequest);

    @Operation(description = "Register with email, password and CPF to be able to login.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registered in successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "209", description = "Conflict, email or CPF are already registered", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, some information was passed wrong", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest);

    @Operation(description = "Register an employee with their information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registered successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "209", description = "Conflict, email, CPF or telefone is already in use", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, some information was passed wrong", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to register an employee", content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> cadastrarFuncionario(@RequestBody FuncionarioRequest funcionarioRequest);

    @Operation(description = "Register a supplier with their information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registered successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "209", description = "Conflict, email, CNPJ or telefone is already in use", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, some information was passed wrong", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to register an employee", content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> cadastrarFornecedor(@RequestBody FornecedorRequest fornecedorRequest);

    @Operation(description = "Verify if it can register an user without creating a new record on the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "It's possible to register successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "209", description = "Conflict, email or CPF are already registered", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, some information was passed wrong", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    ResponseEntity<Void> verificarCliente(@RequestBody RegisterRequest registerRequest);

    @Operation(description = "verify if it can register an employee with their information without creating a new record on the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "It's possible to register an employee successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "209", description = "Conflict, email, CPF or telefone is already in use", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, some information was passed wrong", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to register an employee", content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> verificarFuncionario(@RequestBody FuncionarioRequest funcionarioRequest);

    @Operation(description = "verify if it can register a supplier with their information without creating a new record on the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "It's possible to register a supplier successfully", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "209", description = "Conflict, email, CNPJ or telefone is already in use", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, some information was passed wrong", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you are not authenticated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Forbidden, you don't have the role to register an employee", content = @Content(schema = @Schema()))
    })
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> verificarFornecedor(@RequestBody FornecedorRequest fornecedorRequest);
}
