package com.lemnos.server.configurations.swagger;

import com.lemnos.server.exceptions.ExceptionResponse;
import com.lemnos.server.models.dtos.responses.FavoritoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

@Tag(name = "Favorito", description = "Favorite")
public interface FavoritoSwagger {

    @Operation(summary = "Get favorite products", description = "Get all favorite products of the user account by its token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned all the favorite products from the user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FavoritoSwagger.class))),
            @ApiResponse(responseCode = "404", description = "User Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    ResponseEntity<List<FavoritoResponse>> getFavoritos(JwtAuthenticationToken token);

    @Operation(summary = "Favorite a product", description = "Favorite a product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully favorited"),
            @ApiResponse(responseCode = "209", description = "The Product is already favorited", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, some information passed wrong", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product or User Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    ResponseEntity<Void> favoritar(JwtAuthenticationToken token, String idProduto);

    @Operation(summary = "Unfavorite a product", description = "Unfavorite a product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully unfavorited"),
            @ApiResponse(responseCode = "400", description = "Bad Request, some information passed wrong", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product or User Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    ResponseEntity<Void> desfavoritar(JwtAuthenticationToken token, String idProduto);
}
