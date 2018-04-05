package com.f_lin.gateway.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author F_lin
 * @since 2018/4/5
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@ConfigurationProperties("footprint.filter")
public class TokenVerificationSetting {
    List<String> no_token_verification = new ArrayList<>();
}
