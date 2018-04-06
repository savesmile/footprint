package com.f_lin.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author F_lin
 * @since 2018/4/6
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@ConfigurationProperties("img")
public class ImageUploadSetting {
    private Map<String, String> setting = new HashMap<>();
}
