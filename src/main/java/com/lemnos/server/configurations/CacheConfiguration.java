package com.lemnos.server.configurations;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfiguration {
    private static final int TRINTA_SEGUNDOS = 30 * 1000;

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setAllowNullValues(false);
        cacheManager.setCacheNames(Arrays.asList("productsDiscount", "allClientes", "allFuncionarios", "allFornecedores"));
        return cacheManager;
    }

    @CacheEvict(value = "productsDiscount", allEntries = true)
    @Scheduled(fixedDelay = TRINTA_SEGUNDOS, initialDelay = 0)
    public void evictProductsDiscountCache() { }

    @CacheEvict(value = "allClientes", allEntries = true)
    @Scheduled(fixedDelay = TRINTA_SEGUNDOS, initialDelay = 0)
    public void evictAllClientesCache() { }

    @CacheEvict(value = "allFuncionarios", allEntries = true)
    @Scheduled(fixedDelay = TRINTA_SEGUNDOS, initialDelay = 0)
    public void evictAllFuncionariosCache() { }

    @CacheEvict(value = "allFornecedores", allEntries = true)
    @Scheduled(fixedDelay = TRINTA_SEGUNDOS, initialDelay = 0)
    public void evictAllFornecedoresCache() { }
}
