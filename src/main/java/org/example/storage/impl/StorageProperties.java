package org.example.storage.impl;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Service
@ConfigurationProperties("storage")
public class StorageProperties {
    @Value("${image.storage}")
    private String location="uploadImages";
}