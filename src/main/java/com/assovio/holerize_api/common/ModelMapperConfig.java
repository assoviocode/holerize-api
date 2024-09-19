package com.assovio.holerize_api.common;

import java.util.TimeZone;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    
    @Bean
    public ModelMapper strictModelMapper() {
        ModelMapper modelMapperStrict = new ModelMapper();
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        modelMapperStrict.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapperStrict;
    }
    
}
