package config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class webconfig extends WebMvcConfigurerAdapter {
   /* @Bean
    public FastJsonConfig fastJsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        SerializerFeature writeMapNullValue = SerializerFeature.WriteMapNullValue;
        SerializerFeature WriteNullStringAsEmpty = SerializerFeature.WriteNullStringAsEmpty;
        SerializerFeature WriteNullNumberAsZero = SerializerFeature.WriteNullNumberAsZero;
        SerializerFeature WriteNullListAsEmpty = SerializerFeature.WriteNullListAsEmpty;
        fastJsonConfig.setSerializerFeatures(writeMapNullValue, WriteNullStringAsEmpty,
                WriteNullNumberAsZero, WriteNullListAsEmpty);
        return fastJsonConfig;
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(
            @Qualifier("fastJsonConfig") FastJsonConfig fastJsonConfig) {
        FastJsonHttpMessageConverter4 fastConverter = new FastJsonHttpMessageConverter4();
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }*/
}


