package com.ruoyi.system.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * MinIO文件服务接口
 *
 * @author ruoyi
 */
public interface IMinioService {

    /**
     * 上传文件到MinIO
     *
     * @param file 文件
     * @param bucketName 桶名称
     * @return 文件URL
     */
    String uploadFile(MultipartFile file, String bucketName) throws Exception;

    /**
     * 删除MinIO中的文件
     *
     * @param bucketName 桶名称
     * @param objectName 对象名称
     */
    void deleteFile(String bucketName, String objectName) throws Exception;
}
